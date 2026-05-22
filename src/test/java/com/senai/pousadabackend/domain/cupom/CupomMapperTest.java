package com.senai.pousadabackend.domain.cupom;

import com.senai.pousadabackend.domain.cupom.dto.CupomDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class CupomMapperTest {

    private CupomMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new CupomMapper();
    }

    @Test
    void toDTO_mapeiaCorretamente() {
        Cupom cupom = Cupom.builder()
                .id(1L)
                .nome("Desconto Verão")
                .codigo("VERAO10")
                .dataDeInicio(LocalDate.of(2024, 1, 1))
                .dataDeVencimento(LocalDate.of(2024, 12, 31))
                .porcentagemDeDesconto(10.0)
                .quantidadeMaximaDeUso(100)
                .build();

        CupomDTO dto = mapper.toDTO(cupom);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getNome()).isEqualTo("Desconto Verão");
        assertThat(dto.getCodigo()).isEqualTo("VERAO10");
        assertThat(dto.getDataDeInicio()).isEqualTo(LocalDate.of(2024, 1, 1));
        assertThat(dto.getDataDeVencimento()).isEqualTo(LocalDate.of(2024, 12, 31));
        assertThat(dto.getPorcentagemDeDesconto()).isEqualTo(10.0);
        assertThat(dto.getQuantidadeMaximaDeUso()).isEqualTo(100);
    }

    @Test
    void toEntity_mapeiaCorretamente() {
        CupomDTO dto = CupomDTO.builder()
                .id(2L)
                .nome("Promo Inverno")
                .codigo("INV20")
                .porcentagemDeDesconto(20.0)
                .quantidadeMaximaDeUso(50)
                .build();

        Cupom cupom = mapper.toEntity(dto);

        assertThat(cupom.getId()).isEqualTo(2L);
        assertThat(cupom.getNome()).isEqualTo("Promo Inverno");
        assertThat(cupom.getCodigo()).isEqualTo("INV20");
        assertThat(cupom.getPorcentagemDeDesconto()).isEqualTo(20.0);
        assertThat(cupom.getQuantidadeMaximaDeUso()).isEqualTo(50);
    }

    @Test
    void toEntity_semId_mapeiaComIdNulo() {
        CupomDTO dto = CupomDTO.builder().nome("Teste").codigo("TEST").build();

        Cupom cupom = mapper.toEntity(dto);

        assertThat(cupom.getId()).isNull();
    }
}
