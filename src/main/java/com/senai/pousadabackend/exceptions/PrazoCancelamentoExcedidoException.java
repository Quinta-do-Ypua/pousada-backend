package com.senai.pousadabackend.exceptions;

public class PrazoCancelamentoExcedidoException extends RuntimeException {

    public PrazoCancelamentoExcedidoException(Integer prazoMaximoDias) {
        super("Cancelamento só permitido até " + prazoMaximoDias + " dia(s) antes do check-in.");
    }

}
