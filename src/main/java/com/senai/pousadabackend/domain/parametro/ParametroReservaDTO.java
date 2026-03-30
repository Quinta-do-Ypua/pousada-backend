package com.senai.pousadabackend.domain.parametro;

import com.senai.pousadabackend.config.validation.GrupoValidacaoAlterar;
import com.senai.pousadabackend.config.validation.GrupoValidacaoInserir;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@Builder
public class ParametroReservaDTO {

    @Null(message = "O id deve estar vazio", groups = GrupoValidacaoInserir.class)
    @NotNull(message = "O id é obrigatório", groups = GrupoValidacaoAlterar.class)
    private Long id;

    private Boolean bloquearReservaComPendencia;
    private Boolean multaCancelamentoAtiva;
    private Integer maxReservasAtivasPorUsuario;
    private Integer tempoEntreReservasDias;
    private Integer prazoMaximoCancelamentoDias;
    private Integer tempoMinimoParaReservaDias;
    private Integer duracaoMinimaDias;
    private Integer duracaoMaximaDias;
    private LocalTime horarioCheckIn;
    private LocalTime horarioCheckOut;
    private BigDecimal percentualMultaCancelamento;

}
