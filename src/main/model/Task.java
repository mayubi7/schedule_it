package model;


import org.json.JSONObject;
import persistence.Writable;

// Represents a task with its name, scheduled time and
// the status of the task (either "unfinished" or "complete")
public class Task implements Writable {
    private String name;
    private int scheduledTime;
    private String status;

    //REQUIRES: String is non-empty and Integer is between 0-23
    //EFFECTS: Assigns string to name and integer to the scheduled time,
    // the status is set to "unfinished"
    public Task(String name, Integer time) {
        this.name = name;
        this.scheduledTime = time;
        this.status = "unfinished";
    }

    //getters
    public String getName() {
        return name;
    }

    public int getTime() {
        return scheduledTime;
    }

    public String getStatus() {
        return status;
    }

    //setters
    public void setTime(int time) {
        scheduledTime = time;
    }

    public void setStatus(String progress) {
        status = progress;
    }


    @Override
    public JSONObject toJson() {
        // code copied and modified from JsonSerializationDemo
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("time", scheduledTime);
        json.put("status", status);
        return json;
    }
}
