package com.senai.pousadabackend.domain.complemento;

import com.senai.pousadabackend.domain.complemento.dto.ComplementoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ComplementoMapperTest {

    private ComplementoMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ComplementoMapper();
    }

    @Test
    void toDTO_mapeiaCorretamente() {
        Complemento complemento = Complemento.builder()
                .id(1L)
                .nome("Café da Manhã")
                .valor(BigDecimal.valueOf(35.00))
                .descricao("Café da manhã completo")
                .build();

        ComplementoDTO dto = mapper.toDTO(complemento);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getNome()).isEqualTo("Café da Manhã");
        assertThat(dto.getValor()).isEqualByComparingTo(BigDecimal.valueOf(35));
        assertThat(dto.getDescricao()).isEqualTo("Café da manhã completo");
    }

    @Test
    void toEntity_mapeiaCorretamente() {
        ComplementoDTO dto = ComplementoDTO.builder()
                .id(2L)
                .nome("Transfer")
                .valor(BigDecimal.valueOf(80.00))
                .descricao("Traslado aeroporto")
                .build();

        Complemento complemento = mapper.toEntity(dto);

        assertThat(complemento.getId()).isEqualTo(2L);
        assertThat(complemento.getNome()).isEqualTo("Transfer");
        assertThat(complemento.getValor()).isEqualByComparingTo(BigDecimal.valueOf(80));
    }

    @Test
    void complemento_isExistente_comId_retornaTrue() {
        Complemento c = Complemento.builder().id(1L).nome("Test").valor(BigDecimal.ONE).descricao("desc").build();
        assertThat(c.isExistente()).isTrue();
    }

    @Test
    void complemento_isExistente_semId_retornaFalse() {
        Complemento c = Complemento.builder().nome("Test").valor(BigDecimal.ONE).descricao("desc").build();
        assertThat(c.isExistente()).isFalse();
    }
}
