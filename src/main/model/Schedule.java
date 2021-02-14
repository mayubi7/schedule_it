package model;

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

public class Schedule {
    private ArrayList<Task> mySchedule;

    //EFFECTS: Creates a schedule that has 24 hours and no tasks
    public Schedule() {
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
    //MODIFIES: this
    //EFFECTS: assign task to a new time and remove task from previous time
    public void reschedule(Task task, int newTime) {
        int oldTime = task.getTime();
        mySchedule.set(oldTime, null);
        mySchedule.set(newTime, task);
        task.setTime(newTime);
    }

    //MODIFIES: this
    //EFFECTS: changes status of task to complete on the schedule
    public void markAsComplete(Task task) {
        task.setStatus("complete");
        mySchedule.set(task.getTime(), task);

    }

    //MODIFIES: this
    //EFFECTS: delete task from schedule
    public void deleteTask(Task task) {
        mySchedule.set(task.getTime(), null);

    }


    //EFFECTS: lists tasks for the day that are not yet completed
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
