package com.senai.pousadabackend.domain.amenidade;

import com.senai.pousadabackend.domain.amenidade.dto.AmenidadeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class AmenidadeMapperTest {

    private AmenidadeMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new AmenidadeMapper();
    }

    @Test
    void toDTO_mapeiaCorretamente() {
        Amenidade amenidade = Amenidade.builder()
                .id(1L)
                .nome("Piscina")
                .icone("pool-icon")
                .build();

        AmenidadeDto dto = mapper.toDTO(amenidade);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getNome()).isEqualTo("Piscina");
        assertThat(dto.getIcone()).isEqualTo("pool-icon");
    }

    @Test
    void toDTO_semIcone_retornaNulo() {
        Amenidade amenidade = Amenidade.builder()
                .id(2L)
                .nome("WiFi")
                .build();

        AmenidadeDto dto = mapper.toDTO(amenidade);

        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getNome()).isEqualTo("WiFi");
        assertThat(dto.getIcone()).isNull();
    }

    @Test
    void toEntity_mapeiaCorretamente() {
        AmenidadeDto dto = AmenidadeDto.builder()
                .id(1L)
                .nome("Academia")
                .icone("gym-icon")
                .build();

        Amenidade amenidade = mapper.toEntity(dto);

        assertThat(amenidade.getId()).isEqualTo(1L);
        assertThat(amenidade.getNome()).isEqualTo("Academia");
        assertThat(amenidade.getIcone()).isEqualTo("gym-icon");
    }

    @Test
    void toEntity_semId_mapeiaComIdNulo() {
        AmenidadeDto dto = AmenidadeDto.builder()
                .nome("Estacionamento")
                .build();

        Amenidade amenidade = mapper.toEntity(dto);

        assertThat(amenidade.getId()).isNull();
        assertThat(amenidade.getNome()).isEqualTo("Estacionamento");
    }

    @Test
    void toDTO_entidadeComAuditoria_naoPropagaAuditoria() {
        Amenidade amenidade = Amenidade.builder()
                .id(1L)
                .nome("Sauna")
                .build();
        amenidade.setDataCriacao(LocalDateTime.now());
        amenidade.setCriadoPor("admin");

        AmenidadeDto dto = mapper.toDTO(amenidade);

        assertThat(dto.getNome()).isEqualTo("Sauna");
    }
}
