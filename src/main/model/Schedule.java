package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

/**
 * A representation of a 24 hours schedule where tasks can be assigned to a 1 hour timeslot.
 *
 * Tasks can be added using the addNewTask method, which takes one argument.
 * It takes an ArrayList of Tasks that can then be assigned to the appropriate times given that it is available.
 * If a timeslot is already occupied, the new task will not be assigned to that time.
 *
 * Tasks can be rescheduled, marked as complete and deleted.
 * The names of the tasks still incomplete can be obtained by the listTasksToDo method.
 */

public class Schedule implements Writable {
    private ArrayList<Task> mySchedule;
    private String name;

    //EFFECTS: Creates a schedule that has 24 hours and no tasks
    public Schedule(String name) {
        this.name = name;
        //based on HairSalon starter from the pre-lecture videos
        mySchedule = new ArrayList<>();
        for (int i = 0; i <= 23; i++) {
            mySchedule.add(i, null);
        }
    }

    //REQUIRES: ArrayList<Task> is non-empty
    //MODIFIES: this
    //EFFECTS: assigns each task to its corresponding timeslot if timeslot is available
    public void addNewTask(ArrayList<Task> task) {
        for (int i = 0; i < task.size(); i++) {
            if (mySchedule.get(task.get(i).getTime()) == null) {
                mySchedule.set(task.get(i).getTime(), task.get(i));
                task.get(i).setTime(task.get(i).getTime());
            }
        }
    }

    //REQUIRES: int must be within 0-23
    //MODIFIES: this, task
    //EFFECTS: assign task to a new time and remove task from previous time
    public void reschedule(Task task, int newTime) {
        int oldTime = task.getTime();
        mySchedule.set(oldTime, null);
        mySchedule.set(newTime, task);
        task.setTime(newTime);
    }

    //MODIFIES: this, task
    //EFFECTS: changes status of task to complete on the schedule
    public void markAsComplete(Task task) {
        task.setStatus("complete");
        mySchedule.set(task.getTime(), task);

    }

    //REQUIRES: hour must be from 0-23
    //MODIFIES: this
    //EFFECTS: clears the task at the time from schedule
    public void deleteTask(int hour) {
        mySchedule.set(hour, null);
    }


    //EFFECTS: lists tasks for the day that are not yet completed
    public List<String> listTasksTodo() {
        List<String> todos = new ArrayList<>();
        for (int i = 0; i <= 23; i++) {
            Task todo = mySchedule.get(i);
            if (todo != null && !todo.getStatus().equals("complete")) {
                todos.add(todo.getName());
            }
        }
        return todos;
    }

    // REQUIRES: name must be a task already in schedule, no duplicate names in schedule
    // EFFECT: find the task with the given name in schedule
    public Task findByName(String name) {
        for (int i = 0; i <= 23; i++) {
            if (mySchedule.get(i) != null && mySchedule.get(i).getName().equals(name)) {
                return mySchedule.get(i);
            }
        }
        return null;
    }

    @Override
    public JSONObject toJson() {
        // code copied and modified from JsonSerializationDemo
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("tasks", tasksToJson());
        return json;
    }


    private JSONArray tasksToJson() {
        // code copied and modified from JsonSerializationDemo
        JSONArray jsonArray = new JSONArray();
        for (Task t: mySchedule) {
            if (t != null) {
                jsonArray.put(t.toJson());
            }
        }
        return jsonArray;
    }

    // getters
    public String getName() {
        return name;
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i <= 23; i++) {
            Task task = mySchedule.get(i);
            if (task != null) {
                tasks.add(task);
            }
        }
        return tasks;
    }
}


