package am.polixis.spring_batch_demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BatchDemoApplicationTests {

    @Test
    void test() {
        String env = System.getenv("env");
        assertNull(env);
    }

}
