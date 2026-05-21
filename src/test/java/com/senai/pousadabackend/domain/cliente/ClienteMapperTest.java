package com.senai.pousadabackend.domain.cliente;

import com.senai.pousadabackend.core.enums.Sexo;
import com.senai.pousadabackend.domain.cliente.dto.ClienteDTO;
import com.senai.pousadabackend.domain.endereco.Endereco;
import com.senai.pousadabackend.domain.endereco.EnderecoMapper;
import com.senai.pousadabackend.domain.endereco.dto.EnderecoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ClienteMapperTest {

    private ClienteMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ClienteMapper(new EnderecoMapper());
    }

    private Endereco enderecoPadrao() {
        return Endereco.builder()
                .id(1L)
                .cidade("São Paulo")
                .estado("SP")
                .rua("Rua A")
                .cep("01000-000")
                .bairro("Centro")
                .numero("10")
                .build();
    }

    private Cliente clientePadrao() {
        return Cliente.builder()
                .id(1L)
                .nome("Ana Silva")
                .cpf("123.456.789-00")
                .email("ana@email.com")
                .celular("(11) 91234-5678")
                .dataDeNascimento(LocalDate.of(1990, 5, 15))
                .sexo(Sexo.FEMININO)
                .endereco(enderecoPadrao())
                .build();
    }

    @Test
    void toDTO_mapeiaCorretamente() {
        Cliente cliente = clientePadrao();

        ClienteDTO dto = mapper.toDTO(cliente);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getNome()).isEqualTo("Ana Silva");
        assertThat(dto.getCpf()).isEqualTo("123.456.789-00");
        assertThat(dto.getEmail()).isEqualTo("ana@email.com");
        assertThat(dto.getCelular()).isEqualTo("(11) 91234-5678");
        assertThat(dto.getDataDeNascimento()).isEqualTo(LocalDate.of(1990, 5, 15));
        assertThat(dto.getSexo()).isEqualTo(Sexo.FEMININO);
        assertThat(dto.getEndereco()).isNotNull();
        assertThat(dto.getEndereco().getCidade()).isEqualTo("São Paulo");
    }

    @Test
    void toEntity_mapeiaCorretamente() {
        EnderecoDTO enderecoDTO = EnderecoDTO.builder()
                .id(1L)
                .cidade("Campinas")
                .estado("SP")
                .rua("Rua B")
                .build();

        ClienteDTO dto = ClienteDTO.builder()
                .id(2L)
                .nome("Carlos Lima")
                .cpf("987.654.321-00")
                .email("carlos@email.com")
                .celular("(19) 98765-4321")
                .dataDeNascimento(LocalDate.of(1985, 3, 20))
                .sexo(Sexo.MASCULINO)
                .endereco(enderecoDTO)
                .build();

        Cliente cliente = mapper.toEntity(dto);

        assertThat(cliente.getId()).isEqualTo(2L);
        assertThat(cliente.getNome()).isEqualTo("Carlos Lima");
        assertThat(cliente.getCpf()).isEqualTo("987.654.321-00");
        assertThat(cliente.getEmail()).isEqualTo("carlos@email.com");
        assertThat(cliente.getSexo()).isEqualTo(Sexo.MASCULINO);
        assertThat(cliente.getEndereco()).isNotNull();
        assertThat(cliente.getEndereco().getCidade()).isEqualTo("Campinas");
    }

    @Test
    void toEntity_enderecoNulo_naoLancaExcecao() {
        ClienteDTO dto = ClienteDTO.builder()
                .id(1L)
                .nome("Sem Endereço")
                .cpf("111.222.333-44")
                .email("sem@email.com")
                .celular("(11) 00000-0000")
                .dataDeNascimento(LocalDate.of(2000, 1, 1))
                .sexo(Sexo.MASCULINO)
                .endereco(null)
                .build();

        Cliente cliente = mapper.toEntity(dto);

        assertThat(cliente.getEndereco()).isNull();
    }
}
