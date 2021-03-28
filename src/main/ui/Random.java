package ui;

import model.Schedule;
import model.Task;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Random extends JFrame {

    private Schedule schedule;

    public Random() {
        setLayout(new BorderLayout());
        setSize(new Dimension(500,400));
        setResizable(false);
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(100,100));
        panel.setLayout(new FlowLayout());
        panel.setBackground(Color.PINK);
        schedule = new Schedule("hi");
        ArrayList<Task> todos = new ArrayList<Task>();
        todos.add(new Task("meeting", 10));
        todos.add(new Task("dance", 18));
        schedule.addNewTask(todos);
        int size = schedule.listTasksTodo().size();

        for (int i = 0; i < size;i++) {
            panel.add(new Label(schedule.listTasksTodo().get(i)));
        }
        add(panel, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }


    public static void main(String[] args) {
        new Random();
    }
}
