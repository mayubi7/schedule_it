package model;

import exceptions.EmptyTaskListException;
import exceptions.InvalidTimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



class ScheduleTest {

    Schedule s1;
    Schedule s2;

    @BeforeEach
    public void setup() {
        s1 = new Schedule("my schedule");
        s2 = new Schedule("my schedule for Tuesday");
    }

    @Test
    public void testAddNewTaskEmptyTaskList() {
        ArrayList<Task> list1 = new ArrayList<>();
        ArrayList<Task> list2 = new ArrayList<>();
        try {
            s1.addNewTask(list1);
            s2.addNewTask(list2);
            fail("EmptyTaskListException not thrown!");
        } catch (EmptyTaskListException e) {
            // expected
        }
    }


    @Test
    public void testMakeNewTaskOneTask() {
        Task todo1 = new Task("math homework", 12);
        Task todo2 = new Task("workout", 16);
        ArrayList<Task> list1 = new ArrayList<>();
        ArrayList<Task> list2 = new ArrayList<>();

        list1.add(todo1);
        list2.add(todo2);

        try {
            s1.addNewTask(list1);
            s2.addNewTask(list2);
        } catch (EmptyTaskListException e) {
            fail("EmptyTaskListException thrown!");
        }


        List<String> todos1 = s1.listTasksTodo();
        List<String> todos2 = s2.listTasksTodo();

        List<String> names1 = new ArrayList<>();
        names1.add("math homework");
        List<String> names2 = new ArrayList<>();
        names2.add("workout");

        assertEquals(names1, todos1);
        assertEquals(names2, todos2);

        assertEquals(1,todos1.size());
        assertEquals(1,todos2.size());

    }

    @Test
    public void testMakeNewTaskMultipleTasks() {
        Task todo1 = new Task("meeting", 10);
        Task todo2 = new Task("breakfast", 8);
        Task todo3 = new Task("workout", 8);
        Task todo4 = new Task("sleep", 23);

        ArrayList<Task> list1 = new ArrayList<>();
        ArrayList<Task> list2 = new ArrayList<>();

        List<Task> taskList1 = Arrays.asList(todo1, todo2, todo3);
        List<Task> taskList2 = Arrays.asList(todo4, todo1, todo2);

        list1.addAll(taskList1);
        list2.addAll(taskList2);

        try {
            s1.addNewTask(list1);
            s2.addNewTask(list2);
        } catch (EmptyTaskListException e) {
            fail("EmptyTaskListException thrown!");
        }

        List<String> names1 = new ArrayList<>();
        names1.add("breakfast");
        names1.add("meeting");

        List<String> names2 = new ArrayList<>();
        names2.add("breakfast");
        names2.add("meeting");
        names2.add("sleep");

        assertEquals(names1, s1.listTasksTodo());
        assertEquals(names2, s2.listTasksTodo());

        assertEquals(2,s1.listTasksTodo().size());
        assertEquals(3,s2.listTasksTodo().size());

    }

    @Test
    public void testRescheduleInvalidTime() {
        Task todo1 = new Task("meeting", 13);
        Task todo2 = new Task("homework", 10);

        ArrayList<Task> list1 = new ArrayList<>();
        list1.add(todo1);

        ArrayList<Task> list2 = new ArrayList<>();
        list2.add(todo2);

        try {
            s1.addNewTask(list1);
            s2.addNewTask(list2);
        } catch (EmptyTaskListException e) {
            fail("EmptyTaskListException thrown!");
        }

        try {
            s1.reschedule(todo1, -1);
            fail("InvalidTimeException not thrown!");
        } catch (InvalidTimeException e) {
           // expected
        }

        try {
            s2.reschedule(todo2, 24);
            fail("InvalidTimeException not thrown!");
        } catch (InvalidTimeException e) {
            // expected
        }
    }

