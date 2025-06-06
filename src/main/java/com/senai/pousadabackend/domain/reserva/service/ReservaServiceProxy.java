package com.senai.pousadabackend.domain.reserva.service;

import com.senai.pousadabackend.domain.email.EmailService;
import com.senai.pousadabackend.domain.reserva.Reserva;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReservaServiceProxy implements ReservaService {

    private final ReservaService delegate;

    private final EmailService emailService;

    public ReservaServiceProxy(@Qualifier("reservaServiceImpl") ReservaService reservaService,
                               EmailService emailService) {
        this.delegate = reservaService;
        this.emailService = emailService;
    }

    @Override
    public Reserva cancelarPorId(Long id) {
        Reserva reserva = this.buscarPorId(id);
        emailService.enviar("Cancelamento de reserva",
                "Estamos passando para avisar que sua reserva do quarto: " + reserva.getQuarto() + " de número: " + reserva.getId() + " Foi cancelada.",
                reserva.getCliente());
        return delegate.cancelarPorId(id);
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
        return delegate.salvar(reserva);
    }

    @Override
    public Reserva buscarPorId(Long id) {
        return delegate.buscarPorId(id);
    }

    @Override
    public Reserva excluir(Long id) {
        return delegate.excluir(id);
    }

    @Override
    public void throwIfNotExists(Long aLong) {
        delegate.throwIfNotExists(aLong);
    }

    @Override
    public Page<Reserva> buscarPorSpecification(String parametro, Pageable pageable) {
        return delegate.buscarPorSpecification(parametro, pageable);
    }

    @Override
    public Page<Reserva> listarPaginado(Pageable pageable) {
        return delegate.listarPaginado(pageable);
    }

    }
