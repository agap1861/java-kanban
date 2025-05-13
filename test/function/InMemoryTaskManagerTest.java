package function;

import TypeOfTask.Epic;
import TypeOfTask.Status;
import TypeOfTask.Subtask;
import TypeOfTask.Task;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;


class InMemoryTaskManagerTest {
    private static TaskManager inMemoryTaskManager;


    @BeforeEach
    public void createTask() {
        inMemoryTaskManager = new InMemoryTaskManager(Managers.getDefaultHistory());

        Task task1 = new Task("task1", "descriptionForTask1" +
                " спринта", Status.NEW, 1);
        Task task2 = new Task("task2", "descriptionForTask2", Status.NEW, 2);
        inMemoryTaskManager.addNewTask(task1);
        inMemoryTaskManager.addNewTask(task2);
        Epic epic1 = new Epic("epic1", "descriptionForEpic1 ", 3);
        Epic epic2 = new Epic("epic2", "descriptionForEpic2 ", 4);
        inMemoryTaskManager.addNewEpic(epic1);
        inMemoryTaskManager.addNewEpic(epic2);
        Subtask subtask1 = new Subtask("subtask1", "descriptionForSubtask1", Status.NEW,
                5, epic1);
        Subtask subtask2 = new Subtask("subtask2", "descriptionForSubtask2", Status.NEW,
                6, epic1);
        Subtask subtask3 = new Subtask("subtask3", "descriptionForSubtask3", Status.NEW,
                7, epic2);
        inMemoryTaskManager.addNewSubtask(subtask1);
        inMemoryTaskManager.addNewSubtask(subtask2);
        inMemoryTaskManager.addNewSubtask(subtask3);
    }


    @Test
    public void validWhenAddTask() {

        Task task1 = new Task("task1", "descriptionForTask1" +
                " спринта", Status.NEW, 1);
        boolean resultOfAdd = inMemoryTaskManager.addNewTask(task1);
        Assertions.assertFalse(resultOfAdd, "Добавилось Task  с одинаковым id");
    }

    @Test
    public void validWhenAddEpic() {
        Epic epic1 = new Epic("epic1", "descriptionForEpic1 ", 3);
        boolean resultOfAdd = inMemoryTaskManager.addNewEpic(epic1);
        Assertions.assertFalse(resultOfAdd, "Добавилось Epic  с одинаковым id");
    }

    @Test
    public void validWhenAddSubtask() {
        Subtask subtask1 = new Subtask("subtask1", "descriptionForSubtask1", Status.NEW,
                5, inMemoryTaskManager.searchEpicById(3));
        Subtask subtask2 = new Subtask("subtask1", "descriptionForSubtask1", Status.NEW,
                5, inMemoryTaskManager.searchEpicById(4));

        boolean resultOfAddInTheSameEpic = inMemoryTaskManager.addNewSubtask(subtask1);
        boolean resultOfAddInDiffrentEpic = inMemoryTaskManager.addNewSubtask(subtask2);
        Assertions.assertFalse(resultOfAddInTheSameEpic, "Добавилось Subtask  с одинаковым id");
        Assertions.assertTrue(resultOfAddInDiffrentEpic, "Не Добавился subtask в другой epic ");
    }

    @Test
    public void ShouldNotBeChangedTask() {

        var manager = Managers.getDefault();
        String expectedName = "TaskName";
        String expectedDescriprion = "TaskdeCription";
        Status expectedStatus = Status.DONE;
        int expectedId = 6;
        Task task = new Task(expectedName, expectedDescriprion, expectedStatus, expectedId);
        manager.addNewTask(task);
        Task resultTask = manager.searchTaskById(expectedId);
        Assertions.assertEquals(expectedName, resultTask.getName());
        Assertions.assertEquals(expectedId, resultTask.getId());
        Assertions.assertEquals(expectedDescriprion, resultTask.getDescription());
        Assertions.assertEquals(expectedStatus, resultTask.getStatus());
    }

