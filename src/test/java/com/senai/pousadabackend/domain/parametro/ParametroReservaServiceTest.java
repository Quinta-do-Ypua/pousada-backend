package com.senai.pousadabackend.domain.parametro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParametroReservaServiceTest {

    @Mock
    private ParametroReservaRepository repository;

    private ParametroReservaService service;

    @BeforeEach
    void setUp() {
        service = new ParametroReservaService(repository);
    }

    private ParametroReserva parametroCompleto() {
        return ParametroReserva.builder()
                .id(1L)
                .bloquearReservaComPendencia(true)
                .multaCancelamentoAtiva(true)
                .maxReservasAtivasPorUsuario(3)
                .tempoEntreReservasDias(2)
                .prazoMaximoCancelamentoDias(10)
                .tempoMinimoParaReservaDias(1)
                .duracaoMinimaDias(2)
                .duracaoMaximaDias(15)
                .horarioCheckIn(LocalTime.of(14, 0))
                .horarioCheckOut(LocalTime.of(12, 0))
                .percentualMultaCancelamento(new BigDecimal("25.00"))
                .build();
    }

    @Test
    void recarregarCache_comRegistroExistente_atualizaCache() {
        ParametroReserva parametro = parametroCompleto();
        when(repository.findById(1L)).thenReturn(Optional.of(parametro));

        service.recarregarCache();

        assertThat(service.getParametros()).isEqualTo(parametro);
    }

    @Test
    void recarregarCache_semRegistro_usaDefaults() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        service.recarregarCache();
        ParametroReserva result = service.getParametros();

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void getParametros_cacheNulo_triggerRecarregar() {
        ParametroReserva parametro = parametroCompleto();
        when(repository.findById(1L)).thenReturn(Optional.of(parametro));

        ParametroReserva result = service.getParametros();

        assertThat(result).isEqualTo(parametro);
        verify(repository).findById(1L);
    }

    @Test
    void getParametros_cachePopulado_naoRecarrega() {
        ParametroReserva parametro = parametroCompleto();
        when(repository.findById(1L)).thenReturn(Optional.of(parametro));

        service.recarregarCache();
        service.getParametros();
        service.getParametros();

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void isBloquearReservaComPendencia_retornaValorDoParametro() {
        ParametroReserva parametro = parametroCompleto();
        when(repository.findById(1L)).thenReturn(Optional.of(parametro));
        service.recarregarCache();

        assertThat(service.isBloquearReservaComPendencia()).isTrue();
    }

    @Test
    void isMultaCancelamentoAtiva_retornaValorDoParametro() {
        ParametroReserva parametro = parametroCompleto();
        when(repository.findById(1L)).thenReturn(Optional.of(parametro));
        service.recarregarCache();

        assertThat(service.isMultaCancelamentoAtiva()).isTrue();
    }

    @Test
    void getMaxReservasAtivasPorUsuario_retornaValorDoParametro() {
        ParametroReserva parametro = parametroCompleto();
        when(repository.findById(1L)).thenReturn(Optional.of(parametro));
        service.recarregarCache();

        assertThat(service.getMaxReservasAtivasPorUsuario()).isEqualTo(3);
    }

    @Test
    void getTempoEntreReservasDias_retornaValorDoParametro() {
        ParametroReserva parametro = parametroCompleto();
        when(repository.findById(1L)).thenReturn(Optional.of(parametro));
        service.recarregarCache();

        assertThat(service.getTempoEntreReservasDias()).isEqualTo(2);
    }

    @Test
    void getPrazoMaximoCancelamentoDias_retornaValorDoParametro() {
        ParametroReserva parametro = parametroCompleto();
        when(repository.findById(1L)).thenReturn(Optional.of(parametro));
        service.recarregarCache();

        assertThat(service.getPrazoMaximoCancelamentoDias()).isEqualTo(10);
    }

    @Test
    void getTempoMinimoParaReservaDias_retornaValorDoParametro() {
        ParametroReserva parametro = parametroCompleto();
        when(repository.findById(1L)).thenReturn(Optional.of(parametro));
        service.recarregarCache();

        assertThat(service.getTempoMinimoParaReservaDias()).isEqualTo(1);
    }

    @Test
    void getDuracaoMinimaDias_retornaValorDoParametro() {
        ParametroReserva parametro = parametroCompleto();
        when(repository.findById(1L)).thenReturn(Optional.of(parametro));
        service.recarregarCache();

        assertThat(service.getDuracaoMinimaDias()).isEqualTo(2);
    }

    @Test
    void getDuracaoMaximaDias_retornaValorDoParametro() {
        ParametroReserva parametro = parametroCompleto();
        when(repository.findById(1L)).thenReturn(Optional.of(parametro));
        service.recarregarCache();

        assertThat(service.getDuracaoMaximaDias()).isEqualTo(15);
    }

    @Test
    void getHorarioCheckIn_retornaValorDoParametro() {
        ParametroReserva parametro = parametroCompleto();
        when(repository.findById(1L)).thenReturn(Optional.of(parametro));
        service.recarregarCache();

        assertThat(service.getHorarioCheckIn()).isEqualTo(LocalTime.of(14, 0));
    }

    @Test
    void getHorarioCheckOut_retornaValorDoParametro() {
        ParametroReserva parametro = parametroCompleto();
        when(repository.findById(1L)).thenReturn(Optional.of(parametro));
        service.recarregarCache();

        assertThat(service.getHorarioCheckOut()).isEqualTo(LocalTime.of(12, 0));
    }

    @Test
    void getPercentualMultaCancelamento_retornaValorDoParametro() {
        ParametroReserva parametro = parametroCompleto();
        when(repository.findById(1L)).thenReturn(Optional.of(parametro));
        service.recarregarCache();

        assertThat(service.getPercentualMultaCancelamento())
                .isEqualByComparingTo(new BigDecimal("25.00"));
    }

    @Test
    void comDefaults_retornaParametroComId1() {
        ParametroReserva defaults = ParametroReserva.comDefaults();

        assertThat(defaults.getId()).isEqualTo(1L);
        assertThat(defaults.getMaxReservasAtivasPorUsuario()).isEqualTo(1);
        assertThat(defaults.getDuracaoMaximaDias()).isEqualTo(30);
        assertThat(defaults.getPrazoMaximoCancelamentoDias()).isEqualTo(7);
    }
}
