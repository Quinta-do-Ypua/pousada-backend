package com.senai.pousadabackend.domain.Imagem.configuracao.service;

import com.senai.pousadabackend.domain.Imagem.configuracao.ImagemConfiguracao;
import com.senai.pousadabackend.domain.Imagem.quarto.ImagemQuartoRepository;
import com.senai.pousadabackend.domain.temaSistema.TemaSistemaService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImagemConfiguracaoServiceImpl implements ImagemConfiguracaoService  {

    private final TemaSistemaService temaSistemaService;
    private final ImagemQuartoRepository repository;

    public ImagemConfiguracaoServiceImpl(
            @Qualifier("temaSistemaServiceImpl")
            TemaSistemaService temaSistemaService,
            ImagemQuartoRepository repository) {
        this.temaSistemaService = temaSistemaService;
        this.repository = repository;
    }

    @Override
    public void uploadImagem(List<MultipartFile> imagens, Long idConfiguracao) {

    }

    @Override
    public void salvar(List<ImagemConfiguracao> urlsFormatadas, Long idConfiguracao) {

    }

    @Override
    public List<ImagemConfiguracao> listarPor(Long idConfiguracao) {
        return List.of();
    }

    @Override
    public void deletar(ImagemConfiguracao imagemConfiguracao) {

    }

}
