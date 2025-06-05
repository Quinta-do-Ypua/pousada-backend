package com.senai.pousadabackend.domain.Imagem.service;

import com.senai.pousadabackend.domain.Imagem.ImagemQuarto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Validated
public interface ImagemService {

    void uploadImagem(
            @NotEmpty(message = "Deve haver no mínimo uma imagem vinculada")
            List<MultipartFile> imagens,
            @NotNull(message = "O id do quarto é obrigatório")
            Long idQuarto);

    void salvar(
            @NotEmpty(message = "Deve haver no mínimo uma url vinculada")
            List<ImagemQuarto> urlsFormatadas,
            @NotNull(message = "O id do quarto é obrigatório")
            Long idQuarto);

    List<ImagemQuarto> listarPor(
            @NotNull(message = "O id do quarto é obrigatório")
            Long idQuarto);

    void deletar(
            @NotNull(message = "A imagem é obrigatório")
            ImagemQuarto imagemQuarto);
}
