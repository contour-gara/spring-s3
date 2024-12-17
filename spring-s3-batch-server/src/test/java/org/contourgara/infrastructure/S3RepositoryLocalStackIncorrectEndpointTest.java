package org.contourgara.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.core.exception.SdkClientException;

@SpringBootTest
@ActiveProfiles("localstack")
@Testcontainers
class S3RepositoryLocalStackIncorrectEndpointTest {
    @Container
    static LocalStackContainer localStackContainer = new LocalStackContainer(DockerImageName.parse("localstack/localstack:s3-latest"));

    @DynamicPropertySource
    static void awsProperties(DynamicPropertyRegistry registry) {
        registry.add("aws.access-key-id", localStackContainer::getAccessKey);
        registry.add("aws.secret-key", localStackContainer::getSecretKey);
        registry.add("aws.region", localStackContainer::getRegion);
        registry.add("aws.s3.endpoint", () -> "http://incorrect");
    }

    @Autowired
    S3Repository sut;

    @Test
    void S3への接続に失敗した場合例外が返る() {
        // execute
        var actual =  sut.save("test-bucket", "test-key");

        // assert
        assertThat(actual.getLeft()).isInstanceOf(SdkClientException.class);
    }
}
