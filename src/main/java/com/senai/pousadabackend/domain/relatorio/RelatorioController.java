package com.senai.pousadabackend.domain.relatorio;

import com.senai.pousadabackend.domain.relatorio.dto.RelatorioReservasDTO;
import com.senai.pousadabackend.domain.relatorio.dto.RelatorioResumoDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/resumo")
    public RelatorioResumoDTO getResumo() {
        return relatorioService.getResumo();
    }

    @GetMapping("/reservas")
    public RelatorioReservasDTO getRelatorio(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate de,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ate,
            @RequestParam(required = false) Long quartoId,
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) String status
    ) {
        return relatorioService.getRelatorio(de, ate, quartoId, clienteId, status);
    }
}
