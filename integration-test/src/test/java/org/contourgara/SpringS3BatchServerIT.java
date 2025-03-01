package org.contourgara;

import io.brachu.johann.DockerCompose;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringS3BatchServerIT {
    @Test
    void test() throws Exception {
        DockerCompose compose = DockerCompose.builder().absolute("../compose.yml").build();
//        compose.up();
        compose.stop("localstack");
        Thread.sleep(10000);
//        compose.down();
        compose.start("localstack");
        Thread.sleep(10000);
    }
}
