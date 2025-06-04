package com.senai.pousadabackend.mappers;

import com.senai.pousadabackend.dto.ReservaDTO;
import com.senai.pousadabackend.entity.Reserva;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ReservaMapper implements BaseMapper<Reserva, ReservaDTO> {

    private final QuartoMapper quartoMapper;
    private final ComplementoMapper complementoMapper;
    private final ClienteMapper clienteMapper;

    public ReservaMapper(QuartoMapper quartoMapper, ComplementoMapper complementoMapper, ClienteMapper clienteMapper) {
        this.quartoMapper = quartoMapper;
        this.complementoMapper = complementoMapper;
        this.clienteMapper = clienteMapper;
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
                .cliente(clienteMapper.toDTO(reserva.getCliente()))
                .observacao(reserva.getObservacao())
                .complementos(
                        Optional.ofNullable(reserva.getComplementos())
                                .orElse(List.of())
                                .stream()
                                .map(complementoMapper::toDTO)
                                .toList()
                )
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
                .cliente(clienteMapper.toEntity(reservaDTO.getCliente()))
                .observacao(reservaDTO.getObservacao())
                .checkOut(reservaDTO.getCheckOut())
                .observacao(reservaDTO.getObservacao())
                .valorDaReserva(reservaDTO.getValorDaReserva())
                .complementos(Optional.ofNullable(reservaDTO.getComplementos())
                        .orElse(List.of())
                        .stream()
                        .map(complementoMapper::toEntity)
                        .toList())
                .build();
    }
}
