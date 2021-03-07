package persistence;

import model.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JsonTest {
    // code copied and modified from JsonSerializationDemo

    protected void checkTask(String name, Integer time, Task task) {
        assertEquals(name, task.getName());
        assertEquals(time, task.getTime());
    }
}

