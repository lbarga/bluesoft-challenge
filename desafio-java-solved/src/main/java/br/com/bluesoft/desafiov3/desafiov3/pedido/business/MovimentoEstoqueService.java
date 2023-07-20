package br.com.bluesoft.desafiov3.desafiov3.pedido.business;

import br.com.bluesoft.desafiov3.desafiov3.pedido.model.ItemPedido;
import br.com.bluesoft.desafiov3.desafiov3.pedido.model.MovimentoEstoque;
import br.com.bluesoft.desafiov3.desafiov3.pedido.model.Pedido;
import br.com.bluesoft.desafiov3.desafiov3.pedido.model.exception.EstoqueVazioException;
import br.com.bluesoft.desafiov3.desafiov3.pedido.repository.MovimentoEstoqueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovimentoEstoqueService {

    private final MovimentoEstoqueRepository movimentoEstoqueRepository;

    public MovimentoEstoqueService(MovimentoEstoqueRepository movimentoEstoqueRepository) {
        this.movimentoEstoqueRepository = movimentoEstoqueRepository;
    }

    @Transactional
    public void movimentarEstoquePedido(final Pedido pedido, boolean simularFalha) throws EstoqueVazioException {
        double quantidadeMovimentada = pedido.getItens()
                .stream()
                .mapToDouble(ItemPedido::getQuantidade)
                .sum();

        MovimentoEstoque movimentoEstoque = new MovimentoEstoque(pedido.getId(), quantidadeMovimentada);

        //NÃO APAGAR ESTE BLOCO DE CÓDIGO DE FALHA!!//
        //Aqui é somente uma simulação de algum problema.
        //Seja no banco, com uma comunicação via API, ou mensageria. Apenas para simular um problema ao movimentar o estoque.
        if (simularFalha) {
            simularFalha();
        }
        //

        movimentoEstoqueRepository.salvarMovimentoEstoque(movimentoEstoque);
    }

    private void simularFalha() throws EstoqueVazioException {
        throw new EstoqueVazioException();
    }

}
