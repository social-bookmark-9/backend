package com.sparta.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.config.location=" + "classpath:/application.yml" )
class BackendApplicationTests {

    @Test
    void contextLoads() {
    }

}
