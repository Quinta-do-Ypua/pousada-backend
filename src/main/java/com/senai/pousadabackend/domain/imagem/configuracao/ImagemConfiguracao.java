package com.senai.pousadabackend.domain.imagem.configuracao;

import com.senai.pousadabackend.domain.temaSistema.TemaSistema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "imagens_configuracoes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ImagemConfiguracao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private Long id;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "file_id", nullable = false)
    private String fileId;

    @JoinColumn(name = "tema_sistema_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private TemaSistema tema;

}
