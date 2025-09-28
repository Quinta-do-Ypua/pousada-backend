package com.senai.pousadabackend.domain.usuario;

import com.senai.pousadabackend.core.entity.EntityAudit;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Usuario")
@Table(name = "usuarios")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Usuario extends EntityAudit {

    @Id
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Transient
    private String senha;

    @Column(name = "keycloak_id", nullable = false)
    private String keycloakId;

    private List<String> roles;

    @Transient
    public boolean isExistente() {
        return getId() == null;
    }
}
