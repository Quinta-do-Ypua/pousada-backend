package com.senai.pousadabackend.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegistroNaoEncontradoException extends RuntimeException {

    public RegistroNaoEncontradoException(String mensagem) {
        super(mensagem);
        log.error(mensagem);
        log.error("StackTrace: {}", this.getStackTrace());
    }

    public RegistroNaoEncontradoException() {
        this("Registro não encontrado");
    }

}
