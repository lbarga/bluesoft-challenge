package br.com.bluesoft.desafiov3.desafiov3.pedido.model;

import br.com.bluesoft.desafiov3.desafiov3.pedido.model.exception.Limite50ItensException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pedido {
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    final private List<ItemPedido> itens = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "retirada_na_loja")
    private boolean retiradaNaLoja;
    @Column(name = "forma_pagamento")
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    protected Pedido() {
    }

    public Pedido(boolean retiradaNaLoja, FormaPagamento formaPagamento) {
        this.retiradaNaLoja = retiradaNaLoja;
        this.formaPagamento = formaPagamento;
    }

    public Long getId() {
        return id;
    }

    public boolean getIsRetiradaNaLoja() {
        return retiradaNaLoja;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public void addItens(List<ItemPedido> itens) throws Limite50ItensException {
        double totalDeItems = itens
                .stream()
                .mapToDouble(ItemPedido::getQuantidade)
                .sum();

        if (totalDeItems > 50) {
            throw new Limite50ItensException();
        }

        this.itens.addAll(itens);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", retiradaNaLoja=" + retiradaNaLoja +
                ", formaPagamento=" + formaPagamento +
                ", itens=" + itens +
                '}';
    }
}