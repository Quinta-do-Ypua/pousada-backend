package com.senai.pousadabackend.exceptions;

public class DuracaoReservaInvalidaException extends RuntimeException {

    public DuracaoReservaInvalidaException(Integer minimo, Integer maximo) {
        super("A reserva deve ter entre " + minimo + " e " + maximo + " diária(s).");
    }

}
