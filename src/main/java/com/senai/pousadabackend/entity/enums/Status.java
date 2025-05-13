package com.senai.pousadabackend.entity.enums;

public enum Status {
    ATIVO,
    INATIVO;

    public boolean isAtivo() {
        return this.equals(ATIVO);
    }

}
