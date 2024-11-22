package org.contourgara;

import static org.assertj.core.api.Assertions.assertThat;

import io.vavr.control.Either;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Disabled
@SpringBootTest
class S3RepositoryS3Test {
    private static final String BUCKET_NAME = ""; // type your bucket name

    @DynamicPropertySource
    static void awsProperties(DynamicPropertyRegistry registry) { // type your credentials
        registry.add("aws.access-key-id", () -> "");
        registry.add("aws.secret-key", () -> "");
        registry.add("aws.region", () -> "");
    }

    @Autowired
    S3Repository sut;

    @Test
    void S3へのアップロードに成功した場合nullが返る() {
        // execute
        var actual =  sut.save(BUCKET_NAME, "test-key");

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
