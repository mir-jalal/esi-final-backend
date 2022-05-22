package com.esifinal.backend;

import com.esifinal.backend.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class EsiFinalBackendApplicationTests {

    @Test
    void contextLoads() {
        User user = new User();
        assertNull(user.getName());
    }

}
