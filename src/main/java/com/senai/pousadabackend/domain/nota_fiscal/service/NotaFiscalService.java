package com.senai.pousadabackend.domain.nota_fiscal.service;

import com.senai.pousadabackend.core.BaseServiceInterface;
import com.senai.pousadabackend.domain.nota_fiscal.NotaFiscal;
import com.senai.pousadabackend.domain.reserva.Reserva;

public interface NotaFiscalService extends BaseServiceInterface<NotaFiscal, Long> {

    void criarNotaFiscalAssincronaAPartirDaReserva(Reserva reserva);

    NotaFiscal criarERetornarNotaFiscalAPartirDaReserva(Reserva reserva);

}