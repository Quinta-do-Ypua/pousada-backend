package com.senai.pousadabackend.domain.parametro;

import com.senai.pousadabackend.core.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParametroReservaMapper implements BaseMapper<ParametroReserva, ParametroReservaDTO> {

    @Override
    public ParametroReservaDTO toDTO(ParametroReserva parametroReserva) {
        if (parametroReserva == null) {
            return null;
        }
        return ParametroReservaDTO.builder()
                .id(parametroReserva.getId())
                .bloquearReservaComPendencia(parametroReserva.getBloquearReservaComPendencia())
                .multaCancelamentoAtiva(parametroReserva.getMultaCancelamentoAtiva())
                .maxReservasAtivasPorUsuario(parametroReserva.getMaxReservasAtivasPorUsuario())
                .tempoEntreReservasDias(parametroReserva.getTempoEntreReservasDias())
                .prazoMaximoCancelamentoDias(parametroReserva.getPrazoMaximoCancelamentoDias())
                .tempoMinimoParaReservaDias(parametroReserva.getTempoMinimoParaReservaDias())
                .duracaoMinimaDias(parametroReserva.getDuracaoMinimaDias())
                .duracaoMaximaDias(parametroReserva.getDuracaoMaximaDias())
                .horarioCheckIn(parametroReserva.getHorarioCheckIn())
                .horarioCheckOut(parametroReserva.getHorarioCheckOut())
                .percentualMultaCancelamento(parametroReserva.getPercentualMultaCancelamento())
                .build();
    }

    @Override
    public ParametroReserva toEntity(ParametroReservaDTO dto) {
        if (dto == null) {
            return null;
        }
        ParametroReserva parametro = ParametroReserva.builder()
                .id(dto.getId())
                .build();

        if (dto.getBloquearReservaComPendencia() != null) {
            parametro.setBloquearReservaComPendencia(dto.getBloquearReservaComPendencia());
        }
        if (dto.getMultaCancelamentoAtiva() != null) {
            parametro.setMultaCancelamentoAtiva(dto.getMultaCancelamentoAtiva());
        }
        if (dto.getMaxReservasAtivasPorUsuario() != null) {
            parametro.setMaxReservasAtivasPorUsuario(dto.getMaxReservasAtivasPorUsuario());
        }
        if (dto.getTempoEntreReservasDias() != null) {
            parametro.setTempoEntreReservasDias(dto.getTempoEntreReservasDias());
        }
        if (dto.getPrazoMaximoCancelamentoDias() != null) {
            parametro.setPrazoMaximoCancelamentoDias(dto.getPrazoMaximoCancelamentoDias());
        }
        if (dto.getTempoMinimoParaReservaDias() != null) {
            parametro.setTempoMinimoParaReservaDias(dto.getTempoMinimoParaReservaDias());
        }
        if (dto.getDuracaoMinimaDias() != null) {
            parametro.setDuracaoMinimaDias(dto.getDuracaoMinimaDias());
        }
        if (dto.getDuracaoMaximaDias() != null) {
            parametro.setDuracaoMaximaDias(dto.getDuracaoMaximaDias());
        }
        if (dto.getHorarioCheckIn() != null) {
            parametro.setHorarioCheckIn(dto.getHorarioCheckIn());
        }
        if (dto.getHorarioCheckOut() != null) {
            parametro.setHorarioCheckOut(dto.getHorarioCheckOut());
        }
        if (dto.getPercentualMultaCancelamento() != null) {
            parametro.setPercentualMultaCancelamento(dto.getPercentualMultaCancelamento());
        }

        return parametro;
    }

    public List<ParametroReservaDTO> toDTOList(List<ParametroReserva> parametros) {
        if (parametros == null) {
            return null;
        }
        return parametros.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDTO(ParametroReservaDTO dto, ParametroReserva parametroReserva) {
        if (dto == null || parametroReserva == null) {
            return;
        }
        if (dto.getBloquearReservaComPendencia() != null) {
            parametroReserva.setBloquearReservaComPendencia(dto.getBloquearReservaComPendencia());
        }
        if (dto.getMultaCancelamentoAtiva() != null) {
            parametroReserva.setMultaCancelamentoAtiva(dto.getMultaCancelamentoAtiva());
        }
        if (dto.getMaxReservasAtivasPorUsuario() != null) {
            parametroReserva.setMaxReservasAtivasPorUsuario(dto.getMaxReservasAtivasPorUsuario());
        }
        if (dto.getTempoEntreReservasDias() != null) {
            parametroReserva.setTempoEntreReservasDias(dto.getTempoEntreReservasDias());
        }
        if (dto.getPrazoMaximoCancelamentoDias() != null) {
            parametroReserva.setPrazoMaximoCancelamentoDias(dto.getPrazoMaximoCancelamentoDias());
        }
        if (dto.getTempoMinimoParaReservaDias() != null) {
            parametroReserva.setTempoMinimoParaReservaDias(dto.getTempoMinimoParaReservaDias());
        }
        if (dto.getDuracaoMinimaDias() != null) {
            parametroReserva.setDuracaoMinimaDias(dto.getDuracaoMinimaDias());
        }
        if (dto.getDuracaoMaximaDias() != null) {
            parametroReserva.setDuracaoMaximaDias(dto.getDuracaoMaximaDias());
        }
        if (dto.getHorarioCheckIn() != null) {
            parametroReserva.setHorarioCheckIn(dto.getHorarioCheckIn());
        }
        if (dto.getHorarioCheckOut() != null) {
            parametroReserva.setHorarioCheckOut(dto.getHorarioCheckOut());
        }
        if (dto.getPercentualMultaCancelamento() != null) {
            parametroReserva.setPercentualMultaCancelamento(dto.getPercentualMultaCancelamento());
        }
    }

}
