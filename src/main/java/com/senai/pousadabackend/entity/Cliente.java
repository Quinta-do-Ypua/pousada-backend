package com.senai.pousadabackend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senai.pousadabackend.entity.enums.Sexo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "clientes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Cliente extends EntityAudit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String celular;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false, name = "data_de_nascimento")
    private LocalDate dataDeNascimento;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @JoinColumn(name = "endereco_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Endereco endereco;

}
