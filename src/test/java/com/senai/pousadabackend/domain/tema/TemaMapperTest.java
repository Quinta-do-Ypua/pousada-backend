package com.senai.pousadabackend.domain.tema;

import com.senai.pousadabackend.domain.tema.dto.TemaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TemaMapperTest {

    private TemaMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new TemaMapper();
    }

    @Test
    void toDTO_mapeiaCorretamente() {
        Tema tema = Tema.builder()
                .id(1L)
                .primaryColor("#FF0000")
                .secondaryColor("#00FF00")
                .textColor("#000000")
                .grayBg("#CCCCCC")
                .graySecondaryBg("#EEEEEE")
                .logo("http://example.com/logo.png")
                .loginImage("http://example.com/login.png")
                .build();

        TemaDTO dto = mapper.toDTO(tema);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getPrimaryColor()).isEqualTo("#FF0000");
        assertThat(dto.getSecondaryColor()).isEqualTo("#00FF00");
        assertThat(dto.getTextColor()).isEqualTo("#000000");
        assertThat(dto.getGrayBg()).isEqualTo("#CCCCCC");
        assertThat(dto.getGraySecondaryBg()).isEqualTo("#EEEEEE");
        assertThat(dto.getLogo()).isEqualTo("http://example.com/logo.png");
        assertThat(dto.getLoginImage()).isEqualTo("http://example.com/login.png");
    }

    @Test
    void toDTO_semCamposOpcionais_retornaNull() {
        Tema tema = Tema.builder()
                .id(1L)
                .primaryColor("#FF0000")
                .secondaryColor("#00FF00")
                .textColor("#333333")
                .grayBg("#DDDDDD")
                .graySecondaryBg("#EEEEEE")
                .build();

        TemaDTO dto = mapper.toDTO(tema);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getLogo()).isNull();
        assertThat(dto.getLoginImage()).isNull();
    }

    @Test
    void toEntity_mapeiaCorretamente() {
        TemaDTO dto = TemaDTO.builder()
                .id(2L)
                .primaryColor("rgba(139, 69, 19, 0.85)")
                .secondaryColor("rgb(80, 19, 19)")
                .textColor("#555555")
                .grayBg("#EEEEEE")
                .graySecondaryBg("#F1F3F6")
                .logo("http://example.com/logo2.png")
                .loginImage("http://example.com/login2.png")
                .build();

        Tema tema = mapper.toEntity(dto);

        assertThat(tema.getId()).isEqualTo(2L);
        assertThat(tema.getPrimaryColor()).isEqualTo("rgba(139, 69, 19, 0.85)");
        assertThat(tema.getSecondaryColor()).isEqualTo("rgb(80, 19, 19)");
        assertThat(tema.getTextColor()).isEqualTo("#555555");
        assertThat(tema.getGrayBg()).isEqualTo("#EEEEEE");
        assertThat(tema.getGraySecondaryBg()).isEqualTo("#F1F3F6");
        assertThat(tema.getLogo()).isEqualTo("http://example.com/logo2.png");
        assertThat(tema.getLoginImage()).isEqualTo("http://example.com/login2.png");
    }

    @Test
    void toEntity_semId_retornaIdNulo() {
        TemaDTO dto = TemaDTO.builder()
                .primaryColor("#AA0000")
                .secondaryColor("#0000AA")
                .textColor("#111111")
                .grayBg("#BBBBBB")
                .graySecondaryBg("#CCCCCC")
                .build();

        Tema tema = mapper.toEntity(dto);

        assertThat(tema.getId()).isNull();
        assertThat(tema.getPrimaryColor()).isEqualTo("#AA0000");
    }

    @Test
    void toDTO_comDefaults_mapeiaCorretamente() {
        Tema tema = Tema.comDefaults();

        TemaDTO dto = mapper.toDTO(tema);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getPrimaryColor()).isNotNull();
        assertThat(dto.getSecondaryColor()).isNotNull();
    }
}
