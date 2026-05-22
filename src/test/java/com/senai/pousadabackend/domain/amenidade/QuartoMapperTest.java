package com.senai.pousadabackend.domain.amenidade;

import com.senai.pousadabackend.domain.quarto.Quarto;
import com.senai.pousadabackend.domain.quarto.QuartoMapper;
import com.senai.pousadabackend.domain.quarto.dto.QuartoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QuartoMapperTest {

    private QuartoMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new QuartoMapper(new AmenidadeMapper());
    }

    @Test
    void toDTO_semAmenidades_mapeiaCorretamente() {
        Quarto quarto = Quarto.builder()
                .id(1L)
                .nome("Suite Master")
                .capacidade(2)
                .qtdCamaCasal(1)
                .qtdCamaSolteiro(0)
                .valorDiaria(BigDecimal.valueOf(200))
                .observacao("Vista para o mar")
                .build();

        QuartoDTO dto = mapper.toDTO(quarto);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getNome()).isEqualTo("Suite Master");
        assertThat(dto.getCapacidade()).isEqualTo(2);
        assertThat(dto.getValorDiaria()).isEqualByComparingTo(BigDecimal.valueOf(200));
        assertThat(dto.getObservacao()).isEqualTo("Vista para o mar");
        assertThat(dto.getAmenidades()).isNull();
    }

    @Test
    void toDTO_comAmenidades_mapeiaCorretamente() {
        Amenidade amenidade = Amenidade.builder().id(1L).nome("Piscina").build();
        Quarto quarto = Quarto.builder()
                .id(1L)
                .nome("Suite")
                .capacidade(2)
                .valorDiaria(BigDecimal.valueOf(150))
                .amenidades(List.of(amenidade))
                .build();

        QuartoDTO dto = mapper.toDTO(quarto);

        assertThat(dto.getAmenidades()).hasSize(1);
        assertThat(dto.getAmenidades().getFirst().getNome()).isEqualTo("Piscina");
    }

    @Test
    void toEntity_semAmenidades_mapeiaCorretamente() {
        QuartoDTO dto = QuartoDTO.builder()
                .id(2L)
                .nome("Quarto Standard")
                .capacidade(1)
                .qtdCamaSolteiro(1)
                .valorDiaria(BigDecimal.valueOf(100))
                .build();

        Quarto quarto = mapper.toEntity(dto);

        assertThat(quarto.getId()).isEqualTo(2L);
        assertThat(quarto.getNome()).isEqualTo("Quarto Standard");
        assertThat(quarto.getCapacidade()).isEqualTo(1);
        assertThat(quarto.getAmenidades()).isNull();
    }

    @Test
    void toEntity_comAmenidades_mapeiaCorretamente() {
        var amenidadeDto = com.senai.pousadabackend.domain.amenidade.dto.AmenidadeDto.builder()
                .id(1L).nome("Academia").build();

        QuartoDTO dto = QuartoDTO.builder()
                .id(1L)
                .nome("Suite")
                .capacidade(2)
                .valorDiaria(BigDecimal.valueOf(180))
                .amenidades(List.of(amenidadeDto))
                .build();

        Quarto quarto = mapper.toEntity(dto);

        assertThat(quarto.getAmenidades()).hasSize(1);
        assertThat(quarto.getAmenidades().getFirst().getNome()).isEqualTo("Academia");
    }
}
