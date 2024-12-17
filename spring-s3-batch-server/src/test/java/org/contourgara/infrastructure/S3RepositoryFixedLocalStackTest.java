package org.contourgara.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;
import software.amazon.awssdk.services.s3.model.S3Exception;

@SpringBootTest
@ActiveProfiles("localstack")
@Testcontainers
class S3RepositoryFixedLocalStackTest {
    @Container
    static GenericContainer<?> localStackContainer = new FixedHostPortGenericContainer<>("localstack/localstack:s3-latest")
            .withCopyFileToContainer(MountableFile.forClasspathResource("init.py"), "/etc/localstack/init/ready.d/init.py")
            .withExposedPorts(4566)
            .withFixedExposedPort(14566, 4566);

    @DynamicPropertySource
    static void awsProperties(DynamicPropertyRegistry registry) {
        registry.add("aws.access-key-id", () -> "dummy");
        registry.add("aws.secret-key", () -> "dummy");
        registry.add("aws.region", () -> "ap-northeast-1");
        registry.add("aws.s3.endpoint", () -> "http://localhost:14566");
    }

    @Autowired
    S3Repository sut;

    @BeforeEach
    void setUp() {
        localStackContainer.stop();
        while (!localStackContainer.isHealthy()) {
            localStackContainer.start();
        }
    }

    @Test
    void S3へのアップロードに成功した場合nullが返る() {
        // execute
        var actual =  sut.save("test-bucket", "test-key");

        // assert
        var expected = Either.right(null);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void バケットが存在しない場合例外が返る() {
        // execute
        var actual =  sut.save("incorrect-bucket", "test-key");

        // assert
        assertThat(actual.getLeft()).isInstanceOf(S3Exception.class);
    }
}
