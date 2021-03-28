package ui;

import model.Schedule;
import model.Task;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class AddTaskButton extends JFrame implements ActionListener {

    private JLabel label;
    private JTextField field;
    private JTextField field2;
    private Schedule schedule;
    private JList<Task> tasks;


    public AddTaskButton() {
        super("Add Task");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400,150));
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
        setVisible(true);
        setResizable(false);

    }

    public void actionPerformed(ActionEvent e) {
        schedule = new Schedule("my schedule");
        tasks = new JList<Task>();
        if (e.getActionCommand().equals("myButton")) {
            label.setText(field.getText() + " " + field2.getText());
            String time = field2.getText();
            Task todo = new Task(field.getText(),Integer.parseInt(time));
            ArrayList<Task> todos = new ArrayList<>();
            todos.add(todo);
            schedule.addNewTask(todos);
        }
    }

}
