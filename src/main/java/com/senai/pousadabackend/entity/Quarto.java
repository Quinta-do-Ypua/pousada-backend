package com.senai.pousadabackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "quartos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Quarto extends EntityAudit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private Integer capacidade;

    @Column
    private Integer qtdCamaSolteiro;

    @Column
    private Integer qtdCamaCasal;

    @Column(nullable = false)
    private BigDecimal valorDiaria;

    @JoinTable(
            name = "quarto_complemento",
            joinColumns = @JoinColumn(name = "quarto_id"),
            inverseJoinColumns = @JoinColumn(name = "complemento_id"))
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Complemento> complementos;

    @Column
    private String observacao;

}
