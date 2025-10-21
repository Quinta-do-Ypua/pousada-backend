package com.senai.pousadabackend.domain.Imagem.configuracao;

import com.senai.pousadabackend.domain.configuracao.Configuracao;
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

    @JoinColumn(name = "configuracao_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Configuracao configuracao;

}
