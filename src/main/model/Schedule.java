package model;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private ArrayList<Task> mySchedule;

    public Schedule() {
        mySchedule = new ArrayList<>();
        for (int i = 0; i <= 23; i++) {
            mySchedule.add(i, null);
        }
    }

    //REQUIRES: String is non-empty and int must be within 0-23
    //METHODS: this
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
    //METHODS: this
    //EFFECTS: assign task to a new time and remove task from previous time
    public void reschedule(Task task, int newTime) {
        int oldTime = task.getTime();
        mySchedule.set(oldTime, null);
        mySchedule.set(newTime, task);
        task.setTime(newTime);
    }

    //METHODS: this
    //EFFECTS: change status of task to complete
    public void markAsComplete(Task task) {
        task.setStatus("complete");

    }

    //METHODS: this
    //EFFECTS: delete task from schedule
    public void deleteTask(Task task) {
        mySchedule.set(task.getTime(), null);

    }

    //METHODS: this
    //EFFECTS: lists task for the day that are not yet completed
    public List<String> listTasksTodo() {
        List<String> todos = new ArrayList<>();
        for (int i = 0; i <= 23; i++) {
            Task todo = mySchedule.get(i);
            if (todo != null && todo.getStatus() != "complete") {
                todos.add(todo.getName());
            }
        }
        return todos;
    }

}
