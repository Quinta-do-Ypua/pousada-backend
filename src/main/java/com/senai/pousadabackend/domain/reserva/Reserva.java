package com.senai.pousadabackend.domain.reserva;

import com.senai.pousadabackend.domain.cliente.Cliente;
import com.senai.pousadabackend.domain.complemento.Complemento;
import com.senai.pousadabackend.entity.EntityAudit;
import com.senai.pousadabackend.domain.quarto.Quarto;
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

    @Column
    private BigDecimal valorDaReserva;

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

    @ManyToMany
    @JoinTable(
            name = "reserva_complemento",
            joinColumns = @JoinColumn(name = "reserva_id"),
            inverseJoinColumns = @JoinColumn(name = "complemento_id")
    )
    private List<Complemento> complementos;


}
