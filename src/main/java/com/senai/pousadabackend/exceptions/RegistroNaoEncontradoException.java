package com.senai.pousadabackend.exceptions;

public class RegistroNaoEncontradoException extends RuntimeException {

    public RegistroNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public RegistroNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
