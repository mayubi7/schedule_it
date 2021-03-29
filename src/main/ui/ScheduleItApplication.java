package ui;


import model.Schedule;
import model.Task;
import persistence.JsonReader;
import persistence.JsonWriter;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class ScheduleItApplication extends JFrame implements ActionListener {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 700;
    private static final String JSON_STORE = "./data/schedule.json";
    private static final String SOUND_FILE = "./data/button-3.wav";

    private Schedule schedule;
    private JLabel label;
    private JLabel label2;
    private JTextField field;
    private JTextField field2;
    private JTextField field3;
    private JTextField field4;
    private JTextField field5;
    private JPanel displayTasks;
    private JPanel panel;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    public ScheduleItApplication() {
        super("Schedule App");
        initializeFields();
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: instantiates schedule, JsonWriter and JsonReader
    private void initializeFields() {
        this.schedule = new Schedule("My Schedule");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this ScheduleItApplication will operate
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH,HEIGHT));
        setResizable(false);
        addTaskButton();
        removeTaskButton();
        completeButton();
        saveButton();
        loadButton();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: draws the add task button onto the JFrame
    private void addTaskButton() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(100, 200));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        JButton btn = new JButton("Add Task");
        btn.setActionCommand("myButton");
        btn.addActionListener(this);

        label = new JLabel("flag");
        JLabel addDescription = new JLabel("Task description:");
        JLabel addTime = new JLabel("Time:");
        field = new JTextField(5);
        field2 = new JTextField(5);
        add(addDescription, BorderLayout.WEST);
        add(field);
        add(addTime);
        add(field2);
        add(btn);
        add(label);
        pack();
        setLocationRelativeTo(null);
    }

    // MODIFIES: this
    // EFFECTS: draws the remove task button onto the JFrame
    private void removeTaskButton() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 150));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        JButton btn = new JButton("Remove Task");
        btn.setActionCommand("RemoveButton");
        btn.addActionListener(this);

        JLabel label = new JLabel("flag");
        JLabel addTime = new JLabel("Time to clear:");
        field3 = new JTextField(5);
        add(addTime);
        add(field3);
        add(btn);
        add(label);
        pack();
        setLocationRelativeTo(null);
    }

    // MODIFIES: this
    // EFFECTS: draws the mark as complete task button onto the JFrame
    private void completeButton() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(100, 200));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        JButton btn = new JButton("Mark task as complete");
        btn.setActionCommand("complete");
        btn.addActionListener(this);

        JLabel label = new JLabel("flag");
        JLabel addDescription = new JLabel("Completed Task:");
        JLabel addTime = new JLabel("Scheduled Time:");
        field4 = new JTextField(5);
        field5 = new JTextField(5);
        add(addDescription, BorderLayout.WEST);
        add(field4);
        add(addTime);
        add(field5);
        add(btn);
        add(label);
        pack();
        setLocationRelativeTo(null);
    }

    // MODIFIES: this
    // EFFECTS: draws the save schedule button onto the JFrame
    private void saveButton() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 150));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        JButton btn = new JButton("Save Schedule");
        btn.setActionCommand("SaveButton");
        btn.addActionListener(this);
        add(btn);
    }

    // MODIFIES: this
    // EFFECTS: draws the load schedule button onto the JFrame
    private void loadButton() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 150));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        JButton btn = new JButton("Load Schedule");
        btn.setActionCommand("LoadButton");
        btn.addActionListener(this);
        add(btn);
    }

    // MODIFIES: this
    // adds/removes task to schedule or loads/save schedule
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("myButton")) {
            addTask();
        } else if (e.getActionCommand().equals("RemoveButton")) {
            removeTask();
        } else if (e.getActionCommand().equals("SaveButton")) {
            try {
                jsonWriter.open();
                jsonWriter.write(schedule);
                jsonWriter.close();
            } catch (FileNotFoundException error) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        } else if (e.getActionCommand().equals("complete")) {
            completeTask();
        } else {
            try {
                schedule = jsonReader.read();
                showSchedule();
            } catch (IOException err) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }


    // EFFECTS: marks task as complete in schedule
    private void completeTask() {
        Task todo = new Task(field4.getText(), Integer.parseInt(field5.getText()));
        schedule.markAsComplete(todo);
        showSchedule();
    }

    // MODIFIES: this
    // EFFECTS: removes task from schedule and shows change in panel
    private void removeTask() {
        String details = field3.getText() + "hrs: cleared.";
        label.setText(details);
        schedule.deleteTask(Integer.parseInt(field3.getText()));
        showSchedule();
    }

    // MODIFIES: this
    // EFFECTS: adds task to schedule and shows change in panel
    private void addTask() {
        playSound(SOUND_FILE);
        label.setText(field2.getText() + "hrs: " + field.getText() + " added to schedule if timeslot available.");
        Task todo = new Task(field.getText(), Integer.parseInt(field2.getText()));
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(todo);
        schedule.addNewTask(tasks);
        showSchedule();
    }

    // MODIFIES: this
    // EFFECTS: makes a panel that shows the tasks in the schedule
    private void showSchedule() {
        displayTasks = new JPanel();
        displayTasks.setPreferredSize(new Dimension(500,300));
        displayTasks.setLayout(new FlowLayout());
        displayTasks.setBackground(Color.PINK);
        String title = "Schedule";
        Border border = BorderFactory.createTitledBorder(title);
        displayTasks.setBorder(border);
        setLayout(new BorderLayout());
        add(displayTasks, BorderLayout.SOUTH);
        int size = schedule.getTasks().size();
        for (int i = 0; i < size; i++) {
            int hrs = schedule.getTasks().get(i).getTime();
            String name = schedule.getTasks().get(i).getName();
            String details = hrs + "hrs: " + name;
            JLabel label = new JLabel(details);
            displayTasks.add(label);
        }
    }

    // MODIFIES: this
    // EFFECTS: plays the sound from file given
    public void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Could not play sound.");
        }
    }

}
