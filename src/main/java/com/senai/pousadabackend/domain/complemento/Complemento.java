package com.senai.pousadabackend.domain.complemento;

import com.senai.pousadabackend.core.entity.EntityAudit;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Complemento")
@Table(name = "complementos")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Complemento extends EntityAudit {

    @Id
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Transient
    public boolean isExistente() {
        return getId() != null;
    }
}
