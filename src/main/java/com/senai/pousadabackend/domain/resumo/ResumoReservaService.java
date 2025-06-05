package com.senai.pousadabackend.domain.resumo;

import com.senai.pousadabackend.core.BaseServiceInterface;
import com.senai.pousadabackend.domain.reserva.Reserva;

public interface ResumoReservaService extends BaseServiceInterface<ResumoReserva, Long> {

    void criarNotaFiscalAssincronaAPartirDaReserva(Reserva reserva);

    ResumoReserva criarERetornarNotaFiscalAPartirDaReserva(Reserva reserva);

}