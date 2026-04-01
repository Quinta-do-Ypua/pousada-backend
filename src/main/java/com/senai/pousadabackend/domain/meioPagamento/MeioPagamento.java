package com.senai.pousadabackend.domain.meioPagamento;

import com.senai.pousadabackend.core.entity.EntityAudit;
import com.senai.pousadabackend.core.enums.TipoPagamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "MeioPagamento")
@Table(name = "meios_pagamento")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class MeioPagamento extends EntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 200)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pagamento", nullable = false)
    private TipoPagamento tipo;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(name = "taxa_percentual", precision = 5, scale = 2)
    private BigDecimal taxaPercentual;

    @Column(name = "taxa_fixa", precision = 10, scale = 2)
    private BigDecimal taxaFixa;

    @Column(name = "prazo_compensacao")
    private Integer prazoCompensacao;

    @Column(name = "permite_parcelamento")
    private Boolean permiteParcelamento = false;

    @Column(name = "max_parcelas")
    private Integer maxParcelas;
}
