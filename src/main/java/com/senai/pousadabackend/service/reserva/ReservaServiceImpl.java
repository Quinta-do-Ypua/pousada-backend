package com.senai.pousadabackend.service.reserva;

import com.senai.pousadabackend.entity.*;
import com.senai.pousadabackend.entity.enums.StatusDaReserva;
import com.senai.pousadabackend.exceptions.CancelamentoDeReservaConcluidaException;
import com.senai.pousadabackend.exceptions.DataDaReservaInvalida;
import com.senai.pousadabackend.exceptions.ExisteReservaAbertaParaEsseCliente;
import com.senai.pousadabackend.exceptions.ExisteReservaParaEssaDataException;
import com.senai.pousadabackend.repository.NotaFiscalRepository;
import com.senai.pousadabackend.repository.ReservaRepository;
import com.senai.pousadabackend.service.BaseService;
import com.senai.pousadabackend.service.complemento.ComplementoService;
import com.senai.pousadabackend.service.quarto.QuartoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ReservaServiceImpl extends BaseService<Reserva, Long, ReservaRepository> implements ReservaService {

    private final ReservaRepository reservaRepository;

    private final QuartoService quartoService;

    private final NotaFiscalRepository notaFiscalRepository;

    private final ComplementoService complementoService;

    public ReservaServiceImpl(ReservaRepository repo,
                              @Qualifier("quartoServiceProxy") QuartoService quartoService,
                              NotaFiscalRepository notaFiscalRepository,
                              @Qualifier("complementoServiceProxy") ComplementoService complementoService) {
        super(repo);
        this.reservaRepository = repo;
        this.quartoService = quartoService;
        this.notaFiscalRepository = notaFiscalRepository;
        this.complementoService = complementoService;
    }

    @Override
    public Reserva salvar(Reserva reserva) {
        if (reserva.getId() == null) {
            inicializarReserva(reserva);
            validarNovaReserva(reserva);
            gerarNotaFiscal(reserva);
        }
        return super.salvar(reserva);
    }

    @Override
    public Reserva cancelarPorId(Long id) {
        Reserva reserva = buscarPorId(id);
        validarCancelamento(reserva);
        reserva.setStatusDaReserva(StatusDaReserva.CANCELADA);
        this.salvar(reserva);
        return reserva;
    }

    private void validarNovaReserva(Reserva reserva) {
        validarDatas(reserva);
        validarStatusInicial(reserva);
        validarDisponibilidadeDoQuarto(reserva);
        validarPendenciasDoCliente(reserva.getCliente());
    }

    private void inicializarReserva(Reserva reserva) {
        definirStatusPadrao(reserva);
    }

    private void validarCancelamento(Reserva reserva) {
        if (reserva.getStatusDaReserva() == StatusDaReserva.CONCLUIDA
                || reserva.getStatusDaReserva() == StatusDaReserva.FECHADA) {
            throw new CancelamentoDeReservaConcluidaException();
        }
    }

    private void validarDatas(Reserva reserva) {
        if (reserva.getCheckIn() == null || reserva.getCheckOut() == null) {
            throw new NullPointerException("A data de CheckIn e CheckOut são obrigatórias");
        }

        if (!reserva.getCheckIn().isBefore(reserva.getCheckOut())) {
            throw new DataDaReservaInvalida();
        }
    }

    private void validarStatusInicial(Reserva reserva) {
        if (reserva.getStatusDaReserva() == StatusDaReserva.CANCELADA
                || reserva.getStatusDaReserva() == StatusDaReserva.FECHADA) {
            throw new CancelamentoDeReservaConcluidaException("Não é possível criar uma reserva cancelada ou fechada.");
        }
    }

    private void validarDisponibilidadeDoQuarto(Reserva reserva) {
        boolean quartoOcupado = !reservaRepository.findQuartosEntreCheckInECheckOut(
                reserva.getCheckIn(), reserva.getCheckOut(), reserva.getQuarto()).isEmpty();

        if (quartoOcupado) {
            throw new ExisteReservaParaEssaDataException();
        }
    }

    private void validarPendenciasDoCliente(Cliente cliente) {
        boolean clienteTemReservaAberta = reservaRepository.findReservaByCliente(cliente).stream()
                .anyMatch(r -> r.getStatusDaReserva() == StatusDaReserva.ABERTA);

        if (clienteTemReservaAberta) {
            throw new ExisteReservaAbertaParaEsseCliente();
        }
    }

    private void definirStatusPadrao(Reserva reserva) {
        if (reserva.getStatusDaReserva() == null) {
            reserva.setStatusDaReserva(StatusDaReserva.ABERTA);
        }
    }



    private void gerarNotaFiscal(Reserva reserva) {
        NotaFiscal notaFiscal = new NotaFiscal();
        notaFiscal.setNumero("NF-" + System.currentTimeMillis());
        notaFiscal.setDataCadastro(LocalDate.now());
        notaFiscal.setCliente(reserva.getCliente());

        List<NotaFiscalItem> itens = new ArrayList<>();

        Quarto quarto = quartoService.buscarPorId(reserva.getQuarto().getId());

        long dias = Math.max(1, ChronoUnit.DAYS.between(reserva.getCheckIn(), reserva.getCheckOut()));
        NotaFiscalItem itemQuarto = new NotaFiscalItem();
        itemQuarto.setNotaFiscal(notaFiscal);
        itemQuarto.setItem(ItemNF.builder()
                        .itemId(quarto.getId())
                        .tipoItem("Quarto")
                        .valorUnitario(quarto.getValorDiaria()).build());
        itemQuarto.setQuantidade((int) dias);
        itemQuarto.setValorUnitario(quarto.getValorDiaria());
        itemQuarto.setValorTotal(quarto.getValorDiaria().multiply(BigDecimal.valueOf(dias)));

        itens.add(itemQuarto);

        for (var complementoRef : reserva.getComplementos()) {
            Complemento complemento = complementoService.buscarPorId(complementoRef.getId());

            NotaFiscalItem itemComp = new NotaFiscalItem();
            itemComp.setNotaFiscal(notaFiscal);
            itemComp.setItem(ItemNF.builder()
                            .tipoItem("Complemento")
                            .itemId(complemento.getId())
                            .valorUnitario(complemento.getValor()).build());
            itemComp.setQuantidade(1);
            itemComp.setValorUnitario(complemento.getValor());
            itemComp.setValorTotal(complemento.getValor());

            itens.add(itemComp);
        }

        notaFiscal.setItens(itens);
        notaFiscal.setValorTotal(itens.stream()
                .map(NotaFiscalItem::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        notaFiscalRepository.saveAndFlush(notaFiscal);
    }



}