    @Test
    public void ShouldNotBeChangedEpic() {
        var manager = Managers.getDefault();
        String expectedName = "EpicName";
        String expectedDescriprion = "EpicDecription";
        Status expectedStatus = Status.NEW;
        int expectedId = 6;
        Epic epic = new Epic(expectedName, expectedDescriprion, expectedId);
        manager.addNewTask(epic);
        Task resultTask = manager.searchTaskById(expectedId);
        Assertions.assertEquals(expectedName, resultTask.getName());
        Assertions.assertEquals(expectedId, resultTask.getId());
        Assertions.assertEquals(expectedDescriprion, resultTask.getDescription());
        Assertions.assertEquals(expectedStatus, resultTask.getStatus());
    }

    @Test
    public void ShouldNotBeChangedSubtask() {
        var manager = Managers.getDefault();
        String expectedName = "SubtaskName";
        String expectedDescriprion = "SubtaskDecription";
        Status expectedStatus = Status.IN_PROGRESS;
        int expectedId = 6;
        Subtask subtask = new Subtask(expectedName, expectedDescriprion, expectedStatus, expectedId);
        ;
        manager.addNewTask(subtask);
        Task resultTask = manager.searchTaskById(expectedId);
        Assertions.assertEquals(expectedName, resultTask.getName());
        Assertions.assertEquals(expectedId, resultTask.getId());
        Assertions.assertEquals(expectedDescriprion, resultTask.getDescription());
        Assertions.assertEquals(expectedStatus, resultTask.getStatus());
    }

    @Test
    public void searchTasks() {
        Task expectedTask = new Task("task1", "descriptionForTask1" +
                " спринта", Status.NEW, 1);
        Epic expectedEpic = new Epic("epic1", "descriptionForEpic1 ", 3);
        Subtask expectedSubtask = new Subtask("subtask3", "descriptionForSubtask3", Status.NEW,
                7, inMemoryTaskManager.searchEpicById(3));
        Assertions.assertEquals(inMemoryTaskManager.searchTaskById(1), expectedTask, "Поиск Task'ov не работает");
        Assertions.assertEquals(inMemoryTaskManager.searchEpicById(3), expectedEpic, "Поиск Epic'ov не работает");
        Assertions.assertEquals(inMemoryTaskManager.searchSubtaskById(7), expectedSubtask,
                "Поиска Subtask'ov не рабоет");
    }

    @Test
    public void changeStatus() {
        Task newTask = new Task("task1", "descriptionForTask1" +
                " спринта", Status.IN_PROGRESS, 1);
        Task oldTask = new Task("task1", "descriptionForTask1" +
                " спринта", Status.NEW, 1);
        inMemoryTaskManager.upateTask(oldTask, newTask);
        Assertions.assertEquals(inMemoryTaskManager.searchTaskById(1).getStatus(), newTask.getStatus(),
                "Статус Task'a не поменялся");

        Subtask newSubtask = new Subtask("subtask3", "descriptionForSubtask3", Status.DONE,
                5, inMemoryTaskManager.searchEpicById(3));
        Subtask onldSubtask = new Subtask("subtask1", "descriptionForSubtask1", Status.NEW,
                5, inMemoryTaskManager.searchEpicById(3));
        inMemoryTaskManager.updateSubtask(onldSubtask, newSubtask);
        Assertions.assertEquals(inMemoryTaskManager.searchSubtaskById(5).getStatus(), newSubtask.getStatus(),
                "Статус у Subtask'a не поменялся");
        Assertions.assertEquals(inMemoryTaskManager.searchEpicById(3).getStatus(), Status.IN_PROGRESS,
                "Статус у Epic'a не поменялся");
        Subtask newSubtask2 = new Subtask("subtask2", "descriptionForSubtask2", Status.DONE,
                6, inMemoryTaskManager.searchEpicById(3));
        inMemoryTaskManager.updateSubtask(inMemoryTaskManager.searchSubtaskById(6), newSubtask2);
        Assertions.assertEquals(inMemoryTaskManager.searchSubtaskById(6).getStatus(), newSubtask2.getStatus(),
                "Статус у Subtask'a не поменялся");
        Assertions.assertEquals(inMemoryTaskManager.searchEpicById(3).getStatus(), Status.DONE, "Статус у Epic'a" +
                "не поменялся");
    }

