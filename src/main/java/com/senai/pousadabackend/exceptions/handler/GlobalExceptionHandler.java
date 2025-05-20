package com.senai.pousadabackend.exceptions.handler;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.senai.pousadabackend.exceptions.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, Map<String, Object>> handle(){
        return criarMapDeErro(ErroDaApi.BODY_INVALIDO,
                "O corpo (body) da requisição possui erros ou não existe");
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    public Map<String, Map<String, Object>> handle(RegistroDuplicadoException e){
        return criarMapDeErro(ErroDaApi.FORMATO_INVALIDO, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Map<String, Object>> handle(MethodArgumentNotValidException ex) {
        var error = ex.getBindingResult().getFieldErrors().getFirst();
        return criarMapDeErro(ErroDaApi.FORMATO_INVALIDO, error.getField() + " " + error.getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidDefinitionException.class)
    public Map<String, Map<String, Object>> handle(InvalidDefinitionException ide){
        String atributo = ide.getPath().getLast().getFieldName();
        String msgDeErro = "O campo '" + atributo + "' possui formato inválido";
        return criarMapDeErro(ErroDaApi.FORMATO_INVALIDO, msgDeErro);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IntegracaoException.class)
    public Map<String, Map<String, Object>> handle(IntegracaoException ie){
        return criarMapDeErro(ErroDaApi.INTEGRACAO_INVALIDA, ie.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public Map<String, Map<String, Object>> handle(BusinessException be){
        return criarMapDeErro(ErroDaApi.REGRA_VIOLADA, be.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, Map<String, String>> handle(ConstraintViolationException cve){
        Map<String, Map<String, String>> body = new HashMap<>();
        Map<String, String> msgs = new HashMap<>();
        body.put("errors", msgs);
        cve.getConstraintViolations().forEach((error) -> {
            String[] paths = error.getPropertyPath().toString().split("\\.");
            String atributo = paths[paths.length - 1];
            String errorMessage = error.getMessage();
            String mensagemCompleta = "O atributo '" + atributo +
                    "' apresentou o seguinte erro: '" + errorMessage + "'";
            String plainJsonError = "{ mensagem: " + mensagemCompleta + " }";
            msgs.put("{ codigo:" + ErroDaApi.CONDICAO_VIOLADA.getCodigo(), plainJsonError);
        });
        return body;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, Map<String, Object>> handle(IllegalArgumentException ie){
        return criarMapDeErro(ErroDaApi.PARAMETRO_INVALIDO, ie.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public Map<String, Map<String, Object>> handle(NullPointerException npe){
        return criarMapDeErro(ErroDaApi.PARAMETRO_INVALIDO, npe.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingPathVariableException.class)
    public Map<String, Map<String, Object>> handle(MissingPathVariableException mpve){
        return criarMapDeErro(ErroDaApi.PRECONDICAO_REQUERIDA, mpve.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Map<String, Map<String, Object>> handle(MethodArgumentTypeMismatchException matme){
        return criarMapDeErro(ErroDaApi.TIPO_PARAMETRO_INVALIDO, "A URI possui valores inválidos");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Map<String, Map<String, Object>> handle(HttpRequestMethodNotSupportedException hrmnse){
        return criarMapDeErro(ErroDaApi.METODO_HTTP_NAO_SUPORTADO, hrmnse.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Map<String, Map<String, Object>> handle(MissingServletRequestParameterException mrpe){
        return criarMapDeErro(ErroDaApi.PARAMETRO_OBRIGATORIO, mrpe.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RegistroNaoEncontradoException.class)
    public Map<String, Map<String, Object>> handle(RegistroNaoEncontradoException rnee){
        return criarMapDeErro(ErroDaApi.REGISTRO_NAO_ENCONTRADO, rnee.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public Map<String, Map<String, Object>> handle(InvalidDataAccessResourceUsageException ie){
        return criarMapDeErro(ErroDaApi.INTEGRACAO_INVALIDA,
                "Ocorreu um erro de integração com a Api externa");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InativoException.class)
    public Map<String, Map<String, Object>> handle(InativoException ie){
        return criarMapDeErro(ErroDaApi.PARAMETRO_INVALIDO,
                ie.getMessage());
    }

    private Map<String, Map<String, Object>> criarMapDeErro(ErroDaApi erroDaApi, String msgDeErro){

        Map<String, Map<String, Object>> body = new HashMap<>();

        Map<String, Object> description = new HashMap<>();
        description.put("codigo", erroDaApi.getCodigo());
        description.put("mensagem", msgDeErro);

        body.put("errors", description);

        return body;
    }
}
