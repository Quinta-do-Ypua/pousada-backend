package com.senai.pousadabackend.exceptions;

public class LimiteReservasExcedidoException extends RuntimeException {

    public LimiteReservasExcedidoException(Integer limite) {
        super("Este cliente já atingiu o limite de " + limite + " reserva(s) ativa(s) simultânea(s).");
    }

}
