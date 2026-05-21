package com.senai.pousadabackend.domain.reserva;

import com.senai.pousadabackend.core.enums.StatusDaReserva;
import com.senai.pousadabackend.domain.cliente.Cliente;
import com.senai.pousadabackend.domain.cliente.ClienteService;
import com.senai.pousadabackend.domain.complemento.ComplementoMapper;
import com.senai.pousadabackend.domain.complemento.dto.ComplementoDTO;
import com.senai.pousadabackend.domain.quarto.Quarto;
import com.senai.pousadabackend.domain.quarto.QuartoService;
import com.senai.pousadabackend.domain.reserva.dto.ReservaResumidaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservaResumidaMapperTest {

    @Mock
    private QuartoService quartoService;

    @Mock
    private ClienteService clienteService;

    private ReservaResumidaMapper mapper;

    @BeforeEach
    void setUp() {
        ComplementoMapper complementoMapper = new ComplementoMapper();
        mapper = new ReservaResumidaMapper(quartoService, clienteService, complementoMapper);
    }

    @Test
    void toReserva_mapeiaCorretamente() {
        LocalDateTime checkIn = LocalDateTime.now().plusDays(5);
        LocalDateTime checkOut = checkIn.plusDays(3);

        Quarto quarto = new Quarto();
        quarto.setId(1L);
        Cliente cliente = new Cliente();
        cliente.setId(2L);

        when(quartoService.buscarPorId(1L)).thenReturn(quarto);
        when(clienteService.buscarPorId(2L)).thenReturn(cliente);

        ReservaResumidaDto dto = ReservaResumidaDto.builder()
                .id(10L)
                .quartoId(1L)
                .clienteId(2L)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .observacao("Vista mar")
                .valorDaReserva(BigDecimal.valueOf(600))
                .statusDaReserva(StatusDaReserva.ABERTA)
                .build();

        Reserva reserva = mapper.toReserva(dto);

        assertThat(reserva.getId()).isEqualTo(10L);
        assertThat(reserva.getQuarto().getId()).isEqualTo(1L);
        assertThat(reserva.getCliente().getId()).isEqualTo(2L);
        assertThat(reserva.getObservacao()).isEqualTo("Vista mar");
        assertThat(reserva.getValorDaReserva()).isEqualByComparingTo(BigDecimal.valueOf(600));
        assertThat(reserva.getStatusDaReserva()).isEqualTo(StatusDaReserva.ABERTA);
    }

    @Test
    void toReserva_comComplementos_mapeiaLista() {
        LocalDateTime checkIn = LocalDateTime.now().plusDays(5);

        Quarto quarto = new Quarto();
        quarto.setId(1L);
        Cliente cliente = new Cliente();
        cliente.setId(2L);

        when(quartoService.buscarPorId(1L)).thenReturn(quarto);
        when(clienteService.buscarPorId(2L)).thenReturn(cliente);

        ReservaResumidaDto dto = ReservaResumidaDto.builder()
                .id(11L)
                .quartoId(1L)
                .clienteId(2L)
                .checkIn(checkIn)
                .checkOut(checkIn.plusDays(2))
                .complementos(List.of(
                        ComplementoDTO.builder().id(5L).nome("Café").valor(BigDecimal.valueOf(35)).build()
                ))
                .build();

        Reserva reserva = mapper.toReserva(dto);

        assertThat(reserva.getComplementos()).hasSize(1);
        assertThat(reserva.getComplementos().getFirst().getNome()).isEqualTo("Café");
    }

    @Test
    void toReserva_semComplementos_complementosNulo() {
        LocalDateTime checkIn = LocalDateTime.now().plusDays(5);

        Quarto quarto = new Quarto();
        quarto.setId(1L);
        Cliente cliente = new Cliente();
        cliente.setId(2L);

        when(quartoService.buscarPorId(1L)).thenReturn(quarto);
        when(clienteService.buscarPorId(2L)).thenReturn(cliente);

        ReservaResumidaDto dto = ReservaResumidaDto.builder()
                .id(12L)
                .quartoId(1L)
                .clienteId(2L)
                .checkIn(checkIn)
                .checkOut(checkIn.plusDays(2))
                .complementos(null)
                .build();

        Reserva reserva = mapper.toReserva(dto);

        assertThat(reserva.getComplementos()).isNull();
    }
}