    @Test
    public void testReschedule() {
        Task todo1 = new Task("meeting", 10);
        Task todo2 = new Task("homework", 12);

        ArrayList<Task> list1 = new ArrayList<>();
        list1.add(todo1);

        ArrayList<Task> list2 = new ArrayList<>();
        list2.add(todo2);

        try {
            s1.addNewTask(list1);
            s2.addNewTask(list2);
        } catch (EmptyTaskListException e) {
            fail("EmptyTaskListException thrown!");
        }

        try {
            s1.reschedule(todo1, 14);
        } catch (InvalidTimeException e) {
            fail("InvalidTimeException thrown!");
        }
        List<String> taskNames = new ArrayList<>();
        taskNames.add("meeting");

        try {
            s2.reschedule(todo2, 13);
        } catch (InvalidTimeException e) {
            fail("InvalidTimeException thrown!");
        }
        List<String> taskNames2 = new ArrayList<>();
        taskNames2.add("homework");

        assertEquals(taskNames, s1.listTasksTodo());
        assertEquals(taskNames2, s2.listTasksTodo());

        assertEquals(1,s2.listTasksTodo().size());
        assertEquals(1,s1.listTasksTodo().size());

    }

    @Test
    public void testMarkAsComplete() {
        Task todo1 = new Task("meeting", 10);
        Task todo2 = new Task("homework", 12);

        ArrayList<Task> list1 = new ArrayList<>();
        list1.add(todo1);

        ArrayList<Task> list2 = new ArrayList<>();
        list2.add(todo1);
        list2.add(todo2);

        try {
            s1.addNewTask(list1);
            s2.addNewTask(list2);
        } catch (EmptyTaskListException e) {
            fail("EmptyTaskListException thrown!");
        }

        s1.markAsComplete(todo1);
        s2.markAsComplete(todo2);

        List<String> taskNames = new ArrayList<>();
        List<String> taskNames2 = new ArrayList<>();

        assertEquals(taskNames, s1.listTasksTodo());
        assertEquals(taskNames2, s2.listTasksTodo());

        assertTrue(s1.listTasksTodo().isEmpty());
        assertEquals(0,s2.listTasksTodo().size());

    }

    @Test
    public void testDeleteInvalidTime() {
        Task todo1 = new Task("meeting", 10);
        Task todo2 = new Task("homework", 23);

        ArrayList<Task> list1 = new ArrayList<>();
        list1.add(todo1);

        ArrayList<Task> list2 = new ArrayList<>();
        list2.add(todo1);
        list2.add(todo2);

        try {
            s1.addNewTask(list1);
            s2.addNewTask(list2);
        } catch (EmptyTaskListException e) {
            fail("EmptyTaskListException thrown!");
        }

        try {
            s1.deleteTask(-1);
        } catch (InvalidTimeException e) {
            // expected
        }

        try {
            s2.deleteTask(24);
        } catch (InvalidTimeException e) {
            // expected
        }
    }

    @Test
    public void testDeleteTask() {
        Task todo1 = new Task("meeting", 10);
        Task todo2 = new Task("homework", 23);

        ArrayList<Task> list1 = new ArrayList<>();
        list1.add(todo1);

        ArrayList<Task> list2 = new ArrayList<>();
        list2.add(todo1);
        list2.add(todo2);

        try {
            s1.addNewTask(list1);
            s2.addNewTask(list2);
        } catch (EmptyTaskListException e) {
            fail("EmptyTaskListException thrown!");
        }

        try {
            s1.deleteTask(10);
            s2.deleteTask(23);
        } catch (InvalidTimeException e) {
            fail("InvalidTimeException thrown!");
        }

        List<String> taskNames = new ArrayList<>();
        List<String> taskNames2 = new ArrayList<>();
        taskNames2.add("meeting");

        assertEquals(taskNames, s1.listTasksTodo());
        assertEquals(taskNames2, s2.listTasksTodo());

        assertTrue(s1.listTasksTodo().isEmpty());
        assertEquals(1,s2.listTasksTodo().size());

    }

