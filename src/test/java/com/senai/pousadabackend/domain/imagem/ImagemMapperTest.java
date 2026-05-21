package com.senai.pousadabackend.domain.imagem;

import com.senai.pousadabackend.domain.imagem.configuracao.ImagemConfiguracao;
import com.senai.pousadabackend.domain.imagem.configuracao.ImagemConfiguracaoMapper;
import com.senai.pousadabackend.domain.imagem.configuracao.dto.ImagemConfiguracaoDTO;
import com.senai.pousadabackend.domain.imagem.quarto.ImagemQuarto;
import com.senai.pousadabackend.domain.imagem.quarto.ImagemQuartoMapper;
import com.senai.pousadabackend.domain.imagem.quarto.dto.ImagemQuartoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ImagemMapperTest {

    private ImagemQuartoMapper quartoMapper;
    private ImagemConfiguracaoMapper configuracaoMapper;

    @BeforeEach
    void setUp() {
        quartoMapper = new ImagemQuartoMapper();
        configuracaoMapper = new ImagemConfiguracaoMapper();
    }

    @Test
    void imagemQuarto_toDTO_mapeiaCorretamente() {
        ImagemQuarto imagem = ImagemQuarto.builder()
                .id(1L)
                .url("http://bucket/foto.jpg")
                .fileId("file-123")
                .build();

        ImagemQuartoDTO dto = quartoMapper.toDTO(imagem);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getUrl()).isEqualTo("http://bucket/foto.jpg");
        assertThat(dto.getFileId()).isEqualTo("file-123");
    }

    @Test
    void imagemQuarto_toEntity_mapeiaCorretamente() {
        ImagemQuartoDTO dto = ImagemQuartoDTO.builder()
                .id(2L)
                .url("http://bucket/foto2.jpg")
                .fileId("file-456")
                .build();

        ImagemQuarto imagem = quartoMapper.toEntity(dto);

        assertThat(imagem.getId()).isEqualTo(2L);
        assertThat(imagem.getUrl()).isEqualTo("http://bucket/foto2.jpg");
        assertThat(imagem.getFileId()).isEqualTo("file-456");
    }

    @Test
    void imagemConfiguracao_toDTO_mapeiaCorretamente() {
        ImagemConfiguracao imagem = ImagemConfiguracao.builder()
                .id(10L)
                .url("http://bucket/logo.png")
                .fileId("logo-file-001")
                .build();

        ImagemConfiguracaoDTO dto = configuracaoMapper.toDTO(imagem);

        assertThat(dto.getId()).isEqualTo(10L);
        assertThat(dto.getUrl()).isEqualTo("http://bucket/logo.png");
        assertThat(dto.getFileId()).isEqualTo("logo-file-001");
    }

    @Test
    void imagemConfiguracao_toEntity_mapeiaCorretamente() {
        ImagemConfiguracaoDTO dto = ImagemConfiguracaoDTO.builder()
                .id(20L)
                .url("http://bucket/banner.png")
                .fileId("banner-file-002")
                .build();

        ImagemConfiguracao imagem = configuracaoMapper.toEntity(dto);

        assertThat(imagem.getId()).isEqualTo(20L);
        assertThat(imagem.getUrl()).isEqualTo("http://bucket/banner.png");
        assertThat(imagem.getFileId()).isEqualTo("banner-file-002");
    }
}
