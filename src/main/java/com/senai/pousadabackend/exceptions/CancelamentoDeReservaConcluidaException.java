package com.senai.pousadabackend.exceptions;

public class CancelamentoDeReservaConcluidaException extends RuntimeException {

    public CancelamentoDeReservaConcluidaException() {
        super("Não é possivel cancelar uma reserva concluida");
    }
}
