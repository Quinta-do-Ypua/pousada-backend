package com.senai.pousadabackend.service.nota_fiscal;

import com.senai.pousadabackend.entity.NotaFiscal;
import com.senai.pousadabackend.entity.Reserva;
import com.senai.pousadabackend.service.BaseServiceInterface;

public interface NotaFiscalService extends BaseServiceInterface<NotaFiscal, Long> {

    void criarNotaFiscalAssincronaAPartirDaReserva(Reserva reserva);

    NotaFiscal criarERetornarNotaFiscalAPartirDaReserva(Reserva reserva);

}
