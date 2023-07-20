package br.com.bluesoft.desafiov3.desafiov3.pedido.model;

import javax.persistence.*;

@Entity
public class MovimentoEstoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pedido_id")
    private Long pedidoId;

    @Column(name = "quantidade_reservada")
    private Double quantidadeReservada;

    protected MovimentoEstoque() {
    }

    public MovimentoEstoque(Long pedidoId, Double quantidadeReservada) {
        this.pedidoId = pedidoId;
        this.quantidadeReservada = quantidadeReservada;
    }
}
