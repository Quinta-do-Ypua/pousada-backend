package com.senai.pousadabackend.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@FilterDef(name = "statusAtivo", parameters = @ParamDef(name = "statusParam", type = String.class))
@Filter(name = "statusAtivo", condition = "status = :statusParam")
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class EntityAudit {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    private LocalDateTime dataAlteracao;

    @CreatedBy
    @Column(updatable = false)
    private String criadoPor;

    @LastModifiedBy
    private String alteradoPor;

    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'ATIVO'")
    @Enumerated(EnumType.STRING)
    private Status status;

    public boolean isNovo() {
        return dataCriacao == null;
    }

}