package ui;


import java.io.FileNotFoundException;

// starts Schedule App
public class Main {
    public static void main(String[] args) {
        try {
            new ScheduleApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
