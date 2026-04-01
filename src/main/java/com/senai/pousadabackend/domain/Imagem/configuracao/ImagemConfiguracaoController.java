package com.senai.pousadabackend.domain.Imagem.configuracao;

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

    @PostMapping("/tema")
    public ResponseEntity<String> uploadImagem(
            @RequestParam("imagens") List<MultipartFile> imagens,
            @RequestParam("idTemaSistema") Long idTemaSistema
    ) {
        service.uploadImagem(imagens, idTemaSistema);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<List<ImagemConfiguracaoDTO>> listarPorIdTema(@PathVariable("id") Long idTemaSistema) {
        return ResponseEntity.ok(service.listarPor(idTemaSistema).stream().map(mapper::toDTO).toList());
    }

    @DeleteMapping()
    public ResponseEntity<String> uploadImagem(@RequestBody ImagemConfiguracaoDTO dto) {
        service.deletar(mapper.toEntity(dto));
        return ResponseEntity.ok().build();
    }

}
