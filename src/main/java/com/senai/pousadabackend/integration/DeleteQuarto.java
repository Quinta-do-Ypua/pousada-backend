package com.senai.pousadabackend.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "deleteQuarto", url = "${imagekit.urlDelete}", configuration = UploadQuartoConfig.class)
public interface DeleteQuarto {

    @DeleteMapping("/{fileId}")
    void deletarImagem(@PathVariable("fileId") String fileId);
}
