package org.contourgara;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aws")
@Data
public class AwsConfig {
    private String accessKeyId;
    private String secretKey;
    private String region;
    private String endpoint;
}
