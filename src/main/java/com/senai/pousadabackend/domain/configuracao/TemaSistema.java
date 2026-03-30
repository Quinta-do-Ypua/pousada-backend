package com.senai.pousadabackend.domain.configuracao;

import com.senai.pousadabackend.core.entity.EntityAudit;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tema_sistema")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class TemaSistema extends EntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeEstabelecimento;
    private String corPrimaria;
    private String corSecundaria;
    private String corDoTexto;
    private String urlLogo;
    private String urlDaImagemPrincipal;

}
