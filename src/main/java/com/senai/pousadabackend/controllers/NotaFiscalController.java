package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.dto.NotaFiscalDto;
import com.senai.pousadabackend.mappers.NotaFiscalMapper;
import com.senai.pousadabackend.service.nota_fiscal.NotaFiscalService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("notas-fiscais")
public class NotaFiscalController {

    private final NotaFiscalService notaFiscalService;

    private final NotaFiscalMapper notaFiscalMapper;

    public NotaFiscalController(@Qualifier("notaFiscalServiceProxy") NotaFiscalService notaFiscalService,
                                NotaFiscalMapper notaFiscalMapper) {
        this.notaFiscalService = notaFiscalService;
        this.notaFiscalMapper = notaFiscalMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotaFiscalDto> buscarPorId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(notaFiscalMapper.toDTO(notaFiscalService.buscarPorId(id)));
    }

}
