package com.senai.pousadabackend.domain.meioPagamento.service;

import com.senai.pousadabackend.core.BaseServiceInterface;
import com.senai.pousadabackend.domain.meioPagamento.MeioPagamento;
import org.springframework.validation.annotation.Validated;

@Validated
public interface MeioPagamentoService extends BaseServiceInterface<MeioPagamento, Long> {
}
