package am.polixis.spring_batch_demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BatchDemoApplicationTests {

    @Test
    void test() {
        String env = System.getenv("env");
        assertNotEquals("PROD", env);
    }

}
