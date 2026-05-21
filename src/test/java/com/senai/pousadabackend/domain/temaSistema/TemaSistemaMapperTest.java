package com.senai.pousadabackend.domain.temaSistema;

import com.senai.pousadabackend.domain.temaSistema.dto.TemaSistemaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TemaSistemaMapperTest {

    private TemaSistemaMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new TemaSistemaMapper();
    }

    private TemaSistema temaPadrao() {
        return TemaSistema.builder()
                .id(1L)
                .nomeEstabelecimento("Pousada do Vale")
                .corPrimaria("#1A2B3C")
                .corSecundaria("#FFFFFF")
                .corDoTexto("#000000")
                .urlLogo("http://logo.png")
                .urlDaImagemPrincipal("http://banner.jpg")
                .build();
    }

    @Test
    void toDTO_mapeiaCorretamente() {
        TemaSistema tema = temaPadrao();

        TemaSistemaDTO dto = mapper.toDTO(tema);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getNomeEstabelecimento()).isEqualTo("Pousada do Vale");
        assertThat(dto.getCorPrimaria()).isEqualTo("#1A2B3C");
        assertThat(dto.getCorSecundaria()).isEqualTo("#FFFFFF");
        assertThat(dto.getCorDoTexto()).isEqualTo("#000000");
        assertThat(dto.getUrlLogo()).isEqualTo("http://logo.png");
        assertThat(dto.getUrlDaImagemPrincipal()).isEqualTo("http://banner.jpg");
    }

    @Test
    void toDTO_nulo_retornaNulo() {
        assertThat(mapper.toDTO(null)).isNull();
    }

    @Test
    void toEntity_mapeiaCorretamente() {
        TemaSistemaDTO dto = TemaSistemaDTO.builder()
                .id(2L)
                .nomeEstabelecimento("Eco Resort")
                .corPrimaria("#00FF00")
                .corSecundaria("#FF0000")
                .corDoTexto("#0000FF")
                .urlLogo("http://eco-logo.png")
                .urlDaImagemPrincipal("http://eco-banner.jpg")
                .build();

        TemaSistema tema = mapper.toEntity(dto);

        assertThat(tema.getId()).isEqualTo(2L);
        assertThat(tema.getNomeEstabelecimento()).isEqualTo("Eco Resort");
        assertThat(tema.getCorPrimaria()).isEqualTo("#00FF00");
    }

    @Test
    void toEntity_nulo_retornaNulo() {
        assertThat(mapper.toEntity(null)).isNull();
    }

    @Test
    void toDTOList_mapeiaLista() {
        List<TemaSistema> lista = List.of(temaPadrao(), temaPadrao());

        List<TemaSistemaDTO> resultado = mapper.toDTOList(lista);

        assertThat(resultado).hasSize(2);
    }

    @Test
    void toDTOList_listaNula_retornaNulo() {
        assertThat(mapper.toDTOList(null)).isNull();
    }

    @Test
    void updateEntityFromDTO_atualizaApenasCamposNaoNulos() {
        TemaSistema tema = temaPadrao();
        TemaSistemaDTO dto = TemaSistemaDTO.builder()
                .nomeEstabelecimento("Novo Nome")
                .corPrimaria("#AABBCC")
                .build();

        mapper.updateEntityFromDTO(dto, tema);

        assertThat(tema.getNomeEstabelecimento()).isEqualTo("Novo Nome");
        assertThat(tema.getCorPrimaria()).isEqualTo("#AABBCC");
        assertThat(tema.getCorSecundaria()).isEqualTo("#FFFFFF"); // unchanged
    }

    @Test
    void updateEntityFromDTO_dtoNulo_semExcecao() {
        TemaSistema tema = temaPadrao();
        mapper.updateEntityFromDTO(null, tema);
        assertThat(tema.getNomeEstabelecimento()).isEqualTo("Pousada do Vale");
    }

    @Test
    void updateEntityFromDTO_entidadeNula_semExcecao() {
        TemaSistemaDTO dto = TemaSistemaDTO.builder().nomeEstabelecimento("Teste").build();
        mapper.updateEntityFromDTO(dto, null);
    }

    @Test
    void updateEntityFromDTO_todosOsCampos() {
        TemaSistema tema = temaPadrao();
        TemaSistemaDTO dto = TemaSistemaDTO.builder()
                .nomeEstabelecimento("Atualizado")
                .corPrimaria("#111")
                .corSecundaria("#222")
                .corDoTexto("#333")
                .urlLogo("http://novo-logo.png")
                .urlDaImagemPrincipal("http://nova-imagem.jpg")
                .build();

        mapper.updateEntityFromDTO(dto, tema);

        assertThat(tema.getNomeEstabelecimento()).isEqualTo("Atualizado");
        assertThat(tema.getCorDoTexto()).isEqualTo("#333");
        assertThat(tema.getUrlLogo()).isEqualTo("http://novo-logo.png");
        assertThat(tema.getUrlDaImagemPrincipal()).isEqualTo("http://nova-imagem.jpg");
    }
}
