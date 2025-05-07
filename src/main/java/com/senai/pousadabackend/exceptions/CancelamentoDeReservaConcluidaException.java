package com.senai.pousadabackend.exceptions;

public class CancelamentoDeReservaConcluidaException extends RuntimeException {

    public CancelamentoDeReservaConcluidaException(String msg) {
        super(msg);
    }

    public CancelamentoDeReservaConcluidaException() {
        super("Não é possivel cancelar uma reserva concluida");
    }
}
