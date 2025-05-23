package com.senai.pousadabackend.entity;

public enum Status {
    ATIVO,
    INATIVO;

    public static Status toStatus(String status) {
        return Status.valueOf(status);
    }

    public boolean isAtivo() {
        return this.equals(ATIVO);
    }

}
