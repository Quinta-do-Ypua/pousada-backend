package com.senai.pousadabackend.domain.temaSistema;

import com.senai.pousadabackend.core.entity.EntityAudit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;

    private String nomeEstabelecimento;
    private String corPrimaria;
    private String corSecundaria;
    private String corDoTexto;
    private String urlLogo;
    private String urlDaImagemPrincipal;

}
