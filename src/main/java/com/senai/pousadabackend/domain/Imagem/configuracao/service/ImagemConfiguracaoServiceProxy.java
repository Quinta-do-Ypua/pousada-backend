package com.senai.pousadabackend.domain.Imagem.configuracao.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.pousadabackend.domain.Imagem.configuracao.ImagemConfiguracao;
import com.senai.pousadabackend.infraestructure.imagem.configuracao.UploadConfiguracao;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImagemConfiguracaoServiceProxy implements ImagemConfiguracaoService {

    private final ImagemConfiguracaoService service;
    private final UploadConfiguracao uploadConfiguracao;

    public ImagemConfiguracaoServiceProxy(ImagemConfiguracaoService service, UploadConfiguracao uploadConfiguracao) {
        this.service = service;
        this.uploadConfiguracao = uploadConfiguracao;
    }

    @Override
    public void uploadImagem(List<MultipartFile> imagens, Long idConfiguracao) {
        service.uploadImagem(imagens, idConfiguracao);
        List<ImagemConfiguracao> urls = new ArrayList<>();
        for (MultipartFile imagem : imagens) {
            String nomeImagem = imagem.getOriginalFilename();
            String response = uploadConfiguracao.uploadImagem(imagem, nomeImagem);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode;
            try {
                jsonNode = mapper.readTree(response);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            urls.add(ImagemConfiguracao
                    .builder()
                    .url(jsonNode.get("url").asText())
                    .fileId(jsonNode.get("fileId").asText())
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
