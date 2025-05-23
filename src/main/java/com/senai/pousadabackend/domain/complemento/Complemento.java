package com.senai.pousadabackend.domain.complemento;

import com.senai.pousadabackend.entity.EntityAudit;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

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

    @Column(name = "nome", nullable = false)
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
