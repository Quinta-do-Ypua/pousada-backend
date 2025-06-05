package com.senai.pousadabackend.domain.nota_fiscal.item_nf;

import com.senai.pousadabackend.core.entity.EntityAudit;
import com.senai.pousadabackend.domain.nota_fiscal.NotaFiscal;
import com.senai.pousadabackend.domain.nota_fiscal.item.Item;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "nota_fiscal_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class NotaFiscalItem extends EntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @JoinColumn(name = "nota_fiscal_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private NotaFiscal notaFiscal;

    @JoinColumn(name = "item_nf_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Item item;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private BigDecimal valorUnitario;

    @Column(nullable = false)
    private BigDecimal valorTotal;

}
