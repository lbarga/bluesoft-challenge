package br.com.bluesoft.desafiov3.desafiov3.pedido.web.view;

import br.com.bluesoft.desafiov3.desafiov3.pedido.model.Pedido;

import java.util.List;

public class PedidosPaginadoView {
    private List<Pedido> pedidos;

    private Long totalPedidos;

    protected PedidosPaginadoView() {
    }

    public PedidosPaginadoView(List<Pedido> pedidos, Long totalPedidos) {
        this.pedidos = pedidos;
        this.totalPedidos = totalPedidos;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public Long getTotalPedidos() {
        return totalPedidos;
    }
}
