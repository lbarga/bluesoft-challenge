package br.com.bluesoft.desafiov3.desafiov3.pedido.web.form;

public class ItemPedidoFormulario {
    private String descricaoProduto;
    private double quantidade;

    public ItemPedidoFormulario(String descricaoProduto, double quantidade) {
        this.descricaoProduto = descricaoProduto;
        this.quantidade = quantidade;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }


    public double getQuantidade() {
        return quantidade;
    }

}
