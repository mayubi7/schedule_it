package persistence;

import exceptions.EmptyTaskListException;
import model.*;
import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Represents a reader that reads JSON representation of schedule from file
public class JsonReader {
    // code copied and modified from JsonSerializationDemo

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads schedule from file and returns it
    // throws IOException if an error occurs while reading data
    public Schedule read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSchedule(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses schedule from JSON object and returns it
    private Schedule parseSchedule(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Schedule s = new Schedule(name);
        addTasks(s, jsonObject);
        return s;
    }

    // MODIFIES: s
    // EFFECTS: parses tasks from JSON object and adds them to schedule
    private void addTasks(Schedule s, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tasks");
        ArrayList<Task> tasks = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            String name = nextTask.getString("name");
            Integer time = nextTask.getInt("time");
            String status = nextTask.getString("status");
            Task task = new Task(name,time);
            task.setStatus(status);
            tasks.add(task);
        }
        try {
            s.addNewTask(tasks);
        } catch (EmptyTaskListException e) {
            System.out.println("Task list is empty!");
        }
    }
}
