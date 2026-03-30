package com.senai.pousadabackend.domain.parametro;

import java.math.BigDecimal;
import java.time.LocalTime;

public interface ParametroReservaService {

    Boolean isBloquearReservaComPendencia();

    Boolean isMultaCancelamentoAtiva();

    Integer getMaxReservasAtivasPorUsuario();

    Integer getTempoEntreReservasDias();

    Integer getPrazoMaximoCancelamentoDias();

    Integer getTempoMinimoParaReservaDias();

    Integer getDuracaoMinimaDias();

    Integer getDuracaoMaximaDias();

    LocalTime getHorarioCheckIn();

    LocalTime getHorarioCheckOut();

    BigDecimal getPercentualMultaCancelamento();

}
