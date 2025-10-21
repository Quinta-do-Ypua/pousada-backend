package com.senai.pousadabackend.domain.Imagem.configuracao.service;

import com.senai.pousadabackend.domain.Imagem.configuracao.ImagemConfiguracao;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Validated
public interface ImagemConfiguracaoService {

    void uploadImagem(
            @NotEmpty(message = "Deve haver no mínimo uma imagem vinculada")
            List<MultipartFile> imagens,
            @NotNull(message = "O id da configuração é obrigatória")
            Long idConfiguracao);

    void salvar(
            @NotEmpty(message = "Deve haver no mínimo uma url vinculada")
            List<ImagemConfiguracao> urlsFormatadas,
            @NotNull(message = "O id da configuração é obrigatória")
            Long idConfiguracao);

    List<ImagemConfiguracao> listarPor(
            @NotNull(message = "O id da configuração é obrigatória")
            Long idConfiguracao);

    void deletar(
            @NotNull(message = "A imagem é obrigatória")
            ImagemConfiguracao imagemConfiguracao);

}
