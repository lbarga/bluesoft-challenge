
package br.com.bluesoft.desafiov3.desafiov3.pedido.model.exception;

public class Limite50ItensException extends Exception {

    public Limite50ItensException() {
        super("O maximo de itens por pedido e 50!");
    }
}
