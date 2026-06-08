package com.senai.pousadabackend.domain.relatorio;

import com.senai.pousadabackend.core.enums.StatusDaReserva;
import com.senai.pousadabackend.domain.relatorio.dto.FaturamentoPorQuartoDTO;
import com.senai.pousadabackend.domain.relatorio.dto.RelatorioReservasDTO;
import com.senai.pousadabackend.domain.relatorio.dto.RelatorioResumoDTO;
import com.senai.pousadabackend.domain.reserva.Reserva;
import com.senai.pousadabackend.domain.reserva.ReservaMapper;
import com.senai.pousadabackend.domain.reserva.ReservaRepository;
import com.senai.pousadabackend.domain.reserva.dto.ReservaDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;

    public RelatorioService(ReservaRepository reservaRepository, ReservaMapper reservaMapper) {
        this.reservaRepository = reservaRepository;
        this.reservaMapper = reservaMapper;
    }

    public RelatorioResumoDTO getResumo() {
        List<Object[]> statusCounts = reservaRepository.countPorStatus();

        Map<String, Long> porStatus = new LinkedHashMap<>();
        for (StatusDaReserva s : StatusDaReserva.values()) {
            porStatus.put(s.name(), 0L);
        }

        long totalReservas = 0;
        long canceladas = 0;
        for (Object[] row : statusCounts) {
            StatusDaReserva status = (StatusDaReserva) row[0];
            Long count = (Long) row[1];
            porStatus.put(status.name(), count);
            totalReservas += count;
            if (status == StatusDaReserva.CANCELADA) {
                canceladas = count;
            }
        }

        BigDecimal faturamentoTotal = reservaRepository.somarFaturamentoTotal();
        if (faturamentoTotal == null) faturamentoTotal = BigDecimal.ZERO;

        LocalDateTime now = LocalDateTime.now();
        Long reservasEsteMes = reservaRepository.countPorMes(now.getYear(), now.getMonthValue());
        if (reservasEsteMes == null) reservasEsteMes = 0L;

        BigDecimal faturamentoEsteMes = reservaRepository.somarFaturamentoPorMes(now.getYear(), now.getMonthValue());
        if (faturamentoEsteMes == null) faturamentoEsteMes = BigDecimal.ZERO;

        double taxaCancelamento = totalReservas > 0
                ? Math.round((canceladas * 100.0 / totalReservas) * 10.0) / 10.0
                : 0.0;

        return RelatorioResumoDTO.builder()
                .totalReservas(totalReservas)
                .reservasPorStatus(porStatus)
                .faturamentoTotal(faturamentoTotal)
                .reservasEsteMes(reservasEsteMes)
                .faturamentoEsteMes(faturamentoEsteMes)
                .taxaCancelamento(taxaCancelamento)
                .build();
    }

    public RelatorioReservasDTO getRelatorio(LocalDate de, LocalDate ate, Long quartoId, Long clienteId, String statusStr) {
        LocalDateTime inicio = de.atStartOfDay();
        LocalDateTime fim = ate.atTime(23, 59, 59);
        StatusDaReserva status = (statusStr != null && !statusStr.isBlank())
                ? StatusDaReserva.valueOf(statusStr)
                : null;

        List<Reserva> reservas = reservaRepository.findRelatorio(inicio, fim, quartoId, clienteId, status);

        Map<String, Long> porStatus = new LinkedHashMap<>();
        for (StatusDaReserva s : StatusDaReserva.values()) {
            porStatus.put(s.name(), 0L);
        }

        BigDecimal faturamentoTotal = BigDecimal.ZERO;
        BigDecimal totalDescontoCupons = BigDecimal.ZERO;
        long contaNaoCanceladas = 0;

        for (Reserva r : reservas) {
            porStatus.merge(r.getStatusDaReserva().name(), 1L, Long::sum);
            if (r.getStatusDaReserva() != StatusDaReserva.CANCELADA) {
                faturamentoTotal = faturamentoTotal.add(
                        r.getValorDaReserva() != null ? r.getValorDaReserva() : BigDecimal.ZERO);
                contaNaoCanceladas++;
                if (r.getDescontoCupom() != null && r.getDescontoCupom().compareTo(BigDecimal.ZERO) > 0) {
                    totalDescontoCupons = totalDescontoCupons.add(r.getDescontoCupom());
                }
            }
        }

        BigDecimal ticketMedio = contaNaoCanceladas > 0
                ? faturamentoTotal.divide(BigDecimal.valueOf(contaNaoCanceladas), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        List<Object[]> porQuartoRows = reservaRepository.findFaturamentoPorQuartoNoPeriodo(inicio, fim, quartoId, clienteId);
        List<FaturamentoPorQuartoDTO> faturamentoPorQuarto = porQuartoRows.stream()
                .map(row -> new FaturamentoPorQuartoDTO((String) row[0], (BigDecimal) row[1]))
                .collect(Collectors.toList());

        List<ReservaDTO> reservaDTOs = reservas.stream()
                .map(reservaMapper::toDTO)
                .collect(Collectors.toList());

        return RelatorioReservasDTO.builder()
                .reservas(reservaDTOs)
                .totalReservas((long) reservas.size())
                .reservasPorStatus(porStatus)
                .faturamentoTotal(faturamentoTotal)
                .ticketMedio(ticketMedio)
                .totalDescontoCupons(totalDescontoCupons)
                .faturamentoPorQuarto(faturamentoPorQuarto)
                .build();
    }
}
