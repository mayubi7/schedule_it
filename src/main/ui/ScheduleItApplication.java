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
    private JTextField field;
    private JTextField field2;
    private JTextField field3;
    private JPanel displayTasks;
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

    // TODO ISSUE THO: ADDING MULTIPLE TASKS AT SAME TIME
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("myButton")) {
            playSound(SOUND_FILE);
            label.setText(field2.getText() + "hrs: " + field.getText() + " added to schedule if timeslot available.");
            Task todo = new Task(field.getText(), Integer.parseInt(field2.getText()));
            ArrayList<Task> tasks = new ArrayList<>();
            tasks.add(todo);
            schedule.addNewTask(tasks);
            showSchedule();
        } else if (e.getActionCommand().equals("RemoveButton")) {
            String details = field3.getText() + "hrs: cleared.";
            label.setText(details);
            schedule.deleteTask(Integer.parseInt(field3.getText()));
            showSchedule();
        } else if (e.getActionCommand().equals("SaveButton")) {
            try {
                jsonWriter.open();
                jsonWriter.write(schedule);
                jsonWriter.close();
            } catch (FileNotFoundException error) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        } else {
            try {
                schedule = jsonReader.read();
                showSchedule();
            } catch (IOException err) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }


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

    // TODO: ADD MODIFIES AND STUFF
    // TODO: ALSO, CANT SEE FLAG STUFF FIX MAKE THEM ALL BORDER LAYOUT
    private void removeTaskButton() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 150));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        JButton btn = new JButton("Remove Task");
        btn.setActionCommand("RemoveButton");
        btn.addActionListener(this);

        label = new JLabel("flag");
        JLabel addTime = new JLabel("Time to clear:");
        field3 = new JTextField(5);
        add(addTime);
        add(field3);
        add(btn);
        add(label);
        pack();
        setLocationRelativeTo(null);
    }

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
