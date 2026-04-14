package com.senai.pousadabackend.infraestructure.imagem.quarto;

import com.senai.pousadabackend.domain.Imagem.quarto.dto.ResultadoUploadDTO;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
public class UploadQuarto {

    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Value("${minio.url}")
    private String minioUrl;

    public UploadQuarto(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public ResultadoUploadDTO uploadImagem(MultipartFile imagem) {
        try {
            garantirBucketExiste();

            String objectName = UUID.randomUUID() + "-" + imagem.getOriginalFilename();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(imagem.getInputStream(), imagem.getSize(), -1)
                            .contentType(imagem.getContentType())
                            .build()
            );

            String url = minioUrl + "/" + bucketName + "/" + objectName;

            return new ResultadoUploadDTO(objectName, url);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer upload da imagem no MinIO: " + e.getMessage(), e);
        }
    }

    private void garantirBucketExiste() throws Exception {
        boolean existe = minioClient.bucketExists(
                BucketExistsArgs.builder()
                        .bucket(bucketName)
                        .build()
        );

        if (!existe) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(bucketName)
                            .build()
            );
        }
    }
}
