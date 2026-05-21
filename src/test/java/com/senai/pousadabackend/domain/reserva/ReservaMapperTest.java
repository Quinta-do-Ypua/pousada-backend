package com.senai.pousadabackend.domain.reserva;

import com.senai.pousadabackend.core.enums.Sexo;
import com.senai.pousadabackend.core.enums.StatusDaReserva;
import com.senai.pousadabackend.domain.amenidade.AmenidadeMapper;
import com.senai.pousadabackend.domain.cliente.Cliente;
import com.senai.pousadabackend.domain.cliente.ClienteMapper;
import com.senai.pousadabackend.domain.cliente.dto.ClienteDTO;
import com.senai.pousadabackend.domain.complemento.Complemento;
import com.senai.pousadabackend.domain.complemento.ComplementoMapper;
import com.senai.pousadabackend.domain.complemento.dto.ComplementoDTO;
import com.senai.pousadabackend.domain.endereco.Endereco;
import com.senai.pousadabackend.domain.endereco.EnderecoMapper;
import com.senai.pousadabackend.domain.quarto.Quarto;
import com.senai.pousadabackend.domain.quarto.QuartoMapper;
import com.senai.pousadabackend.domain.quarto.dto.QuartoDTO;
import com.senai.pousadabackend.domain.reserva.dto.ReservaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReservaMapperTest {

    private ReservaMapper mapper;

    @BeforeEach
    void setUp() {
        AmenidadeMapper amenidadeMapper = new AmenidadeMapper();
        QuartoMapper quartoMapper = new QuartoMapper(amenidadeMapper);
        ComplementoMapper complementoMapper = new ComplementoMapper();
        EnderecoMapper enderecoMapper = new EnderecoMapper();
        ClienteMapper clienteMapper = new ClienteMapper(enderecoMapper);
        mapper = new ReservaMapper(quartoMapper, complementoMapper, clienteMapper);
    }

    private Cliente clientePadrao() {
        return Cliente.builder()
                .id(1L)
                .nome("João")
                .cpf("000.000.000-00")
                .email("joao@email.com")
                .celular("(11) 99999-9999")
                .dataDeNascimento(LocalDate.of(1990, 1, 1))
                .sexo(Sexo.MASCULINO)
                .endereco(Endereco.builder().id(1L).cidade("SP").estado("SP").rua("Rua A").build())
                .build();
    }

    private Quarto quartoPadrao() {
        return Quarto.builder()
                .id(1L)
                .nome("Suite 01")
                .capacidade(2)
                .valorDiaria(BigDecimal.valueOf(200))
                .build();
    }

    private Reserva reservaPadrao() {
        LocalDateTime checkIn = LocalDateTime.now().plusDays(5);
        LocalDateTime checkOut = checkIn.plusDays(3);
        return Reserva.builder()
                .id(1L)
                .quarto(quartoPadrao())
                .cliente(clientePadrao())
                .checkIn(checkIn)
                .checkOut(checkOut)
                .statusDaReserva(StatusDaReserva.ABERTA)
                .valorDaReserva(BigDecimal.valueOf(600))
                .observacao("Vista para o mar")
                .complementos(List.of())
                .build();
    }

    @Test
    void toDTO_mapeiaCorretamente() {
        Reserva reserva = reservaPadrao();

        ReservaDTO dto = mapper.toDTO(reserva);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getStatusDaReserva()).isEqualTo(StatusDaReserva.ABERTA);
        assertThat(dto.getValorDaReserva()).isEqualByComparingTo(BigDecimal.valueOf(600));
        assertThat(dto.getObservacao()).isEqualTo("Vista para o mar");
        assertThat(dto.getQuarto().getId()).isEqualTo(1L);
        assertThat(dto.getCliente().getId()).isEqualTo(1L);
        assertThat(dto.getComplementos()).isEmpty();
    }

    @Test
    void toDTO_comComplementos_mapeia() {
        Reserva reserva = reservaPadrao();
        Complemento comp = Complemento.builder()
                .id(1L)
                .nome("Café da Manhã")
                .valor(BigDecimal.valueOf(35))
                .descricao("Café completo")
                .build();
        reserva.setComplementos(List.of(comp));

        ReservaDTO dto = mapper.toDTO(reserva);

        assertThat(dto.getComplementos()).hasSize(1);
        assertThat(dto.getComplementos().getFirst().getNome()).isEqualTo("Café da Manhã");
    }

    @Test
    void toDTO_complementosNulos_retornaListaVazia() {
        Reserva reserva = reservaPadrao();
        reserva.setComplementos(null);

        ReservaDTO dto = mapper.toDTO(reserva);

        assertThat(dto.getComplementos()).isEmpty();
    }

    @Test
    void toEntity_mapeiaCorretamente() {
        LocalDateTime checkIn = LocalDateTime.now().plusDays(5);
        LocalDateTime checkOut = checkIn.plusDays(3);

        ReservaDTO dto = ReservaDTO.builder()
                .id(1L)
                .statusDaReserva(StatusDaReserva.ABERTA)
                .valorDaReserva(BigDecimal.valueOf(600))
                .checkIn(checkIn)
                .checkOut(checkOut)
                .observacao("Vista para o mar")
                .quarto(QuartoDTO.builder().id(1L).nome("Suite 01").capacidade(2).valorDiaria(BigDecimal.valueOf(200)).build())
                .cliente(ClienteDTO.builder().id(1L).nome("João").cpf("000.000.000-00").email("joao@email.com")
                        .celular("(11) 99999-9999").sexo(com.senai.pousadabackend.core.enums.Sexo.MASCULINO)
                        .dataDeNascimento(LocalDate.of(1990, 1, 1)).build())
                .complementos(List.of())
                .build();

        Reserva reserva = mapper.toEntity(dto);

        assertThat(reserva.getId()).isEqualTo(1L);
        assertThat(reserva.getStatusDaReserva()).isEqualTo(StatusDaReserva.ABERTA);
        assertThat(reserva.getValorDaReserva()).isEqualByComparingTo(BigDecimal.valueOf(600));
        assertThat(reserva.getQuarto().getId()).isEqualTo(1L);
        assertThat(reserva.getCliente().getId()).isEqualTo(1L);
        assertThat(reserva.getComplementos()).isEmpty();
    }

    @Test
    void toEntity_comComplementos_mapeiaLista() {
        LocalDateTime checkIn = LocalDateTime.now().plusDays(5);

        ReservaDTO dto = ReservaDTO.builder()
                .id(2L)
                .checkIn(checkIn)
                .checkOut(checkIn.plusDays(2))
                .quarto(QuartoDTO.builder().id(1L).nome("Q1").capacidade(2).valorDiaria(BigDecimal.valueOf(200)).build())
                .cliente(ClienteDTO.builder().id(1L).nome("Ana").cpf("000.000.000-00").email("ana@email.com")
                        .celular("(11) 88888-8888").sexo(com.senai.pousadabackend.core.enums.Sexo.FEMININO)
                        .dataDeNascimento(LocalDate.of(1992, 3, 15)).build())
                .complementos(List.of(
                        ComplementoDTO.builder().id(1L).nome("Café").valor(BigDecimal.valueOf(35)).build()
                ))
                .build();

        Reserva reserva = mapper.toEntity(dto);

        assertThat(reserva.getComplementos()).hasSize(1);
        assertThat(reserva.getComplementos().getFirst().getNome()).isEqualTo("Café");
    }
}
