package com.senai.pousadabackend.domain.reserva;

import com.senai.pousadabackend.domain.cliente.ClienteMapper;
import com.senai.pousadabackend.domain.cliente.service.ClienteService;
import com.senai.pousadabackend.domain.complemento.ComplementoMapper;
import com.senai.pousadabackend.domain.quarto.QuartoMapper;
import com.senai.pousadabackend.domain.quarto.service.QuartoService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReservaResumidaMapper {

    private final QuartoService quartoService;
    private final QuartoMapper quartoMapper;
    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;
    private final ComplementoMapper complementoMapper;

    public ReservaResumidaMapper(QuartoService quartoService,
                                 QuartoMapper quartoMapper,
                                 ClienteService clienteService,
                                 ClienteMapper clienteMapper,
                                 ComplementoMapper complementoMapper) {
        this.quartoService = quartoService;
        this.quartoMapper = quartoMapper;
        this.clienteService = clienteService;
        this.clienteMapper = clienteMapper;
        this.complementoMapper = complementoMapper;
    }

    public ReservaDTO toReservaDto(ReservaResumidaDto reservaResumidaDto) {
        return ReservaDTO.builder()
                .id(reservaResumidaDto.getId())
                .statusDaReserva(reservaResumidaDto.getStatusDaReserva())
                .quarto(quartoMapper.toDTO(quartoService.buscarPorId(reservaResumidaDto.getQuartoId())))
                .valorDaReserva(reservaResumidaDto.getValorDaReserva())
                .observacao(reservaResumidaDto.getObservacao())
                .complementos(reservaResumidaDto.getComplementos())
                .cliente(clienteMapper.toDTO(clienteService.buscarPorId(reservaResumidaDto.getClienteId())))
                .checkOut(reservaResumidaDto.getCheckOut())
                .checkIn(reservaResumidaDto.getCheckIn())
                .build();
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
                                .orElse(null)
                )
                .statusDaReserva(reservaResumidaDto.getStatusDaReserva())
                .build();
    }

}
