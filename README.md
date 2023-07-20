## Read before starting the challenge

- Create a branch with your name and commit your changes to it.
- When you finish the project, execute the command `mvn clean`, then `mvn test` to check if the tests are passing. If they are not passing, correct the issues. After that, compress the folder and send it following the interviewer's instructions.
- In addition to the zipped project, also send your Github and Linkedin profiles (Note: do not upload the project to Github; provide these details in the same question where you will share the file).
- We aim to follow good programming practices. Therefore, even though it's a simple application, apply all the best practices you would use in a real system. Implement Clean Code and SOLID principles.
- Be concise in your code; we don't need you to showcase everything you know, but rather to do a good job with what you develop.
- _Use the existing Java version in the project. When we mention using Java 8 in the video, it's only to indicate the use of Optional, Lambda, Streams, and similar features. Do not change the Java version of the project._

## Challenge

Imagine that a developer from your team is going on vacation and left some "issues" for you to solve...

He sent you an email with the following text:

**Bugs**:

1. The DBA is reporting that purchase order items are being created in the database with a null value for the pedido_id column. We need to resolve this.

2. A customer is complaining that they cannot delete an order. We must fix this because the customer should be able to remove an order.

3. When we retrieve all orders, many queries are being executed in the production database. When running the test "PedidoControllerTest.listarTodosOsPedidos," 7 queries are executed, even though we only have 6 orders in our insert script.
   We need to fix this problem when retrieving all orders, but if possible, without affecting the good performance of fetching only one order.
   If there's no way to achieve this, we'll prioritize fixing the issue of too many queries!
   (Currently, when we retrieve only one order, the items are not returned, which is good because we don't need the items every time we retrieve a single order).

4. Occasionally, due to some integration issue, we are creating an order without updating the inventory! This should not happen. If there's any problem when updating the inventory, we should not create the order!
   Note: Please don't remove the code that simulates the failure or do something like (if(!simularFalha) movimentoEstoqueService.movimentarEstoquePedido}. Pretend that this boolean does not exist. You cannot change the internal execution order of the method to solve the problem or "skip" the error.
   To simulate this issue, just set the attribute simularFalha to true in the JSON for the order.

**Improvement requested by the analyst**:

We should not create an order with more than 50 items. It won't fit in the truck! Please limit an order to a maximum of 50 items.

**Code improvements**:

1. A senior developer commented that there is an easier way to know the quantity moved in the MovimentoEstoqueService class using Java 8. If you know, please help me.

2. Please review the Pedido, PedidoController, and PedidoService classes and give me suggestions for improvement. If you want, you can go ahead and implement the improvements.

3. A senior developer reviewed our code and said that the Pedido class is not correctly encapsulating the items. I didn't understand what he meant by that. If you understand, please correct it =D

**Unit test**:

1. Implement the body of the method "agruparPedidoPorFormaDePagamento" in the PedidoService class and its respective unit test. **It is essential that you create a unit test for this method.**

## To test the project

After running the project, the URL for it will be: localhost:8080/pedidos

Note that we have a script with some inserts in "data.sql" that Spring Boot will execute automatically when starting the application.

Below, I provide the JSON I use to create an order, in case you want to test it using Postman or similar tools. However, we also included an integration test class to make it easier.

```
{
	"retiradaNaLoja": true,
	"formaPagamento": "CREDITO",
	"simularFalha": false,
	"itens" : [{
		"descricaoProduto": "Coca-Cola",
		"quantidade": 1000
	}, {
		"descricaoProduto": "Lasanha-congelada",
		"quantidade": 3
	}, {
		"descricaoProduto": "Água",
		"quantidade": 3
	}, {
		"descricaoProduto": "Banana",
		"quantidade": 3
	}, {
		"descricaoProduto": "Maça",
		"quantidade": 3
	}, {
		"descricaoProduto": "Veja multi uso",
		"quantidade": 3
	}, {
		"descricaoProduto": "Cebola",
		"quantidade": 3
	}, {
		"descricaoProduto": "Alho",
		"quantidade": 3
	}]
}
```

```
To check the database, access the URL http://localhost:8080/h2-console with the following parameters:

Driver class: org.h2.Driver
JDBC url: jdbc:h2:mem:testdb
User name: sa
Password: sa
```

![image](https://user-images.githubusercontent.com/386430/185471341-d12b90ba-0eaf-4ec0-93e8-b31a5b4b3084.png)
