package com.senai.pousadabackend.domain.Imagem.configuracao.service;

import com.senai.pousadabackend.domain.Imagem.configuracao.ImagemConfiguracao;
import com.senai.pousadabackend.domain.Imagem.quarto.ImagemQuartoRepository;
import com.senai.pousadabackend.domain.configuracao.service.ConfiguracaoService;
import com.senai.pousadabackend.integration.imagem.configuracao.DeleteConfiguracao;
import com.senai.pousadabackend.integration.imagem.configuracao.UploadConfiguracao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImagemConfiguracaoServiceImpl implements ImagemConfiguracaoService  {

    private final ConfiguracaoService configuracaoService;
    private final UploadConfiguracao uploadConfiguracao;
    private final DeleteConfiguracao deleteConfiguracao;
    private final ImagemQuartoRepository repository;

    public ImagemConfiguracaoServiceImpl(
            @Qualifier("configuracaoServiceImpl")
            ConfiguracaoService configuracaoService,
            UploadConfiguracao uploadConfiguracao,
            DeleteConfiguracao deleteConfiguracao,
            ImagemQuartoRepository repository) {
        this.configuracaoService = configuracaoService;
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
