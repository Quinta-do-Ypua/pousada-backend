package com.senai.pousadabackend.exceptions;

public class TempoEntreReservasNaoRespeitadoException extends RuntimeException {

    public TempoEntreReservasNaoRespeitadoException(Integer diasEntreReservas) {
        super("Deve haver um intervalo de " + diasEntreReservas + " dia(s) entre reservas.");
    }

}
