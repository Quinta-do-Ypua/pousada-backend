package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.domain.pagamento.StripeResponseDTO;
import com.senai.pousadabackend.domain.pagamento.StripeService;
import com.senai.pousadabackend.domain.reserva.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stripe")
public class StripeController {

    @Autowired
    private StripeService stripeService;

    @PostMapping
    public ResponseEntity<StripeResponseDTO> uploadImagem(@RequestBody Reserva reserva) {
        return ResponseEntity.ok(stripeService.checkoutProducts(reserva));
    }
}
