package com.senai.pousadabackend.domain.Imagem.configuracao.service;

import com.senai.pousadabackend.domain.Imagem.configuracao.ImagemConfiguracao;
import com.senai.pousadabackend.domain.Imagem.quarto.dto.ResultadoUploadDTO;
import com.senai.pousadabackend.infraestructure.imagem.quarto.UploadQuartoClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImagemConfiguracaoServiceProxy implements ImagemConfiguracaoService {

    private final ImagemConfiguracaoService service;
    private final UploadQuartoClient uploadQuartoClient;

    public ImagemConfiguracaoServiceProxy(
            @Qualifier("imagemConfiguracaoServiceImpl")
            ImagemConfiguracaoService service, UploadQuartoClient uploadQuartoClient) {
        this.service = service;
        this.uploadQuartoClient = uploadQuartoClient;
    }

    @Override
    public void uploadImagem(List<MultipartFile> imagens, Long idConfiguracao) {
        service.uploadImagem(imagens, idConfiguracao);

        List<ImagemConfiguracao> urls = new ArrayList<>();

        for (MultipartFile imagem : imagens) {
            ResultadoUploadDTO resultado = uploadQuartoClient.uploadImagem(imagem);

            urls.add(ImagemConfiguracao
                    .builder()
                    .url(resultado.getUrl())
                    .fileId(resultado.getObjectName())
                    .build()
            );
        }
        this.salvar(urls, idConfiguracao);
    }

    @Override
    public void salvar(List<ImagemConfiguracao> urlsFormatadas, Long idConfiguracao) {
        service.salvar(urlsFormatadas, idConfiguracao);
    }

    @Override
    public List<ImagemConfiguracao> listarPor(Long idConfiguracao) {
        return service.listarPor(idConfiguracao);
    }

    @Override
    public void deletar(ImagemConfiguracao imagemConfiguracao) {
        service.deletar(imagemConfiguracao);
    }

}
