package model;


// NOTE: ADD COMMENTS BASED ON GENERAL REQUIREMENTS LATER
public class Task {
    private String name;
    private int scheduledTime;
    private String status;

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


}
