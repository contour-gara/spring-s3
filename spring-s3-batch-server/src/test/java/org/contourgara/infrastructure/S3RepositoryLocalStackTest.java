package org.contourgara.infrastructure;

import static org.assertj.core.api.Assertions.*;

import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
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
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;

@SpringBootTest
@ActiveProfiles("localstack")
@Testcontainers
class S3RepositoryLocalStackTest {
    @Container
    static LocalStackContainer localStackContainer = new LocalStackContainer(DockerImageName.parse("localstack/localstack:s3-latest"));

    @DynamicPropertySource
    static void awsProperties(DynamicPropertyRegistry registry) {
        registry.add("aws.access-key-id", localStackContainer::getAccessKey);
        registry.add("aws.secret-key", localStackContainer::getSecretKey);
        registry.add("aws.region", localStackContainer::getRegion);
        registry.add("aws.s3.endpoint", localStackContainer::getEndpoint);
    }

    @Autowired
    S3Client s3Client;

    @Autowired
    S3Repository sut;

    @BeforeEach
    void setUp() {
        for (String bucket : s3Client.listBuckets().buckets().stream().map(Bucket::name).toList()) {
            for (String key : s3Client.listObjectsV2(ListObjectsV2Request.builder().bucket(bucket).build()).contents().stream().map(S3Object::key).toList()) {
                s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucket).key(key).build());
            }
            s3Client.deleteBucket(DeleteBucketRequest.builder().bucket(bucket).build());
        }
        s3Client.createBucket(CreateBucketRequest.builder().bucket("test-bucket").build());
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
