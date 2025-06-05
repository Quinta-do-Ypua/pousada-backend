package com.senai.pousadabackend.domain.amenidade;

import com.senai.pousadabackend.core.entity.EntityAudit;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "amenidades")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Amenidade extends EntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column
    private String icone;

}
