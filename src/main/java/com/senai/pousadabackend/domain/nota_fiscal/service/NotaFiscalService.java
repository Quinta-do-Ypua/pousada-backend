package com.senai.pousadabackend.domain.nota_fiscal.service;

import com.senai.pousadabackend.domain.nota_fiscal.NotaFiscal;
import com.senai.pousadabackend.domain.reserva.Reserva;
import com.senai.pousadabackend.service.BaseServiceInterface;

public interface NotaFiscalService extends BaseServiceInterface<NotaFiscal, Long> {

    NotaFiscal criarNotaFiscalAPartirDaReserva(Reserva reserva);

}
