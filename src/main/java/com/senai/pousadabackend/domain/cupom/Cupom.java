package com.senai.pousadabackend.domain.cupom;

import com.senai.pousadabackend.core.entity.EntityAudit;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Cupom")
@Table(name = "cupons")
public class Cupom extends EntityAudit {

    @Id
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false)
    private String codigo;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "data-inicio", nullable = false)
    private LocalDate dataDeInicio;

    @Column(name = "data-vencimento", nullable = false)
    private LocalDate dataDeVencimento;

    @Column(name = "porcentagem-desconto", nullable = false)
    private Double porcentagemDeDesconto;

    @Column(name = "quantidade-maxima-uso", nullable = false)
    private Integer quantidadeMaximaDeUso;

    @Transient
    public boolean isExistente() {
        return getId() == null;
    }
}
