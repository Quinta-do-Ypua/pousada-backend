package com.senai.pousadabackend.domain.reserva;

import com.senai.pousadabackend.core.enums.StatusDaReserva;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservaConclusaoScheduler {

    private final ReservaRepository reservaRepository;

    @Transactional
    @Scheduled(cron = "0 0 6 * * *")
    public void concluirReservasExpiradas() {
        List<Reserva> reservas = reservaRepository.findReservasParaConcluir(LocalDateTime.now());
        if (reservas.isEmpty()) return;

        reservas.forEach(r -> r.setStatusDaReserva(StatusDaReserva.CONCLUIDA));
        reservaRepository.saveAll(reservas);
        log.info("{} reserva(s) concluída(s) automaticamente.", reservas.size());
    }
}
