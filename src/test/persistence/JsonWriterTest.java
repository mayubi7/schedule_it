package persistence;

import model.*;
import model.Task;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;


public class JsonWriterTest extends JsonTest {
    // code copied and modified from JsonSerializationDemo

    @Test
    void testWriterInvalidFile() {
        try {
            Schedule s = new Schedule("My schedule");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptySchedule() {
        try {
            Schedule s = new Schedule("My schedule");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySchedule.json");
            writer.open();
            writer.write(s);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySchedule.json");
            s = reader.read();
            assertEquals("My schedule", s.getName());
            assertEquals(0, s.listTasksTodo().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralSchedule() {
        try {
            Schedule s = new Schedule("My schedule");
            ArrayList<Task> todos = new ArrayList<>();
            Task todo1 = new Task("meeting", 10);
            Task todo2 = new Task("homework", 15);
            todos.add(todo1);
            todos.add(todo2);
            s.addNewTask(todos);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralSchedule.json");
            writer.open();
            writer.write(s);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralSchedule.json");
            s = reader.read();
            assertEquals("My schedule", s.getName());
            List<Task> tasks = s.getTasks();
            assertEquals(2, tasks.size());
            checkTask("meeting", 10, tasks.get(0));
            checkTask("homework", 15, tasks.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
