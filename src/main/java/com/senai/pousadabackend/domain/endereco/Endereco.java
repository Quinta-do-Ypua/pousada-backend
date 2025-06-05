package com.senai.pousadabackend.domain.endereco;

import com.senai.pousadabackend.core.entity.EntityAudit;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "enderecos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Endereco extends EntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String rua;

    @Column
    private String cep;

    @Column
    private String bairro;

    @Column
    private String numero;

    @Column
    private String complemento;

}
