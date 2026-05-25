package com.senai.pousadabackend.domain.tema;

import com.senai.pousadabackend.core.entity.EntityAudit;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tema")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Tema extends EntityAudit {

    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "cor_primaria", nullable = false)
    @Builder.Default
    private String buttonColor = "rgba(139, 69, 19, 0.85)";

    @Column(name = "cor_secundaria", nullable = false)
    @Builder.Default
    private String titleColor = "rgb(80, 19, 19)";

    @Column(name = "cor_texto", nullable = false)
    @Builder.Default
    private String textColor = "#555555";

    @Column(name = "logo", columnDefinition = "TEXT")
    private String logo;

    @Version
    private Long version;

    public static Tema comDefaults() {
        return Tema.builder()
                .id(1L)
                .build();
    }

}
