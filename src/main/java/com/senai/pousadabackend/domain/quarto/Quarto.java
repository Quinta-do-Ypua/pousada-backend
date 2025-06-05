package com.senai.pousadabackend.domain.quarto;

import com.senai.pousadabackend.core.entity.EntityAudit;
import com.senai.pousadabackend.domain.amenidade.Amenidade;
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
public class Quarto extends EntityAudit {

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

    @Column(length = 1000)
    private String observacao;

    @ManyToMany
    @JoinTable(
            name = "quarto_amenidade",
            joinColumns = @JoinColumn(name = "quarto_id"),
            inverseJoinColumns = @JoinColumn(name = "amenidade_id")
    )
    private List<Amenidade> amenidades;

}
