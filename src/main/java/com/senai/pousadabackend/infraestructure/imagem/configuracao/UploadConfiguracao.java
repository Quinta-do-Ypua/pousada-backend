package com.senai.pousadabackend.infraestructure.imagem.configuracao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "UploadConfiguracao", url = "${imagekit.urlUpload}", configuration = UploadConfiguracaoConfig.class)
public interface UploadConfiguracao {

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String uploadImagem(
            @RequestPart("file") MultipartFile file,
            @RequestPart("fileName") String fileName
    );

}
