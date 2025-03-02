package org.zegai.miniopoc.storage.minio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("minio")
public class MinioConfigProperties {
    private String url;
    private String accessKey;
    private String secretKey;
    private String bucket;
    private Integer publicPreviewExpiresInHours = 1;
    private Integer privatePreviewExpiresInHours = 1;
}
