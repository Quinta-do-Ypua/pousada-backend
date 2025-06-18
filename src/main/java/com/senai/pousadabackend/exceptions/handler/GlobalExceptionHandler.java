package com.senai.pousadabackend.exceptions.handler;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.senai.pousadabackend.exceptions.*;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Map<String, String>> handleValidacao(MethodArgumentNotValidException ex) {
        Map<String, Map<String, String>> erros = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> {
            Map<String, String> msg = new HashMap<>();
            msg.put("mensagem", e.getDefaultMessage());
            erros.put(e.getField(), msg);
        });
        return erros;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            RegistroDuplicadoException.class,
            BusinessException.class,
            DataDaReservaInvalida.class,
            ExisteReservaParaEssaDataException.class,
            ExisteReservaAbertaParaEsseCliente.class,
            RegistrosVinculadosException.class
    })
    public Map<String, String> handleNegocio(RuntimeException ex) {
        return criarMensagem(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, Map<String, String>> handleConstraint(ConstraintViolationException ex) {
        Map<String, Map<String, String>> erros = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String path = violation.getPropertyPath().toString();
            String campo = path.substring(path.lastIndexOf('.') + 1);
            Map<String, String> msg = new HashMap<>();
            msg.put("mensagem", violation.getMessage());
            erros.put(campo, msg);
        });
        return erros;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, String> handleJsonMalFormatado(HttpMessageNotReadableException ex) {
        return criarMensagem("Corpo da requisição inválido ou mal formatado.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Map<String, String> handleTipoIncorreto(MethodArgumentTypeMismatchException ex) {
        return criarMensagem("Parâmetro inválido na URL.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidDefinitionException.class)
    public Map<String, String> handleFormatoInvalido(InvalidDefinitionException ex) {
        String campo = ex.getPath().isEmpty() ? "desconhecido" : ex.getPath().getLast().getFieldName();
        return criarMensagem("O campo '" + campo + "' possui um formato inválido.");
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Map<String, String> handleMetodoInvalido(HttpRequestMethodNotSupportedException ex) {
        return criarMensagem("Método HTTP não suportado.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingPathVariableException.class, MissingServletRequestParameterException.class})
    public Map<String, String> handleParametroAusente(Exception ex) {
        return criarMensagem("Parâmetro obrigatório ausente.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Map<String, String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.error("Violação de integridade: ", ex);

        String mensagemOriginal = ex.getMostSpecificCause().getMessage();

        String mensagem = extrairDetalheDoErro(mensagemOriginal);
        if (mensagem == null) {
            mensagem = analisarViolacaoDeConstraint(mensagemOriginal);
        }

        if (mensagem == null) {
            mensagem = "Violação de integridade. Verifique se há dados obrigatórios ausentes, duplicados ou vínculos ativos.";
        }

        return criarMensagem(mensagem);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public Map<String, String> handleErroSql(InvalidDataAccessResourceUsageException ex) {
        log.error("Erro de acesso a recurso de dados: {}", ex.getMessage(), ex);
        return criarMensagem("Erro interno ao acessar recurso de dados.");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RegistroNaoEncontradoException.class)
    public Map<String, String> handleNaoEncontrado(RegistroNaoEncontradoException ex) {
        return criarMensagem(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, String> handleErroGeral(Exception ex) {
        log.error("Erro inesperado não tratado: ", ex);
        return criarMensagem("Erro interno no servidor. Contate o suporte.");
    }

    private String extrairDetalheDoErro(String mensagem) {
        if (mensagem == null) return null;
        var matcher = Pattern.compile("Detalhe:\\s*(.*)").matcher(mensagem);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }

    private String analisarViolacaoDeConstraint(String mensagem) {
        if (mensagem == null) return null;

        if (mensagem.contains("violates not-null constraint") || mensagem.contains("violação de não-nulo")) {
            var matcher = Pattern.compile("coluna \"(\\w+)\"").matcher(mensagem);
            if (matcher.find()) {
                String campo = matcher.group(1);
                return "O campo '" + campo + "' é obrigatório.";
            }
            return "Existe um campo obrigatório que não foi informado.";
        }

        if (mensagem.contains("violates unique constraint") || mensagem.contains("violação de unicidade")) {
            var matcher = Pattern.compile("chave \\((.+)\\)=\\((.+)\\)").matcher(mensagem);
            if (matcher.find()) {
                String campo = matcher.group(1);
                String valor = matcher.group(2);
                return "O valor '" + valor + "' já existe para o campo '" + campo + "'.";
            }
            return "Já existe um registro com esse valor.";
        }

        var matcherChaveAusente = Pattern.compile(
                "Chave \\((\\w+)_id\\)=\\((\\d+)\\) não está presente na tabela \"(\\w+)\""
        ).matcher(mensagem);
        if (matcherChaveAusente.find()) {
            String campo = matcherChaveAusente.group(1); // endereco
            String id = matcherChaveAusente.group(2);    // 4
            return "Não existe " + campo + " com id " + id + ".";
        }

        if (mensagem.contains("violates foreign key constraint") || mensagem.contains("violação de chave estrangeira")) {
            return "Não é possível excluir ou alterar este registro, pois existem registros vinculados.";
        }

        return null;
    }

    private Map<String, String> criarMensagem(String mensagem) {
        Map<String, String> mapa = new HashMap<>();
        mapa.put("mensagem", mensagem);
        return mapa;
    }

}
