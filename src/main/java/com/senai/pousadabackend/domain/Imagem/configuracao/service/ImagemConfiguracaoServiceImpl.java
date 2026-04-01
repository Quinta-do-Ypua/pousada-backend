package com.senai.pousadabackend.domain.Imagem.configuracao.service;

import com.senai.pousadabackend.domain.Imagem.configuracao.ImagemConfiguracao;
import com.senai.pousadabackend.domain.Imagem.quarto.ImagemQuartoRepository;
import com.senai.pousadabackend.domain.temaSistema.TemaSistemaService;
import com.senai.pousadabackend.infraestructure.imagem.configuracao.DeleteConfiguracao;
import com.senai.pousadabackend.infraestructure.imagem.configuracao.UploadConfiguracao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImagemConfiguracaoServiceImpl implements ImagemConfiguracaoService  {

    private final TemaSistemaService temaSistemaService;
    private final UploadConfiguracao uploadConfiguracao;
    private final DeleteConfiguracao deleteConfiguracao;
    private final ImagemQuartoRepository repository;

    public ImagemConfiguracaoServiceImpl(
            @Qualifier("temaSistemaServiceImpl")
            TemaSistemaService temaSistemaService,
            UploadConfiguracao uploadConfiguracao,
            DeleteConfiguracao deleteConfiguracao,
            ImagemQuartoRepository repository) {
        this.temaSistemaService = temaSistemaService;
        this.uploadConfiguracao = uploadConfiguracao;
        this.repository = repository;
        this.deleteConfiguracao = deleteConfiguracao;
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
