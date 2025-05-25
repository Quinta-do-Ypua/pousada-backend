package com.senai.pousadabackend.domain.Imagem;

import com.senai.pousadabackend.domain.quarto.Quarto;
import com.senai.pousadabackend.domain.quarto.UrlImagem;
import com.senai.pousadabackend.domain.quarto.service.QuartoService;
import com.senai.pousadabackend.domain.quarto.service.QuartoServiceImpl;
import com.senai.pousadabackend.exceptions.BusinessException;
import com.senai.pousadabackend.integration.UploadQuarto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImagemServiceImpl implements ImagemService {

    private final QuartoServiceImpl quartoService;
    private final UploadQuarto uploadQuarto;

    public ImagemServiceImpl(
            @Qualifier("quartoServiceImpl")
            QuartoServiceImpl quartoService,
            UploadQuarto uploadQuarto) {
        this.quartoService = quartoService;
        this.uploadQuarto = uploadQuarto;
    }

    private static final long TAMANHO_MAXIMO_ARQUIVO = 5 * 1024 * 1024;

    @Override
    public void uploadImagem(List<MultipartFile> imagens, Long idQuarto) {
        System.out.println("Chegaram imagens aqui: " + imagens);

        imagens.forEach(imagem -> {
            if (imagem.getSize() > TAMANHO_MAXIMO_ARQUIVO) {
                throw new BusinessException("O arquivo excede o limite de 5MB.");
            }
        });
    }

    @Override
    public void salvar(List<UrlImagem> urlsFormatadas, Long idQuarto) {
        Quarto quarto = quartoService.buscarPorIdComImagens(idQuarto);

        List<UrlImagem> imagensRemovidas = quarto.getUrlImagens().stream()
                .filter(imagem -> urlsFormatadas.stream()
                        .noneMatch(nova -> nova.getId() != null && nova.getId().equals(imagem.getId())))
                .toList();

        for (UrlImagem removida : imagensRemovidas) {
            if (removida.getFileId() != null) {
                uploadQuarto.deletarImagem(removida.getFileId());
            }
        }

        quarto.getUrlImagens().removeAll(imagensRemovidas);

        for (UrlImagem nova : urlsFormatadas) {
            if (nova.getId() == null) {
                nova.setQuarto(quarto);
                quarto.getUrlImagens().add(nova);
            }
        }

        quartoService.salvar(quarto);
    }
}
