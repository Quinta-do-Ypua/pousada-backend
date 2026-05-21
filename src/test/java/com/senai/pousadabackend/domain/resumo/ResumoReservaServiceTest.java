package com.senai.pousadabackend.domain.resumo;

import com.senai.pousadabackend.domain.cliente.Cliente;
import com.senai.pousadabackend.domain.complemento.Complemento;
import com.senai.pousadabackend.domain.complemento.ComplementoService;
import com.senai.pousadabackend.domain.quarto.Quarto;
import com.senai.pousadabackend.domain.quarto.QuartoService;
import com.senai.pousadabackend.domain.reserva.Reserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ResumoReservaServiceTest {

    @Mock
    private ResumoReservaRepository repository;

    @Mock
    private QuartoService quartoService;

    @Mock
    private ComplementoService complementoService;

    private ResumoReservaService service;

    @BeforeEach
    void setUp() {
        service = new ResumoReservaService(repository, quartoService, complementoService);
    }

    private Quarto quartoPadrao() {
        Quarto q = new Quarto();
        q.setId(1L);
        q.setNome("Suite 01");
        q.setValorDiaria(BigDecimal.valueOf(200));
        return q;
    }

    private Cliente clientePadrao() {
        Cliente c = new Cliente();
        c.setId(1L);
        c.setNome("João");
        return c;
    }

    private Reserva reservaValida() {
        Quarto quarto = quartoPadrao();
        return Reserva.builder()
                .id(1L)
                .quarto(quarto)
                .cliente(clientePadrao())
                .checkIn(LocalDateTime.now().plusDays(5))
                .checkOut(LocalDateTime.now().plusDays(8))
                .complementos(new ArrayList<>())
                .build();
    }

    @Test
    void criarERetornarNotaFiscal_semComplementos_criaSucesso() {
        Reserva reserva = reservaValida();
        Quarto quarto = quartoPadrao();

        when(quartoService.buscarPorId(1L)).thenReturn(quarto);
        when(repository.save(any())).thenAnswer(inv -> {
            ResumoReserva r = inv.getArgument(0);
            r.setDataCriacao(java.time.LocalDateTime.now());
            return r;
        });

        ResumoReserva resultado = service.criarERetornarNotaFiscalAPartirDaReserva(reserva);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getCliente().getId()).isEqualTo(1L);
        assertThat(resultado.getValorTotal()).isGreaterThan(BigDecimal.ZERO);
        verify(repository).save(any(ResumoReserva.class));
    }

    @Test
    void criarERetornarNotaFiscal_comComplementos_incluiItens() {
        Reserva reserva = reservaValida();

        Complemento comp1 = Complemento.builder().id(10L).nome("Café").valor(BigDecimal.valueOf(35)).descricao("Café").build();
        Complemento comp2 = Complemento.builder().id(10L).nome("Café").valor(BigDecimal.valueOf(35)).descricao("Café").build();
        reserva.setComplementos(List.of(comp1, comp2));

        when(quartoService.buscarPorId(1L)).thenReturn(quartoPadrao());
        when(complementoService.buscarPorId(10L)).thenReturn(comp1);
        when(repository.save(any())).thenAnswer(inv -> {
            ResumoReserva r = inv.getArgument(0);
            r.setDataCriacao(java.time.LocalDateTime.now());
            return r;
        });

        ResumoReserva resultado = service.criarERetornarNotaFiscalAPartirDaReserva(reserva);

        assertThat(resultado).isNotNull();
        // 1 item de complemento + 1 item de quarto = 2 items total, but café is counted twice
        assertThat(resultado.getItens()).isNotEmpty();
    }

    @Test
    void criarERetornarNotaFiscal_comComplementosDiferentes_criaDoisItens() {
        Reserva reserva = reservaValida();

        Complemento comp1 = Complemento.builder().id(10L).nome("Café").valor(BigDecimal.valueOf(35)).descricao("Café").build();
        Complemento comp2 = Complemento.builder().id(20L).nome("Transfer").valor(BigDecimal.valueOf(80)).descricao("Transfer").build();
        reserva.setComplementos(List.of(comp1, comp2));

        when(quartoService.buscarPorId(1L)).thenReturn(quartoPadrao());
        when(complementoService.buscarPorId(10L)).thenReturn(comp1);
        when(complementoService.buscarPorId(20L)).thenReturn(comp2);
        when(repository.save(any())).thenAnswer(inv -> {
            ResumoReserva r = inv.getArgument(0);
            r.setDataCriacao(java.time.LocalDateTime.now());
            return r;
        });

        ResumoReserva resultado = service.criarERetornarNotaFiscalAPartirDaReserva(reserva);

        // 2 complementos + 1 quarto = 3 itens
        assertThat(resultado.getItens()).hasSize(3);
    }

    @Test
    void criarNotaFiscalAssincrona_naoLancaExcecao() throws InterruptedException {
        Reserva reserva = reservaValida();

        when(quartoService.buscarPorId(1L)).thenReturn(quartoPadrao());
        when(repository.save(any())).thenAnswer(inv -> {
            ResumoReserva r = inv.getArgument(0);
            r.setDataCriacao(java.time.LocalDateTime.now());
            return r;
        });

        service.criarNotaFiscalAssincronaAPartirDaReserva(reserva);

        // Give virtual thread time to complete
        Thread.sleep(200);
        verify(repository, atLeastOnce()).save(any());
    }

    @Test
    void criarERetornarNotaFiscal_calculaValorTotalCorreto() {
        Reserva reserva = reservaValida();
        // 3 days at 200/day = 600
        reserva.setCheckIn(LocalDateTime.now().plusDays(5));
        reserva.setCheckOut(LocalDateTime.now().plusDays(8)); // 3 days

        when(quartoService.buscarPorId(1L)).thenReturn(quartoPadrao());
        when(repository.save(any())).thenAnswer(inv -> {
            ResumoReserva r = inv.getArgument(0);
            r.setDataCriacao(java.time.LocalDateTime.now());
            return r;
        });

        ResumoReserva resultado = service.criarERetornarNotaFiscalAPartirDaReserva(reserva);

        assertThat(resultado.getValorTotal()).isEqualByComparingTo(BigDecimal.valueOf(600));
    }
}
