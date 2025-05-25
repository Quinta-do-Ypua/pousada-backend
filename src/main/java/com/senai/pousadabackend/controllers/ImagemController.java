package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.domain.Imagem.ImagemService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/imagens")
public class ImagemController {

    private final ImagemService service;

    public ImagemController(
            @Qualifier("imagemServiceProxy")
            ImagemService service) {
        this.service = service;
    }

    @PostMapping("/room")
    public ResponseEntity<String> uploadImagem(
            @RequestParam("imagens") List<MultipartFile> imagens,
            @RequestParam("idQuarto") Long idQuarto
    ) {
        service.uploadImagem(imagens, idQuarto);
        return ResponseEntity.ok().build();
    }
}
