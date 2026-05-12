package com.senai.pousadabackend.domain.imagem.quarto.service;

import com.senai.pousadabackend.domain.imagem.quarto.ImagemQuarto;
import com.senai.pousadabackend.domain.imagem.quarto.ImagemQuartoRepository;
import com.senai.pousadabackend.domain.imagem.quarto.dto.ResultadoUploadDTO;
import com.senai.pousadabackend.domain.quarto.Quarto;
import com.senai.pousadabackend.domain.quarto.QuartoService;
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
public class ImagemQuartoService {

    private static final long TAMANHO_MAXIMO_ARQUIVO = 5 * 1024 * 1024;

    private final QuartoService quartoService;
    private final MinioDeleteClient minioDeleteClient;
    private final MinioUploadClient minioUploadClient;
    private final ImagemQuartoRepository repository;

    public ImagemQuartoService(QuartoService quartoService,
                                MinioDeleteClient minioDeleteClient,
                                MinioUploadClient minioUploadClient,
                                ImagemQuartoRepository repository) {
        this.quartoService = quartoService;
        this.minioDeleteClient = minioDeleteClient;
        this.minioUploadClient = minioUploadClient;
        this.repository = repository;
    }

    public void uploadImagem(
            @NotEmpty(message = "Deve haver no mínimo uma imagem vinculada") List<MultipartFile> imagens,
            @NotNull(message = "O id do quarto é obrigatório") Long idQuarto) {

        imagens.forEach(imagem -> {
            if (imagem.getSize() > TAMANHO_MAXIMO_ARQUIVO) {
                throw new BusinessException("O arquivo excede o limite de 5MB.");
            }
        });

        Quarto quarto = quartoService.buscarPorId(idQuarto);

        for (MultipartFile imagem : imagens) {
            ResultadoUploadDTO resultado = minioUploadClient.uploadImagem(imagem);
            ImagemQuarto nova = ImagemQuarto.builder()
                    .fileId(resultado.getObjectName())
                    .url(resultado.getUrl())
                    .quarto(quarto)
                    .build();
            validar(nova);
            repository.save(nova);
        }
    }

    public List<ImagemQuarto> listarPor(
            @NotNull(message = "O id do quarto é obrigatório") Long idQuarto) {
        return repository.listarPor(idQuarto);
    }

    public void deletar(
            @NotNull(message = "A imagem é obrigatória") ImagemQuarto imagemQuarto) {
        validar(imagemQuarto);
        minioDeleteClient.deletarImagem(imagemQuarto.getFileId());
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
