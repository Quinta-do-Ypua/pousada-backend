package com.senai.pousadabackend.controllers;

import com.senai.pousadabackend.domain.Imagem.configuracao.ImagemConfiguracaoDTO;
import com.senai.pousadabackend.domain.Imagem.configuracao.ImagemConfiguracaoMapper;
import com.senai.pousadabackend.domain.Imagem.configuracao.service.ImagemConfiguracaoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/imagens-configuracao")
public class ImagemConfiguracaoController {

    private final ImagemConfiguracaoService service;
    private final ImagemConfiguracaoMapper mapper;

    public ImagemConfiguracaoController(
            @Qualifier("imagemConfiguracaoServiceProxy")
            ImagemConfiguracaoService service,
            ImagemConfiguracaoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/room")
    public ResponseEntity<String> uploadImagem(
            @RequestParam("imagens") List<MultipartFile> imagens,
            @RequestParam("idConfiguracao") Long idConfiguracao
    ) {
        service.uploadImagem(imagens, idConfiguracao);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<List<ImagemConfiguracaoDTO>> listarPorIdQuarto(@PathVariable("id") Long idConfiguracao) {
        return ResponseEntity.ok(service.listarPor(idConfiguracao).stream().map(mapper::toDTO).toList());
    }

    @DeleteMapping()
    public ResponseEntity<String> uploadImagem(@RequestBody ImagemConfiguracaoDTO dto) {
        service.deletar(mapper.toEntity(dto));
        return ResponseEntity.ok().build();
    }

}
