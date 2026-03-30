package com.senai.pousadabackend.exceptions;

public class LimiteReservasExcedidoException extends RuntimeException {

    public LimiteReservasExcedidoException(Integer limite) {
        super("Limite de " + limite + " reserva(s) ativa(s) por cliente atingido.");
    }

}
