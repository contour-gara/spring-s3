package org.contourgara;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringS3Application {
    public static void main(String[] args) {
        SpringApplication.exit(SpringApplication.run(SpringS3Application.class, args));
    }
}
