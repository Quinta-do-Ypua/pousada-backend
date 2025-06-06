package com.senai.pousadabackend.domain.Imagem.service;

import com.senai.pousadabackend.domain.Imagem.ImagemQuarto;
import com.senai.pousadabackend.domain.Imagem.ImagemRepository;
import com.senai.pousadabackend.domain.quarto.Quarto;
import com.senai.pousadabackend.domain.quarto.service.QuartoService;
import com.senai.pousadabackend.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImagemServiceImpl implements ImagemService {

    private final QuartoService quartoService;

    private final ImagemRepository repository;

    public ImagemServiceImpl(
            @Qualifier("quartoServiceImpl")
            QuartoService quartoService,
            ImagemRepository repository) {
        this.quartoService = quartoService;
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

        for (ImagemQuarto nova : urlsFormatadas) {
            this.validar(nova);
            nova.setQuarto(quarto);
            repository.save(nova);
        }
    }

    @Override
    public List<ImagemQuarto> listarPor(Long idQuarto) {
        return repository.listarPor(idQuarto);
    }

    @Override
    public void deletar(ImagemQuarto imagemQuarto) {
        this.validar((imagemQuarto));
        repository.deleteById(imagemQuarto.getId());
    }

    private void validar(ImagemQuarto imagemQuarto) {

        if (imagemQuarto.getFileId() == null) {
            throw new BusinessException("A imagem precisa conter o id do arquivo");
        }

        if (imagemQuarto.getUrl() == null) {
            throw new BusinessException("A imagem precisa conter a url do arquivo");
        }
    }
}
