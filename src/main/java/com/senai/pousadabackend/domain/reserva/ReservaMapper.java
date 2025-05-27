package com.senai.pousadabackend.domain.reserva;

import com.senai.pousadabackend.domain.complemento.ComplementoMapper;
import com.senai.pousadabackend.domain.quarto.QuartoMapper;
import com.senai.pousadabackend.core.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ReservaMapper implements BaseMapper<Reserva, ReservaDTO> {


    private final QuartoMapper quartoMapper;
    private final ComplementoMapper complementoMapper;

    public ReservaMapper(QuartoMapper quartoMapper, ComplementoMapper complementoMapper) {
        this.quartoMapper = quartoMapper;
        this.complementoMapper = complementoMapper;
    }

    @Override
    public ReservaDTO toDTO(Reserva reserva) {
        return ReservaDTO.builder()
                .id(reserva.getId())
                .statusDaReserva(reserva.getStatusDaReserva())
                .quarto(quartoMapper.toDTO(reserva.getQuarto()))
                .valorDaReserva(reserva.getValorDaReserva())
                .checkIn(reserva.getCheckIn())
                .checkOut(reserva.getCheckOut())
                .cliente(reserva.getCliente())
                .observacao(reserva.getObservacao())
                .complementos(reserva.getComplementos().stream().map(complementoMapper::toDTO).collect(Collectors.toList()))
                .build();
    }

    @Override
    public Reserva toEntity(ReservaDTO reservaDTO) {
        return Reserva.builder()
                .id(reservaDTO.getId())
                .statusDaReserva(reservaDTO.getStatusDaReserva())
                .quarto(quartoMapper.toEntity(reservaDTO.getQuarto()))
                .valorDaReserva(reservaDTO.getValorDaReserva())
                .checkIn(reservaDTO.getCheckIn())
                .checkOut(reservaDTO.getCheckOut())
                .cliente(reservaDTO.getCliente())
                .observacao(reservaDTO.getObservacao())
                .checkOut(reservaDTO.getCheckOut())
                .cliente(reservaDTO.getCliente())
                .observacao(reservaDTO.getObservacao())
                .valorDaReserva(reservaDTO.getValorDaReserva())
                .complementos(reservaDTO.getComplementos().stream().map(complementoMapper::toEntity).collect(Collectors.toList()))
                .build();
    }
}
