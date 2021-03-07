package persistence;

import model.*;
import model.Task;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class JsonReaderTest extends JsonTest {
    // code copied and modified from JsonSerializationDemo

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Schedule s = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptySchedule() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySchedule.json");
        try {
            Schedule s = reader.read();
            assertEquals("My schedule", s.getName());
            assertEquals(0, s.listTasksTodo().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralSchedule() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralSchedule.json");
        try {
            Schedule s = reader.read();
            assertEquals("My schedule", s.getName());
            List<Task> todos = s.getTasks();
            assertEquals(2, todos.size());
            checkTask("run", 7, todos.get(0));
            checkTask("breakfast", 9, todos.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
