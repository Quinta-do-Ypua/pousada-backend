package com.senai.pousadabackend.infraestructure.imagem.configuracao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "deleteConfiguracao", url = "${imagekit.urlDelete}", configuration = UploadConfiguracaoConfig.class)
public interface DeleteConfiguracao {

    @DeleteMapping("/{fileId}")
    void deletarImagem(@PathVariable("fileId") String fileId);

}
