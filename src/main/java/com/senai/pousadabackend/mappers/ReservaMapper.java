package com.senai.pousadabackend.mappers;

import com.senai.pousadabackend.dto.ReservaDTO;
import com.senai.pousadabackend.entity.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper implements BaseMapper<Reserva, ReservaDTO> {


    private final QuartoMapper quartoMapper;

    public ReservaMapper(QuartoMapper quartoMapper) {
        this.quartoMapper = quartoMapper;
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
                .build();
    }
}
