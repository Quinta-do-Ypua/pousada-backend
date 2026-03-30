package com.senai.pousadabackend.exceptions;

public class PrazoMinimoNaoRespeitadoException extends RuntimeException {

    public PrazoMinimoNaoRespeitadoException(Integer diasMinimos) {
        super("É necessário agendar com pelo menos " + diasMinimos + " dia(s) de antecedência.");
    }

}
