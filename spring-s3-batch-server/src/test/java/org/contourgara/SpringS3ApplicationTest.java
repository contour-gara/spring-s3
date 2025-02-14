package org.contourgara;

import static org.assertj.core.api.Assertions.*;

import org.contourgara.infrastructure.S3Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringS3Application.class, initializers = ConfigDataApplicationContextInitializer.class)
class SpringS3ApplicationTest {
    @Autowired
    S3Repository s3Repository;

    @Test
    void contextLoads() {
        assertThat(s3Repository).isNotNull();
    }
}
