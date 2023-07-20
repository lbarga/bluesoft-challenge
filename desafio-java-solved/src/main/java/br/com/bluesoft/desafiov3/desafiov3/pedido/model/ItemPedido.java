package br.com.bluesoft.desafiov3.desafiov3.pedido.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "item_pedido")
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Pedido pedido;

    @Column(name = "descricao_produto")
    private String descricaoProduto;

    private Double quantidade;

    protected ItemPedido() {
    }

    public ItemPedido(String descricaoProduto, Double quantidade, Pedido pedido) {
        this.descricaoProduto = descricaoProduto;
        this.quantidade = quantidade;
        this.pedido = pedido;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public Long getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }
    
    @Override
    public String toString() {
        return "ItemPedido{" +
                "id=" + id +
                ", descricaoProduto='" + descricaoProduto + '\'' +
                ", quantidade=" + quantidade +
                '}';
    }
}