    @Test
    public void changeStatusWhenDeletedSubtask() {
        Subtask subtask1 = new Subtask("subtask1", "descriptionForSubtask1", Status.DONE,
                5, inMemoryTaskManager.searchEpicById(3));
        inMemoryTaskManager.updateSubtask(inMemoryTaskManager.searchSubtaskById(5), subtask1);
        Assertions.assertEquals(Status.IN_PROGRESS, inMemoryTaskManager.searchEpicById(3).getStatus(),
                "Статус у Epica не обновился");
        inMemoryTaskManager.removeAllSubtask();
        Assertions.assertEquals(inMemoryTaskManager.searchEpicById(3).getStatus(), Status.NEW,
                "Статус у Epica не обновился");

    }

    @Test
    public void checkRemoveById() {
        inMemoryTaskManager.removeTaskById(1);
        Assertions.assertNull(inMemoryTaskManager.searchTaskById(1));
        inMemoryTaskManager.removeEpicById(3);
        Assertions.assertNull(inMemoryTaskManager.searchEpicById(3));
        inMemoryTaskManager.removeSubtaskById(7);
        Assertions.assertNull(inMemoryTaskManager.searchSubtaskById(7));
    }

    @Test
    public void showListOfSubtask() {
        Collection<Subtask> expected = new ArrayList<>();
        expected.add(inMemoryTaskManager.searchSubtaskById(5));
        expected.add(inMemoryTaskManager.searchSubtaskById(6));
        Collection<Subtask> result = inMemoryTaskManager.listSubtaskOfEpic(inMemoryTaskManager.searchEpicById(3));
        Assertions.assertEquals(expected, result, "Вывод Subtaskov по Epicu не правильно работает");

    }

    @Test
    public void shoudCorrecltyRemoveSubtaskWhenRemoveEpic() {
        Epic epic1 = new Epic("epic1", "descriptionForEpic1 ", 3);
        Epic epic2 = new Epic("epic2", "descriptionForEpic2 ", 4);
        InMemoryTaskManager manager = new InMemoryTaskManager(Managers.getDefaultHistory());
        Subtask subtask1 = new Subtask("subtask1", "descriptionForSubtask1", Status.NEW,
                5, epic1);
        Subtask subtask2 = new Subtask("subtask2", "descriptionForSubtask2", Status.NEW,
                6, epic1);
        Subtask subtask3 = new Subtask("subtask3", "descriptionForSubtask3", Status.NEW,
                7, epic2);
        manager.addNewEpic(epic1);
        manager.addNewEpic(epic2);
        manager.addNewSubtask(subtask1);
        manager.addNewSubtask(subtask2);
        manager.addNewSubtask(subtask3);
        Map<Integer, Subtask> subtasks = new HashMap<>();
        subtasks.put(subtask3.getId(), subtask3);
        manager.removeEpicById(3);
        Assertions.assertEquals(subtasks, manager.showUpSubtask(), "Удаление subtask'ov при удалении " +
                "epic'ov не правильное ");


    }

    @Test
    public void shouldCorrectlyRemoveSubtaskInEpicWhenRemoveSubtask() {
        Epic epic1 = new Epic("epic1", "descriptionForEpic1 ", 3);

        InMemoryTaskManager manager = new InMemoryTaskManager(Managers.getDefaultHistory());
        Subtask subtask1 = new Subtask("subtask1", "descriptionForSubtask1", Status.NEW,
                5, epic1);
        Subtask subtask2 = new Subtask("subtask2", "descriptionForSubtask2", Status.NEW,
                6, epic1);
        manager.addNewEpic(epic1);
        manager.addNewSubtask(subtask1);
        manager.addNewSubtask(subtask2);
        manager.removeSubtaskById(5);
        List<Subtask> subtasks = new ArrayList<>();
        subtasks.add(subtask2);
        Assertions.assertEquals(manager.listSubtaskOfEpic(epic1), subtasks, "При удалении Subtas'ov не" +
                " удаляются в epic'e");
    }

}