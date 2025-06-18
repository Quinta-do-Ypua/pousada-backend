package com.senai.pousadabackend.exceptions;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException() {
        super("Senha incorreta");
    }
}
