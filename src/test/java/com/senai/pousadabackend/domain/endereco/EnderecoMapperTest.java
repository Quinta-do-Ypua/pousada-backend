package com.senai.pousadabackend.domain.endereco;

import com.senai.pousadabackend.domain.endereco.dto.EnderecoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EnderecoMapperTest {

    private EnderecoMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new EnderecoMapper();
    }

    private Endereco enderecoPadrao() {
        return Endereco.builder()
                .id(1L)
                .cidade("São Paulo")
                .estado("SP")
                .rua("Rua das Flores")
                .cep("01310-100")
                .bairro("Centro")
                .numero("100")
                .complemento("Apto 5")
                .build();
    }

    @Test
    void toDTO_mapeiaCorretamente() {
        Endereco endereco = enderecoPadrao();

        EnderecoDTO dto = mapper.toDTO(endereco);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getCidade()).isEqualTo("São Paulo");
        assertThat(dto.getEstado()).isEqualTo("SP");
        assertThat(dto.getRua()).isEqualTo("Rua das Flores");
        assertThat(dto.getCep()).isEqualTo("01310-100");
        assertThat(dto.getBairro()).isEqualTo("Centro");
        assertThat(dto.getNumero()).isEqualTo("100");
        assertThat(dto.getComplemento()).isEqualTo("Apto 5");
    }

    @Test
    void toEntity_mapeiaCorretamente() {
        EnderecoDTO dto = EnderecoDTO.builder()
                .id(2L)
                .cidade("Rio de Janeiro")
                .estado("RJ")
                .rua("Av. Atlântica")
                .cep("22010-000")
                .bairro("Copacabana")
                .numero("200")
                .complemento("Cobertura")
                .build();

        Endereco endereco = mapper.toEntity(dto);

        assertThat(endereco.getId()).isEqualTo(2L);
        assertThat(endereco.getCidade()).isEqualTo("Rio de Janeiro");
        assertThat(endereco.getEstado()).isEqualTo("RJ");
        assertThat(endereco.getRua()).isEqualTo("Av. Atlântica");
        assertThat(endereco.getCep()).isEqualTo("22010-000");
        assertThat(endereco.getBairro()).isEqualTo("Copacabana");
        assertThat(endereco.getNumero()).isEqualTo("200");
        assertThat(endereco.getComplemento()).isEqualTo("Cobertura");
    }

    @Test
    void toDTO_semComplemento_retornaNulo() {
        Endereco endereco = Endereco.builder()
                .id(1L)
                .cidade("BH")
                .estado("MG")
                .rua("Rua X")
                .build();

        EnderecoDTO dto = mapper.toDTO(endereco);

        assertThat(dto.getComplemento()).isNull();
        assertThat(dto.getCidade()).isEqualTo("BH");
    }

    @Test
    void toEntity_semId_retornaIdNulo() {
        EnderecoDTO dto = EnderecoDTO.builder()
                .cidade("Curitiba")
                .estado("PR")
                .rua("Rua Y")
                .build();

        Endereco endereco = mapper.toEntity(dto);

        assertThat(endereco.getId()).isNull();
        assertThat(endereco.getCidade()).isEqualTo("Curitiba");
    }
}
