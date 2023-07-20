package br.com.bluesoft.desafiov3.desafiov3.pedido.business;

import br.com.bluesoft.desafiov3.desafiov3.pedido.model.FormaPagamento;
import br.com.bluesoft.desafiov3.desafiov3.pedido.model.ItemPedido;
import br.com.bluesoft.desafiov3.desafiov3.pedido.model.Pedido;
import br.com.bluesoft.desafiov3.desafiov3.pedido.model.exception.EstoqueVazioException;
import br.com.bluesoft.desafiov3.desafiov3.pedido.model.exception.Limite50ItensException;
import br.com.bluesoft.desafiov3.desafiov3.pedido.repository.PedidoRepository;
import br.com.bluesoft.desafiov3.desafiov3.pedido.web.form.PedidoFormulario;
import br.com.bluesoft.desafiov3.desafiov3.pedido.web.view.PedidosPaginadoView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    private final MovimentoEstoqueService movimentoEstoqueService;

    public PedidoService(PedidoRepository pedidoRepository, MovimentoEstoqueService movimentoEstoqueService) {
        this.pedidoRepository = pedidoRepository;
        this.movimentoEstoqueService = movimentoEstoqueService;
    }

    @Transactional(rollbackFor = {Exception.class})
    public Pedido novoPedido(PedidoFormulario pedidoFormulario) throws Limite50ItensException, EstoqueVazioException {
        Pedido pedido = new Pedido(pedidoFormulario.getRetiradaNaLoja(), pedidoFormulario.getFormaPagamento());

        List<ItemPedido> itens = pedidoFormulario.getItens().stream()
                .map(item -> new ItemPedido(item.getDescricaoProduto(), item.getQuantidade(), pedido))
                .collect(Collectors.toList());

        pedido.addItens(itens);

        final Pedido pedidoCriado = pedidoRepository.salvarPedido(pedido);

        movimentoEstoqueService.movimentarEstoquePedido(pedidoCriado, pedidoFormulario.getSimularFalha());

        return pedidoCriado;
    }

    public PedidosPaginadoView listarTodosPaginado(int pageNumber, int pageSize) {
        PedidosPaginadoView pedidosPaginadoView = pedidoRepository.listarTodosPaginado(pageNumber, pageSize);
        return pedidosPaginadoView;
    }

    public Map<FormaPagamento, Long> listarQuantidadeDePedidosPorFormaDePagamento() {
        final List<Pedido> todosOsPedidos = pedidoRepository.listarTodos();
        return agruparPedidoPorFormaDePagamento(todosOsPedidos);
    }

    private Map<FormaPagamento, Long> agruparPedidoPorFormaDePagamento(List<Pedido> todosOsPedidos) {
        // retorna um map com a forma de pagamento e a quantidade de pedidos. Map<FormaPagamento, Long>
        return todosOsPedidos.stream()
                .collect(Collectors.groupingBy(
                        Pedido::getFormaPagamento,
                        Collectors.counting()
                ));
    }

    public Pedido buscarPedido(Long pedidoId) {
        return pedidoRepository.buscarPedido(pedidoId);
    }

    @Transactional
    public void deletarPedido(Long pedidoId) {
        final Pedido pedido = pedidoRepository.buscarPedido(pedidoId);
        pedidoRepository.deletarPedido(pedido);
    }
}
