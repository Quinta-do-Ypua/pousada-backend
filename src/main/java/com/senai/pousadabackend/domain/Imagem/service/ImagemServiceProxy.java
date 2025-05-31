package com.senai.pousadabackend.domain.Imagem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.pousadabackend.domain.Imagem.ImagemQuarto;
import com.senai.pousadabackend.integration.UploadQuarto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImagemServiceProxy implements ImagemService {

    private final ImagemService service;
    private final UploadQuarto uploadQuarto;

    public ImagemServiceProxy(
            @Qualifier("imagemServiceImpl")
            ImagemService service,
            UploadQuarto uploadQuarto
    ) {
        this.service = service;
        this.uploadQuarto = uploadQuarto;
    }

    @Override
    public void uploadImagem(List<MultipartFile> imagens, Long idQuarto) {
        service.uploadImagem(imagens, idQuarto);

        List<ImagemQuarto> urls = new ArrayList<>();

        for (MultipartFile imagem : imagens) {
            String nomeImagem = imagem.getOriginalFilename();
            String response = uploadQuarto.uploadImagem(imagem, nomeImagem);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode;
            try {
                jsonNode = mapper.readTree(response);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            urls.add(ImagemQuarto
                    .builder()
                    .url(jsonNode.get("url").asText())
                    .fileId(jsonNode.get("fileId").asText())
                    .build()
            );
        }

        this.salvar(urls, idQuarto);
    }

    @Override
    public void salvar(List<ImagemQuarto> urlsFormatadas, Long idQuarto) {
        service.salvar(urlsFormatadas, idQuarto);
    }

    @Override
    public List<ImagemQuarto> listarPor(Long idQuarto) {
        return service.listarPor(idQuarto);
    }

    @Override
    public void deletar(ImagemQuarto imagemQuarto) {
        service.deletar(imagemQuarto);
    }
}
