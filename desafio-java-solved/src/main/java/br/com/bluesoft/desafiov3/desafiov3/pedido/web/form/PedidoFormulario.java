package br.com.bluesoft.desafiov3.desafiov3.pedido.web.form;

import br.com.bluesoft.desafiov3.desafiov3.pedido.model.FormaPagamento;

import java.util.List;

public class PedidoFormulario {

    private boolean retiradaNaLoja;
    private boolean simularFalha;
    private FormaPagamento formaPagamento;
    private List<ItemPedidoFormulario> itens;

    public PedidoFormulario(boolean retiradaNaLoja, boolean simularFalha, FormaPagamento formaPagamento, List<ItemPedidoFormulario> itens) {
        this.retiradaNaLoja = retiradaNaLoja;
        this.simularFalha = simularFalha;
        this.formaPagamento = formaPagamento;
        this.itens = itens;
    }

    public boolean getRetiradaNaLoja() {
        return retiradaNaLoja;
    }

    public boolean getSimularFalha() {
        return simularFalha;
    }


    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public List<ItemPedidoFormulario> getItens() {
        return itens;
    }
}
