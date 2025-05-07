package com.senai.pousadabackend.exceptions;

public class FalhaAoEnviarEmailException extends RuntimeException {

    public FalhaAoEnviarEmailException(Throwable cause) {
      super("Não foi possivel enviar e-mail", cause);
    }

}
