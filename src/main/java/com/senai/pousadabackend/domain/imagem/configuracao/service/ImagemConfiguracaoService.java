package com.senai.pousadabackend.domain.imagem.configuracao.service;

import com.senai.pousadabackend.domain.imagem.configuracao.ImagemConfiguracao;
import com.senai.pousadabackend.domain.imagem.configuracao.ImagemConfiguracaoRepository;
import com.senai.pousadabackend.domain.imagem.quarto.dto.ResultadoUploadDTO;
import com.senai.pousadabackend.domain.temaSistema.TemaSistema;
import com.senai.pousadabackend.domain.temaSistema.TemaSistemaService;
import com.senai.pousadabackend.exceptions.BusinessException;
import com.senai.pousadabackend.infraestructure.imagem.MinioDeleteClient;
import com.senai.pousadabackend.infraestructure.imagem.MinioUploadClient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Validated
public class ImagemConfiguracaoService {

    private static final long TAMANHO_MAXIMO_ARQUIVO = 5 * 1024 * 1024;

    private final TemaSistemaService temaSistemaService;
    private final MinioUploadClient minioUploadClient;
    private final MinioDeleteClient minioDeleteClient;
    private final ImagemConfiguracaoRepository repository;

    public ImagemConfiguracaoService(TemaSistemaService temaSistemaService,
                                      MinioUploadClient minioUploadClient,
                                      MinioDeleteClient minioDeleteClient,
                                      ImagemConfiguracaoRepository repository) {
        this.temaSistemaService = temaSistemaService;
        this.minioUploadClient = minioUploadClient;
        this.minioDeleteClient = minioDeleteClient;
        this.repository = repository;
    }

    public void uploadImagem(
            @NotEmpty(message = "Deve haver no mínimo uma imagem vinculada") List<MultipartFile> imagens,
            @NotNull(message = "O id da configuração é obrigatório") Long idConfiguracao) {

        imagens.forEach(imagem -> {
            if (imagem.getSize() > TAMANHO_MAXIMO_ARQUIVO) {
                throw new BusinessException("O arquivo excede o limite de 5MB.");
            }
        });

        TemaSistema tema = temaSistemaService.buscarPorId(idConfiguracao);

        for (MultipartFile imagem : imagens) {
            ResultadoUploadDTO resultado = minioUploadClient.uploadImagem(imagem);
            repository.save(ImagemConfiguracao.builder()
                    .fileId(resultado.getObjectName())
                    .url(resultado.getUrl())
                    .temaSistema(tema)
                    .build());
        }
    }

    public List<ImagemConfiguracao> listarPor(
            @NotNull(message = "O id da configuração é obrigatório") Long idConfiguracao) {
        return repository.listarPor(idConfiguracao);
    }

    public void deletar(
            @NotNull(message = "A imagem é obrigatória") ImagemConfiguracao imagemConfiguracao) {
        if (imagemConfiguracao.getFileId() == null) {
            throw new BusinessException("A imagem precisa conter o id do arquivo");
        }
        minioDeleteClient.deletarImagem(imagemConfiguracao.getFileId());
        repository.deleteById(imagemConfiguracao.getId());
    }

}
