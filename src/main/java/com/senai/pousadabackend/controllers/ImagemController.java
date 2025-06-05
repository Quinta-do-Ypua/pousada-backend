package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.domain.Imagem.ImagemMapper;
import com.senai.pousadabackend.domain.Imagem.ImagemQuartoDTO;
import com.senai.pousadabackend.domain.Imagem.service.ImagemService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/imagens")
public class ImagemController {

    private final ImagemService service;
    private final ImagemMapper mapper;

    public ImagemController(
            @Qualifier("imagemServiceProxy")
            ImagemService service,
            ImagemMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/room")
    public ResponseEntity<String> uploadImagem(
            @RequestParam("imagens") List<MultipartFile> imagens,
            @RequestParam("idQuarto") Long idQuarto
    ) {
        service.uploadImagem(imagens, idQuarto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<List<ImagemQuartoDTO>> listarPorIdQuarto(@PathVariable("id") Long idQuarto) {
        return ResponseEntity.ok(service.listarPor(idQuarto).stream().map(mapper::toDTO).toList());
    }

    @DeleteMapping()
    public ResponseEntity<String> uploadImagem(@RequestBody ImagemQuartoDTO dto) {
        service.deletar(mapper.toEntity(dto));
        return ResponseEntity.ok().build();
    }
}
