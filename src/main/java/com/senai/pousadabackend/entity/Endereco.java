package com.senai.pousadabackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "enderecos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Endereco {

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
