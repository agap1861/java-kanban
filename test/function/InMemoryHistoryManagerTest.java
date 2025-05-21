package function;

import org.junit.jupiter.api.BeforeEach;
import type.of.task.Epic;
import type.of.task.Status;
import type.of.task.Subtask;
import type.of.task.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class InMemoryHistoryManagerTest {
    private static TaskManager manager;
    private static List<Task> expected;

    @BeforeEach
    public void addInHistory() {
        manager = Managers.getDefault();
        expected = new ArrayList<>();
        Task task1 = new Task("task1", "descriptionForTask1", Status.NEW, 1);
        Task task2 = new Task("task2", "descriptionForTask2", Status.NEW, 2);
        Task task3 = new Task("task1", "descriptionForTask1", Status.NEW, 3);
        Task task4 = new Task("task2", "descriptionForTask2", Status.NEW, 4);
        Task task5 = new Task("task1", "descriptionForTask1", Status.NEW, 5);
        Task task6 = new Task("task2", "descriptionForTask2", Status.NEW, 6);
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        manager.addNewTask(task3);
        manager.addNewTask(task4);
        manager.addNewTask(task5);
        manager.addNewTask(task6);
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

    }

    @Test
    public void shouldCorrectlyAddInListOfHistory() {
        Assertions.assertEquals(expected, manager.getHistory(), "Не корректно добавляет в историю или вообще " +
                "не добавляет");
    }

    @Test
    public void ifCopyExistInHistoryRemoveFirstCopyAndAddLastCopy() {
        manager.searchTaskById(1);
        expected.removeFirst();
        expected.add(new Task("task1", "descriptionForTask1", Status.NEW, 1));
        Assertions.assertEquals(expected, manager.getHistory(), "Не правильно удаляет повтор на первой позиции");
        manager.searchTaskById(3);
        Task task3 = new Task("task1", "descriptionForTask1", Status.NEW, 3);
        expected.remove(task3);
        expected.add(task3);
        Assertions.assertEquals(expected, manager.getHistory(), "Не правильно удаляет дубль когда он находится" +
                "НЕ на крайних позициях");
        manager.searchTaskById(3);
        Assertions.assertEquals(expected, manager.getHistory(), "Не правильно удаляет дубль когда он находится" +
                "на крайней позиции");
    }


}