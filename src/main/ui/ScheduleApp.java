package ui;

import model.Schedule;
import model.Task;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Schedule Application
public class ScheduleApp {
    //template based on Teller app example

    private static final String JSON_STORE = "./data/schedule.json";
    private Scanner input;
    private Schedule mySchedule;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    //EFFECTS: runs Schedule app
    public ScheduleApp() throws FileNotFoundException {
        runSchedule();
    }

    //MODIFIES: this
    //EFFECTS: processes user input
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

    //EFFECTS: displays menu of options to choose from to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t a -> add new task(s)");
        System.out.println("\t d -> delete a task");
        System.out.println("\t p -> print all incomplete tasks");
        System.out.println("\t m -> mark a task as complete");
        System.out.println("\t r -> reschedule a task");
        System.out.println("\t s -> save schedule to file");
        System.out.println("\t l -> load schedule from file");
        System.out.println("\t q -> quit");
    }

    //MODIFIES: this
    //EFFECTS: initializes Schedule
    private void init() {
        input = new Scanner(System.in);
        mySchedule = new Schedule("My schedule");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    //MODIFIES: this
    //EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddNewTask();
        } else if (command.equals("d")) {
            doDelete();
        } else if (command.equals("p")) {
            doListAllTasks();
        } else if (command.equals("m")) {
            doMarkAsComplete();
        } else if (command.equals("r")) {
            doReschedule();
        } else if (command.equals("s")) {
            saveSchedule();
        } else if (command.equals("l")) {
            loadSchedule();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    //MODIFIES: this
    //EFFECTS: deletes task given its name and time
    private void doDelete() {
        System.out.println("Enter time: ");
        int time = input.nextInt();
        mySchedule.deleteTask(time);

    }

    //EFFECTS: lists task names of the tasks that are not complete
    private void doListAllTasks() {
        for (int i = 0; i < mySchedule.listTasksTodo().size(); i++) {
            System.out.println(mySchedule.listTasksTodo().get(i));
        }
    }

    //MODIFIES: this
    //EFFECTS: given name and time of a task, changes its status to complete
    private void doMarkAsComplete() {
        System.out.println("Enter completed task: ");
        String taskName = input.next();
        input.nextLine();
        mySchedule.markAsComplete(mySchedule.findByName(taskName));

    }

    //MODIFIES: this
    //EFFECTS: given name, old time, new time, changes task's scheduled time from old to the new time
    private void doReschedule() {
        System.out.println("Enter task: ");
        String taskName = input.next();
        input.nextLine();
        System.out.println("Enter new time for task: ");
        int newTime = input.nextInt();
        mySchedule.reschedule(mySchedule.findByName(taskName), newTime);
    }

    //MODIFIES: this
    //EFFECTS: adds the list of tasks to schedule at appropriate time
    private void doAddNewTask() {
        ArrayList<Task> tasks = describeTasks();
        mySchedule.addNewTask(tasks);
    }

    //EFFECTS: takes in task names with task times and creates a list of tasks
    private ArrayList<Task> describeTasks() {
        ArrayList<Task> todos = new ArrayList<>();
        boolean addMore = true;

        while (addMore) {
            System.out.println("Enter task name or done if finished adding to schedule:");
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

    // EFFECTS: saves schedule to file
    private void saveSchedule() {
        // code copied and modified from JsonSerializationDemo
        try {
            jsonWriter.open();
            jsonWriter.write(mySchedule);
            jsonWriter.close();
            System.out.println("Saved " + mySchedule.getName() + " to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads schedule from file
    private void loadSchedule() {
        // code copied and modified from JsonSerializationDemo
        try {
            mySchedule = jsonReader.read();
            System.out.println("Loaded " + mySchedule.getName() + " from" + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
