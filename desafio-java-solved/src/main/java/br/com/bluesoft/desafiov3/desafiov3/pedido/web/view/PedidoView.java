package br.com.bluesoft.desafiov3.desafiov3.pedido.web.view;

import br.com.bluesoft.desafiov3.desafiov3.pedido.model.FormaPagamento;
import br.com.bluesoft.desafiov3.desafiov3.pedido.model.Pedido;

import java.util.List;
import java.util.stream.Collectors;

public class PedidoView {
    public List<ItemPedidoView> itens;
    private Long id;
    private boolean retiradaNaLoja;
    private FormaPagamento formaPagamento;

    protected PedidoView() {
    }

    public PedidoView(Pedido pedido) {
        this.id = pedido.getId();
        this.retiradaNaLoja = pedido.getIsRetiradaNaLoja();
        this.formaPagamento = pedido.getFormaPagamento();
        this.itens = pedido
                .getItens()
                .stream()
                .map(ItemPedidoView::new)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public boolean isRetiradaNaLoja() {
        return retiradaNaLoja;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public List<ItemPedidoView> getItens() {
        return itens;
    }
}
