package function;

import TypeOfTask.Epic;
import TypeOfTask.Status;
import TypeOfTask.Subtask;
import TypeOfTask.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

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
        manager.addNewTask(task11);
        manager.addNewTask(task12);
        Epic epic1 = new Epic("epic1", "descriptionForEpic1 ", 13);
        Epic epic2 = new Epic("epic2", "descriptionForEpic2 ", 14);
        Subtask subtask1 = new Subtask("subtask1", "descriptionForSubtask1", Status.NEW,
                15, epic1);
        Subtask subtask2 = new Subtask("subtask2", "descriptionForSubtask2", Status.NEW,
                16, epic1);
        Subtask subtask3 = new Subtask("subtask3", "descriptionForSubtask3", Status.NEW,
                17, epic2);
        manager.addNewEpic(epic1);
        manager.addNewEpic(epic2);
        manager.addNewSubtask(subtask1);
        manager.addNewSubtask(subtask2);
        manager.addNewSubtask(subtask3);
        manager.searchTaskById(1);
        expected.add(task1);
        manager.searchEpicById(1);
        Assertions.assertEquals(expected, manager.getHistory());
        expected.clear();
        manager.searchTaskById(2);
        manager.searchTaskById(3);
        manager.searchTaskById(4);
        manager.searchTaskById(5);
        manager.searchEpicById(13);
        expected.add(task1);
        expected.add(task2);
        expected.add(task3);
        expected.add(task4);
        expected.add(task5);
        expected.add(epic1);
        Assertions.assertEquals(expected, manager.getHistory(), "История не работает");
        manager.searchTaskById(1);
        expected.removeFirst();
        expected.add(task1);
        Assertions.assertEquals(expected, manager.getHistory(), "Проблема с дублями в истории");


    }


}