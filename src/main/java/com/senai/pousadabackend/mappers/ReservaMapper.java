package com.senai.pousadabackend.mappers;

import com.senai.pousadabackend.dto.ReservaDTO;
import com.senai.pousadabackend.entity.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper implements BaseMapper<Reserva, ReservaDTO> {

    @Override
    public ReservaDTO toDTO(Reserva reserva) {
        return ReservaDTO.builder()
                .id(reserva.getId())
                .status(reserva.getStatus())
                .quarto(reserva.getQuarto())
                .valorDaReserva(reserva.getValorDaReserva())
                .checkIn(reserva.getCheckIn())
                .complementos(reserva.getComplementos())
                .checkOut(reserva.getCheckOut())
                .cliente(reserva.getCliente())
                .observacao(reserva.getObservacao())
                .valorComplementos(reserva.getValorComplementos())
                .valorDaDiariaDoQuarto(reserva.getValorDaDiariaDoQuarto())
                .valorTotalDoQuarto(reserva.getValorTotalDoQuarto())
                .build();
    }

    @Override
    public Reserva toEntity(ReservaDTO reservaDTO) {
        return Reserva.builder()
                .id(reservaDTO.getId())
                .status(reservaDTO.getStatus())
                .quarto(reservaDTO.getQuarto())
                .valorDaReserva(reservaDTO.getValorDaReserva())
                .checkIn(reservaDTO.getCheckIn())
                .checkOut(reservaDTO.getCheckOut())
                .cliente(reservaDTO.getCliente())
                .observacao(reservaDTO.getObservacao())
                .valorComplementos(reservaDTO.getValorComplementos())
                .checkOut(reservaDTO.getCheckOut())
                .cliente(reservaDTO.getCliente())
                .observacao(reservaDTO.getObservacao())
                .valorComplementos(reservaDTO.getValorComplementos())
                .valorDaReserva(reservaDTO.getValorDaReserva())
                .valorDaDiariaDoQuarto(reservaDTO.getValorDaDiariaDoQuarto())
                .valorTotalDoQuarto(reservaDTO.getValorTotalDoQuarto())
                .build();
    }
}
