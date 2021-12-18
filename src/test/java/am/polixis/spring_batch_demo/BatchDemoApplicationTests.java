package am.polixis.spring_batch_demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BatchDemoApplicationTests {

    @Test
    void test() {
        String env = System.getenv("env");
        assertEquals("DEV", env);
    }

    @Test
    void test2() {
        String env = System.getenv("env");
        assertNotEquals("DEV", env);
    }

}
