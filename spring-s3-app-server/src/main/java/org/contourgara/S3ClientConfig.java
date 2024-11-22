package org.contourgara;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class S3ClientConfig {
    private final AwsConfig awsConfig;

    @Bean
    @Profile({"default"})
    public S3Client createS3Client() {
        return S3Client.builder()
                .credentialsProvider(() -> AwsBasicCredentials.create(awsConfig.getAccessKeyId(), awsConfig.getSecretKey()))
                .region(Region.of(awsConfig.getRegion()))
                .build();
    }

    @Bean
    @Scope("prototype")
    @Profile({"localstack"})
    public S3Client createLocalstackClient() {
        System.out.println("localstack!!!");
        return S3Client.builder()
                .credentialsProvider(() -> AwsBasicCredentials.create(awsConfig.getAccessKeyId(), awsConfig.getSecretKey()))
                .region(Region.of(awsConfig.getRegion()))
                .forcePathStyle(true)
                .endpointOverride(URI.create(awsConfig.getEndpoint()))
                .build();
    }
}
