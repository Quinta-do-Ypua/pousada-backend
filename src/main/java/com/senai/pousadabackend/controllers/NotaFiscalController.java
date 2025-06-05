package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.domain.resumo.ResumoReservaDto;
import com.senai.pousadabackend.domain.resumo.ResumoReservaMapper;
import com.senai.pousadabackend.domain.resumo.ResumoReservaService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("notas-fiscais")
public class NotaFiscalController {

    private final ResumoReservaService resumoReservaService;

    private final ResumoReservaMapper resumoReservaMapper;

    public NotaFiscalController(@Qualifier("resumoReservaServiceProxy") ResumoReservaService resumoReservaService,
                                ResumoReservaMapper resumoReservaMapper) {
        this.resumoReservaService = resumoReservaService;
        this.resumoReservaMapper = resumoReservaMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumoReservaDto> buscarPorId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(resumoReservaMapper.toDTO(resumoReservaService.buscarPorId(id)));
    }

}
