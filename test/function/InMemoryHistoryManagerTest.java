package function;

import TypeOfTask.Status;
import TypeOfTask.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    @Test
    public void saveHistory() {
        ArrayList<Task> expected = new ArrayList<>();
        TaskManager manager = Managers.getDefault();
        Task task1 = new Task("task1", "descriptionForTask1", Status.NEW, 1);
        Task task2 = new Task("task2", "descriptionForTask2", Status.NEW, 2);
        Task task3 = new Task("task1", "descriptionForTask1", Status.NEW, 3);
        Task task4 = new Task("task2", "descriptionForTask2", Status.NEW, 4);
        Task task5 = new Task("task1", "descriptionForTask1", Status.NEW, 5);
        Task task6 = new Task("task2", "descriptionForTask2", Status.NEW, 6);
        Task task7 = new Task("task1", "descriptionForTask1", Status.NEW, 7);
        Task task8 = new Task("task2", "descriptionForTask2", Status.NEW, 8);
        Task task9 = new Task("task1", "descriptionForTask1", Status.NEW, 9);
        Task task10 = new Task("task2", "descriptionForTask2", Status.NEW, 10);
        Task task11 = new Task("task2", "descriptionForTask2", Status.NEW, 11);
        Task task12 = new Task("task2", "descriptionForTask2", Status.NEW, 12);
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        manager.addNewTask(task3);
        manager.addNewTask(task4);
        manager.addNewTask(task5);
        manager.addNewTask(task6);
        manager.addNewTask(task7);
        manager.addNewTask(task8);
        manager.addNewTask(task9);
        manager.addNewTask(task10);
        Assertions.assertEquals(expected, manager.getHistory(), "Что то не так, ArrayList не равны");
        manager.searchTaskById(1);
        expected.add(task1);
        Assertions.assertEquals(expected, manager.getHistory(), "Что то не так, ArrayList не равны");
        manager.searchTaskById(2);
        expected.add(task2);
        Assertions.assertEquals(expected, manager.getHistory(), "Что то не так, ArrayList не равны");
        manager.searchTaskById(3);
        expected.add(task3);
        manager.searchTaskById(4);
        expected.add(task4);
        manager.searchTaskById(5);
        expected.add(task5);
        manager.searchTaskById(6);
        expected.add(task6);
        manager.searchTaskById(7);
        expected.add(task7);
        manager.searchTaskById(8);
        expected.add(task8);
        manager.searchTaskById(9);
        expected.add(task9);
        manager.searchTaskById(10);
        expected.add(task10);
        Assertions.assertEquals(expected, manager.getHistory(), "Что то не так," +
                " скорее всего проблема с переполнением");
        manager.addNewTask(task11);
        manager.searchTaskById(11);
        expected.removeFirst();
        expected.add(task11);
        Assertions.assertEquals(expected, manager.getHistory(), "Что то не так," +
                " скорее всего проблема с переполнением");
        manager.addNewTask(task12);
        manager.searchTaskById(12);
        expected.removeFirst();
        expected.add(task12);
        Assertions.assertEquals(expected, manager.getHistory(), "Что то не так," +
                " скорее всего проблема с переполнением");


    }

}