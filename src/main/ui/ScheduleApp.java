package ui;


import model.Schedule;
import model.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class ScheduleApp {

    private Scanner input;
    private Schedule mySchedule;

    public ScheduleApp() {
        runSchedule();
    }

    private void runSchedule() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");

    }

    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t a -> add new task(s)");
        System.out.println("\t d -> delete a task");
        System.out.println("\t l -> list all incomplete tasks");
        System.out.println("\t m -> mark a task as complete");
        System.out.println("\t r -> reschedule a task");
        System.out.println("\t q -> quit");
    }

    private void init() {
        mySchedule = new Schedule();
        input = new Scanner(System.in);
    }

    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddNewTask();
        } else if (command.equals("d")) {
            doDelete();
        } else if (command.equals("l")) {
            doListAllTasks();
        } else if (command.equals("m")) {
            doMarkAsComplete();
        } else if (command.equals("r")) {
            doReschedule();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    private void doDelete() {
        System.out.println("Enter task to delete: ");
        String taskName = input.next();
        input.nextLine();
        System.out.println("Enter time: ");
        int time = input.nextInt();
        Task todo1 = new Task(taskName, time);
        mySchedule.deleteTask(todo1);

    }

    private void doListAllTasks() {
        for (int i = 0; i < mySchedule.listTasksTodo().size(); i++) {
            System.out.println(mySchedule.listTasksTodo().get(i));
        }
    }

    private void doMarkAsComplete() {
        System.out.println("Enter completed task: ");
        String taskName = input.next();
        input.nextLine();
        System.out.println("Enter time: ");
        Integer time = input.nextInt();
        Task todo = new Task(taskName, time);
        mySchedule.markAsComplete(todo);

    }

    private void doReschedule() {
        System.out.println("Enter task: ");
        String taskName = input.next();
        input.nextLine();
        System.out.println("Enter original scheduled time: ");
        int time = input.nextInt();
        System.out.println("Enter new time for task: ");
        int newTime = input.nextInt();
        Task todo = new Task(taskName, time);
        mySchedule.reschedule(todo, newTime);
    }

    private void doAddNewTask() {
        ArrayList<Task> tasks = describeTasks();
        mySchedule.addNewTask(tasks);
    }

    private ArrayList<Task> describeTasks() {
        ArrayList<Task> todos = new ArrayList<>();
        boolean addMore = true;

        while (addMore) {
            System.out.println("Enter task or done if finished adding to schedule:");
            String taskName = input.next();
            input.nextLine();
            if (taskName.equals("done")) {
                addMore = false;
            } else {
                System.out.println("Enter time:");
                Integer time = input.nextInt();
                Task todo1 = new Task(taskName, time);
                todos.add(todo1);
                System.out.println(todo1.getName() + " at "
                        + todo1.getTime() + " hrs has been added to the schedule if timeslot was available");
            }
        }
        return todos;

    }


}
