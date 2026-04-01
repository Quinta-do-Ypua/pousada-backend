package com.senai.pousadabackend.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoPagamento {

    DINHEIRO("Dinheiro"),
    DEBITO("Cartão de Débito"),
    CREDITO("Cartão de Crédito"),
    PIX("PIX"),
    TRANSFERENCIA("Transferência Bancária"),
    OUTROS("Outros");

    private final String descricao;
}
