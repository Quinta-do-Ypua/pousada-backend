package com.senai.pousadabackend.domain.resumo.item_nf;

import com.senai.pousadabackend.core.entity.EntityAudit;
import com.senai.pousadabackend.domain.resumo.ResumoReserva;
import com.senai.pousadabackend.domain.resumo.item.Item;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "resumo_reserva_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ResumoReservaItem extends EntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @JoinColumn(name = "resumo_reserva_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ResumoReserva resumoReserva;

    @JoinColumn(name = "item_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Item item;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private BigDecimal valorUnitario;

    @Column(nullable = false)
    private BigDecimal valorTotal;

}
