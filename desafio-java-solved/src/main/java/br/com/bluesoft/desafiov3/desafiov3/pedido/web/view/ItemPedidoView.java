package br.com.bluesoft.desafiov3.desafiov3.pedido.web.view;

import br.com.bluesoft.desafiov3.desafiov3.pedido.model.ItemPedido;

public class ItemPedidoView {
    private Long id;
    private Long pedidoId;
    private String descricaoProduto;
    private Double quantidade;

    protected ItemPedidoView() {
    }

    public ItemPedidoView(ItemPedido itemPedido) {
        this.id = itemPedido.getId();
        this.pedidoId = itemPedido.getPedido().getId();
        this.quantidade = itemPedido.getQuantidade();
        this.descricaoProduto = itemPedido.getDescricaoProduto();
    }

    public Long getId() {
        return id;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public Double getQuantidade() {
        return quantidade;
    }

}
