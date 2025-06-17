package com.senai.pousadabackend.exceptions;

public class ExisteReservaAbertaParaEsseCliente extends RuntimeException {

    public ExisteReservaAbertaParaEsseCliente() {
      super("Não é possivel abrir uma reserva pois já existe reservas ativas para esse cliente.");
    }

}
