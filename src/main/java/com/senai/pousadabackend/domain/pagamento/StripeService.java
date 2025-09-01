package com.senai.pousadabackend.domain.pagamento;

import com.senai.pousadabackend.domain.reserva.Reserva;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
public class StripeService {

    @Value("${stripe.chave_privada}")
    private String chavePrivada;

    @Value("${stripe.base_url_pagamento}")
    private String baseUrl;

    private static final String CURRENCY_BRL = "brl";
    private static final String ENDPOINT_SUCESSO = "/success";
    private static final String ENDPOINT_ERRO = "/cancel";

    public StripeResponseDTO checkoutProducts(Reserva reserva) {
        validarReserva(reserva);
        Stripe.apiKey = chavePrivada;

        try {
            SessionCreateParams params = buildSessionParams(reserva);
            Session session = Session.create(params);

            return buildSuccessResponse(session);
        } catch(StripeException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void validarReserva(Reserva reserva) {
        if (reserva == null) {
            throw new IllegalArgumentException("Reserva não pode ser nula");
        }

        if (reserva.getQuarto() == null) {
            throw new IllegalArgumentException("Quarto da reserva não pode ser nulo");
        }

        if (reserva.getQuarto().getNome() == null || reserva.getQuarto().getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do quarto não pode ser vazio");
        }

        if (reserva.getValorDaReserva() == null || reserva.getValorDaReserva().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor da reserva deve ser maior que zero");
        }
    }

    private SessionCreateParams buildSessionParams(Reserva reserva) {
        SessionCreateParams.LineItem lineItem = buildLineItem(reserva);

        return SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(baseUrl + ENDPOINT_SUCESSO)
                .setCancelUrl(baseUrl + ENDPOINT_ERRO)
                .addLineItem(lineItem)
                .putMetadata("reserva_id", String.valueOf(reserva.getId()))
                .putMetadata("quarto_id", String.valueOf(reserva.getQuarto().getId()))
                .setExpiresAt(Instant.now().plus(30, ChronoUnit.MINUTES).getEpochSecond())
                .build();
    }

    private SessionCreateParams.LineItem buildLineItem(Reserva reserva) {
        SessionCreateParams.LineItem.PriceData.ProductData produto =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(reserva.getQuarto().getNome())
                        .setDescription(formataMensagemDoProduto(reserva))
                        .build();

        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(CURRENCY_BRL)
                        .setProductData(produto)
                        .setUnitAmount(converterValorPadraoStripe(reserva.getValorDaReserva()))
                        .build();

        return SessionCreateParams.LineItem.builder()
                .setQuantity(1L)
                .setPriceData(priceData)
                .build();
    }

    private String formataMensagemDoProduto(Reserva reserva) {
        return String.format("Reserva do quarto %s - Check-in: %s - Check-out: %s",
                reserva.getQuarto().getNome(),
                reserva.getCheckIn().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                reserva.getCheckOut().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    private Long converterValorPadraoStripe(BigDecimal valor) {
        return valor.multiply(BigDecimal.valueOf(100)).longValue();
    }

    private StripeResponseDTO buildSuccessResponse(Session session) {
        return StripeResponseDTO.builder()
                .status("SUCCESS")
                .message("Payment session created")
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();
    }
}
