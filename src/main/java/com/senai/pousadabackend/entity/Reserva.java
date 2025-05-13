package com.senai.pousadabackend.entity;

import com.senai.pousadabackend.entity.enums.StatusDaReserva;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Table(name = "reservas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Reserva extends EntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @JoinColumn(name = "quarto_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Quarto quarto;

    @Column(nullable = false)
    private BigDecimal valorTotalDoQuarto;

    @Column(nullable = false)
    private BigDecimal valorDaDiariaDoQuarto;

    @Column(nullable = false)
    private BigDecimal valorDaReserva;

    @Column
    private BigDecimal valorComplementos;

    @Column(name = "status_reserva", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusDaReserva statusDaReserva;

    @Column
    private String observacao;

    @Column(nullable = false)
    private LocalDateTime checkIn;

    @Column(nullable = false)
    private LocalDateTime checkOut;

    @JoinColumn(name = "cliente_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

}
