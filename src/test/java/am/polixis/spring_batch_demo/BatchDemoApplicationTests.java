package am.polixis.spring_batch_demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BatchDemoApplicationTests {

    @Test
    void contextLoads() {
        String env = System.getenv("env");
        assertEquals("PROD", env);
    }

}
