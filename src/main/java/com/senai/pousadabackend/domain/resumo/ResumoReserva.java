package com.senai.pousadabackend.domain.resumo;

import com.senai.pousadabackend.core.entity.EntityAudit;
import com.senai.pousadabackend.domain.cliente.Cliente;
import com.senai.pousadabackend.domain.resumo.item_nf.ResumoReservaItem;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "resumo_reserva")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ResumoReserva extends EntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idNotaFiscal;

    @Column(nullable = false, unique = true)
    private String numero;

    @Column(nullable = false, updatable = false)
    private LocalDate dataCadastro;

    @JoinColumn(name = "cliente_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @OneToMany(mappedBy = "resumoReserva", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResumoReservaItem> itens;


}
