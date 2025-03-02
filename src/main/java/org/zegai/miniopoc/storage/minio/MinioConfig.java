package org.zegai.miniopoc.storage.minio;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MinioConfig {

    private final MinioConfigProperties minioConfigProperties;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
            .endpoint(minioConfigProperties.getUrl())
            .credentials(minioConfigProperties.getAccessKey(), minioConfigProperties.getSecretKey())
            .build();
    }
}
