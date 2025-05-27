package com.senai.pousadabackend.domain.Imagem;

import com.senai.pousadabackend.domain.quarto.Quarto;
import com.senai.pousadabackend.domain.quarto.ImagemQuarto;
import com.senai.pousadabackend.domain.quarto.service.QuartoService;
import com.senai.pousadabackend.exceptions.BusinessException;
import com.senai.pousadabackend.integration.UploadQuarto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImagemServiceImpl implements ImagemService {

    private final QuartoService quartoService;
    private final UploadQuarto uploadQuarto;
    private final ImagemRepository repository;

    public ImagemServiceImpl(
            @Qualifier("quartoServiceImpl")
            QuartoService quartoService,
            UploadQuarto uploadQuarto,
            ImagemRepository repository) {
        this.quartoService = quartoService;
        this.uploadQuarto = uploadQuarto;
        this.repository = repository;
    }

    private static final long TAMANHO_MAXIMO_ARQUIVO = 5 * 1024 * 1024;

    @Override
    public void uploadImagem(List<MultipartFile> imagens, Long idQuarto) {

        imagens.forEach(imagem -> {
            if (imagem.getSize() > TAMANHO_MAXIMO_ARQUIVO) {
                throw new BusinessException("O arquivo excede o limite de 5MB.");
            }
        });
    }

    @Override
    public void salvar(List<ImagemQuarto> urlsFormatadas, Long idQuarto) {
        Quarto quarto = quartoService.buscarPorId(idQuarto);

        List<ImagemQuarto> imagensRemovidas = quarto.getUrlImagens().stream()
                .filter(imagem -> urlsFormatadas.stream()
                        .noneMatch(nova -> nova.getId() != null && nova.getId().equals(imagem.getId())))
                .toList();

        for (ImagemQuarto removida : imagensRemovidas) {
            if (removida.getFileId() != null) {
                uploadQuarto.deletarImagem(removida.getFileId());
                repository.deleteById(removida.getId());
            }
        }

        quarto.getUrlImagens().removeAll(imagensRemovidas);

        for (ImagemQuarto nova : urlsFormatadas) {
            if (nova.getId() == null) {
                nova.setQuarto(quarto);
                quarto.getUrlImagens().add(nova);
            }
        }

        quartoService.salvar(quarto);
    }
}
