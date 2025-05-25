package com.senai.pousadabackend.domain.Imagem;

import com.senai.pousadabackend.domain.quarto.UrlImagem;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Validated
public interface ImagemService {

    void uploadImagem(@NotEmpty(message = "Deve haver no mínimo uma imagem vinculada") List<MultipartFile> imagens, Long idQuarto);

    void salvar(@NotEmpty(message = "Deve haver no mínimo uma url vinculada") List<UrlImagem> urlsFormatadas, Long idQuarto);
}