    @Test
    public void testListTasksTodoOneTask() {
        Task todo1 = new Task("meeting", 10);
        Task todo2 = new Task("breakfast", 8);

        ArrayList<Task> list1 = new ArrayList<>();
        ArrayList<Task> list2 = new ArrayList<>();

        list1.add(todo1);
        list2.add(todo2);

        try {
            s1.addNewTask(list1);
            s2.addNewTask(list2);
        } catch (EmptyTaskListException e) {
            fail("EmptyTaskListException thrown!");
        }

        List<String> names1 = new ArrayList<>();
        names1.add("meeting");


        List<String> names2 = new ArrayList<>();
        names2.add("breakfast");

        assertEquals(names1, s1.listTasksTodo());
        assertEquals(names2, s2.listTasksTodo());

        assertEquals(1, s1.listTasksTodo().size());
        assertEquals(1, s2.listTasksTodo().size());

    }

    @Test
    public void testListTasksTodo() {
        Task todo1 = new Task("meeting", 10);
        Task todo2 = new Task("breakfast", 8);
        Task todo3 = new Task("workout", 9);
        Task todo4 = new Task("sleep", 23);

        ArrayList<Task> list1 = new ArrayList<>();
        ArrayList<Task> list2 = new ArrayList<>();

        List<Task> taskList1 = Arrays.asList(todo1, todo2, todo4);
        List<Task> taskList2 = Arrays.asList(todo3 ,todo1, todo2, todo4);

        list1.addAll(taskList1);
        list2.addAll(taskList2);

        try {
            s1.addNewTask(list1);
            s2.addNewTask(list2);
        } catch (EmptyTaskListException e) {
            fail("EmptyTaskListException thrown!");
        }

        try {
            s1.deleteTask(8);
        } catch (InvalidTimeException e) {
            fail("InvalidTimeException thrown!");
        }
        s2.markAsComplete(todo3);

        List<String> names1 = new ArrayList<>();
        names1.add("meeting");
        names1.add("sleep");

        List<String> names2 = new ArrayList<>();
        names2.add("breakfast");
        names2.add("meeting");
        names2.add("sleep");

        assertEquals(names1, s1.listTasksTodo());
        assertEquals(names2, s2.listTasksTodo());

        assertEquals(2, s1.listTasksTodo().size());
        assertEquals(3, s2.listTasksTodo().size());

    }

    @Test
    public void testFindByName() {
        Task todo1 = new Task("meeting", 10);
        Task todo2 = new Task("homework", 23);

        ArrayList<Task> list1 = new ArrayList<>();
        list1.add(todo1);

        ArrayList<Task> list2 = new ArrayList<>();
        list2.add(todo1);
        list2.add(todo2);

        try {
            s1.addNewTask(list1);
            s2.addNewTask(list2);
        } catch (EmptyTaskListException e) {
            fail("EmptyTaskListException thrown!");
        }

        assertEquals(10,s1.findByName("meeting").getTime());
        assertEquals(23,s2.findByName("homework").getTime());
        assertTrue(s1.findByName("magic class") == null);
    }

    @Test
    public void testGetTasksZero() {
        assertEquals(0,s1.getTasks().size());
        assertEquals(0,s2.getTasks().size());
    }

    @Test
    public void testGetTasksNonZero() {
        Task todo1 = new Task("meeting", 10);
        Task todo2 = new Task("homework", 23);

        ArrayList<Task> list1 = new ArrayList<>();
        list1.add(todo1);

        ArrayList<Task> list2 = new ArrayList<>();
        list2.add(todo1);
        list2.add(todo2);

        try {
            s1.addNewTask(list1);
            s2.addNewTask(list2);
        } catch (EmptyTaskListException e) {
            fail("EmptyTaskListException thrown!");
        }

        assertEquals(1, s1.getTasks().size());
        assertEquals(2, s2.getTasks().size());
    }
}