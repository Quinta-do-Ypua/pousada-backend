package com.senai.pousadabackend.domain.Imagem.quarto.service;

import com.senai.pousadabackend.domain.Imagem.quarto.ImagemQuarto;
import com.senai.pousadabackend.domain.Imagem.quarto.dto.ResultadoUploadDTO;
import com.senai.pousadabackend.infraestructure.imagem.quarto.UploadQuartoClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImagemQuartoServiceProxy implements ImagemQuartoService {

    private final ImagemQuartoService service;
    private final UploadQuartoClient uploadQuartoClient;

    public ImagemQuartoServiceProxy(
            @Qualifier("imagemQuartoServiceImpl")
            ImagemQuartoService service,
            UploadQuartoClient uploadQuartoClient
    ) {
        this.service = service;
        this.uploadQuartoClient = uploadQuartoClient;
    }

    @Override
    public void uploadImagem(List<MultipartFile> imagens, Long idQuarto) {
        service.uploadImagem(imagens, idQuarto);

        List<ImagemQuarto> imagensParaSalvar = new ArrayList<>();

        for (MultipartFile imagem : imagens) {
            ResultadoUploadDTO resultado = uploadQuartoClient.uploadImagem(imagem);

            imagensParaSalvar.add(
                    ImagemQuarto.builder()
                            .fileId(resultado.getObjectName())
                            .url(resultado.getUrl())
                            .build()
            );
        }

        this.salvar(imagensParaSalvar, idQuarto);
    }

    @Override
    public void salvar(List<ImagemQuarto> imagensParaSalvar, Long idQuarto) {
        service.salvar(imagensParaSalvar, idQuarto);
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
