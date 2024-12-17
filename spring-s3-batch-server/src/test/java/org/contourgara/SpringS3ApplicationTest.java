package org.contourgara;

import static org.assertj.core.api.Assertions.*;

import org.contourgara.infrastructure.S3Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringS3ApplicationTest {
    @Autowired
    S3Repository s3Repository;

    @Test
    void contextLoads() {
        assertThat(s3Repository).isNotNull();
    }
}
