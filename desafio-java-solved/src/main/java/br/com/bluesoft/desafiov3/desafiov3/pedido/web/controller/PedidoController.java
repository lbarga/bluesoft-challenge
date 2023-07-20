package br.com.bluesoft.desafiov3.desafiov3.pedido.web.controller;

import br.com.bluesoft.desafiov3.desafiov3.pedido.business.PedidoService;
import br.com.bluesoft.desafiov3.desafiov3.pedido.model.FormaPagamento;
import br.com.bluesoft.desafiov3.desafiov3.pedido.model.Pedido;
import br.com.bluesoft.desafiov3.desafiov3.pedido.model.exception.EstoqueVazioException;
import br.com.bluesoft.desafiov3.desafiov3.pedido.model.exception.Limite50ItensException;
import br.com.bluesoft.desafiov3.desafiov3.pedido.web.form.PedidoFormulario;
import br.com.bluesoft.desafiov3.desafiov3.pedido.web.view.PedidoView;
import br.com.bluesoft.desafiov3.desafiov3.pedido.web.view.PedidosPaginadoView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private static final Logger logger = LoggerFactory.getLogger(PedidoController.class);
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoView novoPedido(@RequestBody PedidoFormulario pedidoFormulario)
            throws EstoqueVazioException, Limite50ItensException {
        logger.info("Criando novo pedido: {}", pedidoFormulario);
        Pedido pedido = pedidoService.novoPedido(pedidoFormulario);
        logger.info("Novo pedido criado com ID: {}", pedido.getId());
        return new PedidoView(pedido);
    }

    @GetMapping()
    public PedidosPaginadoView listarPedidos(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        logger.info("Listando todos os pedidos");
        PedidosPaginadoView pedidosPaginadoView = pedidoService.listarTodosPaginado(pageNumber, pageSize);
        return pedidosPaginadoView;
    }

    @GetMapping("/{pedidoId}")
    public ResponseEntity<?> obterPedidoPorId(@PathVariable Long pedidoId) {
        logger.info("Buscando pedido com ID: {}", pedidoId);
        final Pedido pedido = pedidoService.buscarPedido(pedidoId);

        if (pedido == null) {
            logger.info("Pedido com ID {} não encontrado", pedidoId);
            return ResponseEntity.notFound().build();
        }

        logger.info("Pedido com ID {} encontrado", pedidoId);
        return ResponseEntity.ok(new PedidoView(pedido));
    }

    @DeleteMapping(value = "/{pedidoId}")
    public void deletarPedido(@PathVariable Long pedidoId) {
        logger.info("Excluindo pedido com ID: {}", pedidoId);
        pedidoService.deletarPedido(pedidoId);
        logger.info("Pedido com ID {} excluído", pedidoId);
    }

    @GetMapping("/forma-pagamentos")
    public Map<FormaPagamento, Long> listarQuantidadeDePedidosPorFormaDePagamento() {
        logger.info("Listando quantidade de pedidos por forma de pagamento");
        return pedidoService.listarQuantidadeDePedidosPorFormaDePagamento();
    }
}
