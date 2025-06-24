package com.senai.pousadabackend.exceptions;

public class DataDaReservaInvalida extends RuntimeException {
    public DataDaReservaInvalida() {
      super("A reserva deve ter pelo menos uma diaria, e a data de entrada não pode ser depois da data de saida ");
    }
}
