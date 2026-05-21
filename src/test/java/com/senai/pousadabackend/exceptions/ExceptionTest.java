package com.senai.pousadabackend.exceptions;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionTest {

    @Test
    void businessException_comMensagem_retornaMensagem() {
        BusinessException ex = new BusinessException("Erro de negócio");
        assertThat(ex.getMessage()).isEqualTo("Erro de negócio");
    }

    @Test
    void businessException_comMensagemECausa_retornaMensagem() {
        Throwable causa = new RuntimeException("causa");
        BusinessException ex = new BusinessException("Erro", causa);
        assertThat(ex.getMessage()).isEqualTo("Erro");
        assertThat(ex.getCause()).isEqualTo(causa);
    }

    @Test
    void registroNaoEncontradoException_retornaMensagem() {
        RegistroNaoEncontradoException ex = new RegistroNaoEncontradoException("Não encontrado");
        assertThat(ex.getMessage()).isEqualTo("Não encontrado");
    }

    @Test
    void registroNaoEncontradoException_comCausa_retornaMensagem() {
        Throwable causa = new RuntimeException("causa");
        RegistroNaoEncontradoException ex = new RegistroNaoEncontradoException("Não encontrado", causa);
        assertThat(ex.getCause()).isEqualTo(causa);
    }

    @Test
    void registroDuplicadoException_retornaMensagem() {
        RegistroDuplicadoException ex = new RegistroDuplicadoException("Duplicado");
        assertThat(ex.getMessage()).isEqualTo("Duplicado");
    }

    @Test
    void registrosVinculadosException_semMensagem_retornaMensagemPadrao() {
        RegistrosVinculadosException ex = new RegistrosVinculadosException();
        assertThat(ex.getMessage()).contains("excluir");
    }

    @Test
    void registrosVinculadosException_comMensagem_retornaMensagem() {
        RegistrosVinculadosException ex = new RegistrosVinculadosException("Vinculados");
        assertThat(ex.getMessage()).isEqualTo("Vinculados");
    }

    @Test
    void cancelamentoDeReservaConcluidaException_semMensagem_retornaMensagemPadrao() {
        CancelamentoDeReservaConcluidaException ex = new CancelamentoDeReservaConcluidaException();
        assertThat(ex.getMessage()).contains("cancelar");
    }

    @Test
    void cancelamentoDeReservaConcluidaException_comMensagem_retornaMensagem() {
        CancelamentoDeReservaConcluidaException ex = new CancelamentoDeReservaConcluidaException("Não pode cancelar");
        assertThat(ex.getMessage()).isEqualTo("Não pode cancelar");
    }

    @Test
    void existeReservaParaEssaDataException_retornaMensagem() {
        ExisteReservaParaEssaDataException ex = new ExisteReservaParaEssaDataException();
        assertThat(ex.getMessage()).contains("reserva");
    }

    @Test
    void dataReservaInvalidaException_retornaMensagem() {
        DataDaReservaInvalida ex = new DataDaReservaInvalida();
        assertThat(ex.getMessage()).isNotBlank();
    }

    @Test
    void duracaoReservaInvalidaException_retornaMensagemComMinMax() {
        DuracaoReservaInvalidaException ex = new DuracaoReservaInvalidaException(1, 30);
        assertThat(ex.getMessage()).contains("1");
        assertThat(ex.getMessage()).contains("30");
    }

    @Test
    void prazoMinimoNaoRespeitadoException_retornaMensagemComDias() {
        PrazoMinimoNaoRespeitadoException ex = new PrazoMinimoNaoRespeitadoException(3);
        assertThat(ex.getMessage()).contains("3");
    }

    @Test
    void prazoCancelamentoExcedidoException_retornaMensagemComDias() {
        PrazoCancelamentoExcedidoException ex = new PrazoCancelamentoExcedidoException(7);
        assertThat(ex.getMessage()).contains("7");
    }

    @Test
    void tempoEntreReservasNaoRespeitadoException_retornaMensagemComDias() {
        TempoEntreReservasNaoRespeitadoException ex = new TempoEntreReservasNaoRespeitadoException(5);
        assertThat(ex.getMessage()).contains("5");
    }

    @Test
    void limiteReservasExcedidoException_retornaMensagemComLimite() {
        LimiteReservasExcedidoException ex = new LimiteReservasExcedidoException(2);
        assertThat(ex.getMessage()).contains("2");
    }

    @Test
    void falhaAoEnviarEmailException_retornaMensagem() {
        FalhaAoEnviarEmailException ex = new FalhaAoEnviarEmailException(new RuntimeException("IO error"));
        assertThat(ex.getMessage()).contains("e-mail");
    }

    @Test
    void keycloakIntegrationException_retornaMensagem() {
        KeycloakIntegrationException ex = new KeycloakIntegrationException("Falha Keycloak");
        assertThat(ex.getMessage()).isEqualTo("Falha Keycloak");
    }

    @Test
    void keycloakConfigurationException_retornaMensagem() {
        KeycloakConfigurationException ex = new KeycloakConfigurationException("Config inválida");
        assertThat(ex.getMessage()).isEqualTo("Config inválida");
    }
}
