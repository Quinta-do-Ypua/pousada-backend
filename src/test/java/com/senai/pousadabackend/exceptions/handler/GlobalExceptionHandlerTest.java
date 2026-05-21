package com.senai.pousadabackend.exceptions.handler;

import com.senai.pousadabackend.exceptions.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleNaoEncontrado_retornaMensagemCorreta() {
        var ex = new RegistroNaoEncontradoException("Registro com id '1' não foi encontrado");

        Map<String, String> result = handler.handleNaoEncontrado(ex);

        assertThat(result).containsEntry("mensagem", "Registro com id '1' não foi encontrado");
    }

    @Test
    void handleNegocio_businessException_retornaMensagem() {
        var ex = new BusinessException("Erro de negócio");

        Map<String, String> result = handler.handleNegocio(ex);

        assertThat(result).containsEntry("mensagem", "Erro de negócio");
    }

    @Test
    void handleNegocio_registroDuplicado_retornaMensagem() {
        var ex = new RegistroDuplicadoException("CPF já cadastrado");

        Map<String, String> result = handler.handleNegocio(ex);

        assertThat(result).containsEntry("mensagem", "CPF já cadastrado");
    }

    @Test
    void handleNegocio_illegalArgument_retornaMensagem() {
        var ex = new IllegalArgumentException("Argumento inválido");

        Map<String, String> result = handler.handleNegocio(ex);

        assertThat(result).containsEntry("mensagem", "Argumento inválido");
    }

    @Test
    void handleNegocio_dataDaReservaInvalida_retornaMensagem() {
        var ex = new DataDaReservaInvalida();

        Map<String, String> result = handler.handleNegocio(ex);

        assertThat(result).containsKey("mensagem");
        assertThat(result.get("mensagem")).isNotBlank();
    }

    @Test
    void handleNegocio_existeReservaParaEssaData_retornaMensagem() {
        var ex = new ExisteReservaParaEssaDataException();

        Map<String, String> result = handler.handleNegocio(ex);

        assertThat(result).containsKey("mensagem");
    }

    @Test
    void handleNegocio_registrosVinculados_retornaMensagem() {
        var ex = new RegistrosVinculadosException();

        Map<String, String> result = handler.handleNegocio(ex);

        assertThat(result).containsKey("mensagem");
    }

    @Test
    void handleAccessDenied_retorna403Mensagem() {
        var ex = new AccessDeniedException("Acesso negado");

        Map<String, String> result = handler.handleAccessDeniedException(ex);

        assertThat(result).containsKey("mensagem");
        assertThat(result.get("mensagem")).contains("permissão");
    }

    @Test
    void handleAuthentication_retorna401Mensagem() {
        var ex = new BadCredentialsException("Credenciais inválidas");

        Map<String, String> result = handler.handleAuthenticationException(ex);

        assertThat(result).containsKey("mensagem");
        assertThat(result.get("mensagem")).contains("token");
    }

    @Test
    void handleKeycloakException_integrationException_retornaMensagemGenerica() {
        var ex = new KeycloakIntegrationException("Falha ao conectar");

        Map<String, String> result = handler.handleKeycloakException(ex);

        assertThat(result).containsKey("mensagem");
        assertThat(result.get("mensagem")).contains("autenticação");
    }

    @Test
    void handleKeycloakException_configException_retornaMensagemGenerica() {
        var ex = new KeycloakConfigurationException("Config inválida");

        Map<String, String> result = handler.handleKeycloakException(ex);

        assertThat(result).containsKey("mensagem");
    }

    @Test
    void handleMetodoInvalido_retornaMensagem() {
        var ex = new HttpRequestMethodNotSupportedException("DELETE");

        Map<String, String> result = handler.handleMetodoInvalido(ex);

        assertThat(result).containsEntry("mensagem", "Método HTTP não suportado.");
    }

    @Test
    void handleErroSql_retornaMensagemGenerica() {
        var ex = new InvalidDataAccessResourceUsageException("SQL inválido");

        Map<String, String> result = handler.handleErroSql(ex);

        assertThat(result).containsKey("mensagem");
        assertThat(result.get("mensagem")).contains("Erro interno");
    }

    @Test
    void handleErroGeral_retornaMensagemGenerica() {
        var ex = new Exception("Erro inesperado");

        Map<String, String> result = handler.handleErroGeral(ex);

        assertThat(result).containsKey("mensagem");
        assertThat(result.get("mensagem")).contains("Erro interno");
    }

    @Test
    void handleConstraint_extraiCampoDoPath() {
        Path path = mock(Path.class);
        when(path.toString()).thenReturn("method.nomeDocampo");

        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        when(violation.getPropertyPath()).thenReturn(path);
        when(violation.getMessage()).thenReturn("Campo obrigatório");

        var ex = new ConstraintViolationException(Set.of(violation));

        Map<String, Map<String, String>> result = handler.handleConstraint(ex);

        assertThat(result).containsKey("nomeDocampo");
    }

    @Test
    void handleDataIntegrityViolation_comDetalheNoMensagem_extraiDetalhe() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException(
                "Detalhe: O campo 'email' já existe.");

        Map<String, String> result = handler.handleDataIntegrityViolation(ex);

        assertThat(result.get("mensagem")).contains("email");
    }

    @Test
    void handleDataIntegrityViolation_violacaoNotNull_retornaCampoObrigatorio() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException(
                "coluna \"nome\" violates not-null constraint");

        Map<String, String> result = handler.handleDataIntegrityViolation(ex);

        assertThat(result.get("mensagem")).contains("nome");
    }

    @Test
    void handleDataIntegrityViolation_violacaoUnicidade_retornaMensagem() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException(
                "violates unique constraint chave (email)=(test@test.com)");

        Map<String, String> result = handler.handleDataIntegrityViolation(ex);

        assertThat(result).containsKey("mensagem");
    }

    @Test
    void handleDataIntegrityViolation_violacaoChaveEstrangeira_retornaMensagem() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException(
                "violates foreign key constraint");

        Map<String, String> result = handler.handleDataIntegrityViolation(ex);

        assertThat(result.get("mensagem")).contains("registros vinculados");
    }

    @Test
    void handleDataIntegrityViolation_semDetalheEspecifico_retornaMensagemGenerica() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException(
                "Erro genérico de integridade");

        Map<String, String> result = handler.handleDataIntegrityViolation(ex);

        assertThat(result).containsKey("mensagem");
        assertThat(result.get("mensagem")).isNotBlank();
    }

    @Test
    void handleDataIntegrityViolation_mensagemNula_retornaMensagemGenerica() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("msg", new RuntimeException((String) null));

        Map<String, String> result = handler.handleDataIntegrityViolation(ex);

        assertThat(result).containsKey("mensagem");
    }

    @Test
    void handleDataIntegrityViolation_chaveAusente_retornaMensagem() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException(
                "Chave (endereco_id)=(4) não está presente na tabela \"enderecos\"");

        Map<String, String> result = handler.handleDataIntegrityViolation(ex);

        assertThat(result).containsKey("mensagem");
    }

    @Test
    void handleDataIntegrityViolation_violacaoNaoNuloPt_retornaMensagem() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException(
                "violação de não-nulo na coluna \"nome\"");

        Map<String, String> result = handler.handleDataIntegrityViolation(ex);

        assertThat(result).containsKey("mensagem");
    }

    @Test
    void handleDataIntegrityViolation_violacaoUnicidadePt_retornaMensagem() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException(
                "violação de unicidade chave (cpf)=(12345678901)");

        Map<String, String> result = handler.handleDataIntegrityViolation(ex);

        assertThat(result).containsKey("mensagem");
    }
}
