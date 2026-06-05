package com.senai.pousadabackend.exceptions;

public class ExisteReservaParaEssaDataException extends RuntimeException {

    public ExisteReservaParaEssaDataException() {
        super("Já existe uma reserva para esse quarto neste período.");
    }

    public ExisteReservaParaEssaDataException(String mensagem) {
        super(mensagem);
    }
}
