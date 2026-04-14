package com.senai.pousadabackend.domain.Imagem.quarto.service;

import com.senai.pousadabackend.domain.Imagem.quarto.ImagemQuarto;
import com.senai.pousadabackend.domain.Imagem.quarto.dto.ResultadoUploadDTO;
import com.senai.pousadabackend.infraestructure.imagem.quarto.UploadQuarto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImagemQuartoServiceProxy implements ImagemQuartoService {

    private final ImagemQuartoService service;
    private final UploadQuarto uploadQuarto;

    public ImagemQuartoServiceProxy(
            @Qualifier("imagemQuartoServiceImpl")
            ImagemQuartoService service,
            UploadQuarto uploadQuarto
    ) {
        this.service = service;
        this.uploadQuarto = uploadQuarto;
    }

    @Override
    public void uploadImagem(List<MultipartFile> imagens, Long idQuarto) {
        service.uploadImagem(imagens, idQuarto);

        List<ImagemQuarto> imagensParaSalvar = new ArrayList<>();

        for (MultipartFile imagem : imagens) {
            ResultadoUploadDTO resultado = uploadQuarto.uploadImagem(imagem);

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
