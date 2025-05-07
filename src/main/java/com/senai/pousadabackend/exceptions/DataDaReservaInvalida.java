package com.senai.pousadabackend.exceptions;

public class DataDaReservaInvalida extends RuntimeException {
    public DataDaReservaInvalida() {
      super("O checkin não pode ser antes do checkout e o checkout não pode ser antes do checkin");
    }
}
