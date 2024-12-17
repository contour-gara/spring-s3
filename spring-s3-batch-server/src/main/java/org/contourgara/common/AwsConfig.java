package org.contourgara.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aws")
@Getter
@Setter
public class AwsConfig {
    private String accessKeyId;
    private String secretKey;
    private String region;
    private final S3 s3 = new S3();

    @Getter
    @Setter
    static class S3 {
        private String endpoint;
    }
}
