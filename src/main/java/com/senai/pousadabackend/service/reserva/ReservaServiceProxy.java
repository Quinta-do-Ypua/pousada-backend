package com.senai.pousadabackend.service.reserva;

import com.senai.pousadabackend.entity.Quarto;
import com.senai.pousadabackend.entity.Reserva;
import com.senai.pousadabackend.repository.ReservaRepository;
import com.senai.pousadabackend.service.email.EmailService;
import org.springframework.beans.factory.annotation.Qualifier;
import com.senai.pousadabackend.service.BaseService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReservaServiceProxy extends BaseService<Reserva, Long, ReservaRepository> implements ReservaService {

    private final ReservaService reservaService;

    private final EmailService emailService;

    public ReservaServiceProxy(ReservaRepository repo, @Qualifier("reservaServiceImpl") ReservaService reservaService, EmailService emailService) {
        super(repo);
        this.reservaService = reservaService;
        this.emailService = emailService;
    }

    @Override
    public Reserva cancelarPorId(Long id) {
        Reserva reserva = this.buscarPorId(id);
        emailService.enviar("Cancelamento de reserva",
                "Estamos passando para avisar que sua reserva do quarto: " + reserva.getQuarto() + " de número: " + reserva.getId() + " Foi cancelada.",
                reserva.getCliente());
        return reservaService.cancelarPorId(id);
    }

    @Override
    public Reserva salvar(Reserva reserva) {
        if (reserva.isNovo()) {
            emailService.enviar("Nova reserva!",
                    "Estamos passando para avisar que sua reserva do quarto: " + reserva.getQuarto() + " de número: " + reserva.getId() + " Foi efetuada com sucesso!.",
                    reserva.getCliente());
        } else {
            emailService.enviar("Alteração na sua reserva do quarto: " + reserva.getQuarto(),
                    "Estamos passando para avisar que sua reserva foi alterada: " + reserva,
                    reserva.getCliente());
        }
        return super.salvar(reserva);
    }
}
