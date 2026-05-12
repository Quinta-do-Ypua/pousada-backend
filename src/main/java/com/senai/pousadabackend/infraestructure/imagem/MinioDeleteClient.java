package com.senai.pousadabackend.infraestructure.imagem;

import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MinioDeleteClient {

    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    public MinioDeleteClient(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public void deletarImagem(String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar imagem no MinIO: " + e.getMessage(), e);
        }
    }
}
