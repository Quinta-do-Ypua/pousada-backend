package com.senai.pousadabackend.domain.reserva;

import com.senai.pousadabackend.domain.reserva.dto.ReservaResumidaDto;
import com.senai.pousadabackend.domain.cliente.ClienteService;
import com.senai.pousadabackend.domain.complemento.ComplementoMapper;
import com.senai.pousadabackend.domain.cupom.Cupom;
import com.senai.pousadabackend.domain.cupom.CupomRepository;
import com.senai.pousadabackend.domain.quarto.QuartoService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReservaResumidaMapper {

    private final QuartoService quartoService;
    private final ClienteService clienteService;
    private final ComplementoMapper complementoMapper;
    private final CupomRepository cupomRepository;

    public ReservaResumidaMapper(QuartoService quartoService,
                                 ClienteService clienteService,
                                 ComplementoMapper complementoMapper,
                                 CupomRepository cupomRepository) {
        this.quartoService = quartoService;
        this.clienteService = clienteService;
        this.complementoMapper = complementoMapper;
        this.cupomRepository = cupomRepository;
    }

    public Reserva toReserva(ReservaResumidaDto reservaResumidaDto) {
        return Reserva.builder()
                .id(reservaResumidaDto.getId())
                .checkIn(reservaResumidaDto.getCheckIn())
                .checkOut(reservaResumidaDto.getCheckOut())
                .observacao(reservaResumidaDto.getObservacao())
                .valorDaReserva(reservaResumidaDto.getValorDaReserva())
                .cliente(clienteService.buscarPorId(reservaResumidaDto.getClienteId()))
                .quarto(quartoService.buscarPorId(reservaResumidaDto.getQuartoId()))
                .complementos(
                        Optional.ofNullable(reservaResumidaDto.getComplementos())
                                .map(lista -> lista.stream().map(complementoMapper::toEntity).toList())
                                .orElse(null))
                .statusDaReserva(reservaResumidaDto.getStatusDaReserva())
                .cupom(resolverCupom(reservaResumidaDto.getCupomCodigo()))
                .build();
    }

    private Cupom resolverCupom(String cupomCodigo) {
        if (cupomCodigo == null || cupomCodigo.isBlank()) return null;
        return cupomRepository.findByCodigo(cupomCodigo);
    }

}
