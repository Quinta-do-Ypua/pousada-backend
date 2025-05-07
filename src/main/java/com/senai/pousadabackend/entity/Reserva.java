package com.senai.pousadabackend.entity;

import com.senai.pousadabackend.entity.enums.StatusDaReserva;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusDaReserva status;

    @Column
    private String observacao;

    @Column(nullable = false)
    private LocalDateTime checkIn;

    @Column(nullable = false)
    private LocalDateTime checkOut;

    @JoinColumn(name = "cliente_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    @JoinTable(
            name = "reserva_complemento",
            joinColumns = @JoinColumn(name = "reserva_id"),
            inverseJoinColumns = @JoinColumn(name = "complemento_id"))
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Complemento> complementos;


}
