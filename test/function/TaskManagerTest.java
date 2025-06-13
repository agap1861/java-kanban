package function;

import type.of.task.Epic;
import type.of.task.Status;
import type.of.task.Subtask;
import type.of.task.Task;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;

    protected abstract T createTaskManager();


    @BeforeEach
    public void AddDifferentTaskInManager() {
        taskManager = createTaskManager();

        Task task1 = new Task("task1", "descriptionForTask1", Status.NEW, 30,
                LocalDateTime.of(2024, Month.JUNE, 1, 9, 0), 1);
        Task task2 = new Task("task2", "descriptionForTask2", Status.NEW, 25,
                LocalDateTime.of(2024, Month.JULY, 15, 14, 30), 2);
        taskManager.addNewTask(task1);
        taskManager.addNewTask(task2);
        Epic epic1 = new Epic("epic1", "descriptionForEpic1 ", 3);
        Epic epic2 = new Epic("epic2", "descriptionForEpic2 ", 4);
        taskManager.addNewEpic(epic1);
        taskManager.addNewEpic(epic2);
        Subtask subtask1 = new Subtask("subtask1", "descriptionForSubtask1", Status.NEW,
                45, LocalDateTime.of(2024, Month.OCTOBER, 5, 16, 15),
                5, epic1);
        Subtask subtask2 = new Subtask("subtask2", "descriptionForSubtask2", Status.NEW,
                120, LocalDateTime.of(2025, Month.JANUARY, 10, 11, 20),
                6, epic2);
        Subtask subtask3 = new Subtask("subtask3", "descriptionForSubtask3", Status.NEW, 50,
                LocalDateTime.of(2023, Month.DECEMBER, 25, 10, 5), 7, epic2);
        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);
        taskManager.addNewSubtask(subtask3);

    }


    @Test
    public void ShouldNotAddTaskWithTheSameId() {

        Task task1 = new Task("task1", "descriptionForTask1" +
                " спринта", Status.NEW, 1);
        boolean resultOfAdd = taskManager.addNewTask(task1);
        Assertions.assertFalse(resultOfAdd, "Добавилось Task  с одинаковым id");
    }

    @Test
    public void ShouldNotAddEpicWithTheSameId() {
        Epic epic1 = Epic.createdEpicWithStatus("epic1", "descriptionForEpic1 ", Status.NEW, 3);

        boolean resultOfAdd = taskManager.addNewEpic(epic1);
        Assertions.assertFalse(resultOfAdd, "Добавилось Epic  с одинаковым id");
    }

    @Test
    public void ShouldNotAddSubtaskWithTheSameId() {
        Subtask subtask1 = new Subtask("subtask1", "descriptionForSubtask1", Status.NEW, 45,
                LocalDateTime.of(2024, Month.OCTOBER, 5, 16, 15), 5,
                taskManager.searchEpicById(3));

        boolean resultOfAddInTheSameEpic = taskManager.addNewSubtask(subtask1);

        Assertions.assertFalse(resultOfAddInTheSameEpic, "Добавилось Subtask  с одинаковым id");

    }

    @Test
    public void ShouldNotBeChangedFieldsOfTaskWhenAdd() {

        var manager = Managers.getDefault();
        String expectedName = "TaskName";
        String expectedDescriprion = "TaskdeCription";
        Status expectedStatus = Status.DONE;
        int expectedId = 6;
        LocalDateTime expectedStartTime = LocalDateTime.of(2024, Month.NOVEMBER, 1, 13, 0);
        Integer expectedDuration = 40;
        Task task = new Task(expectedName, expectedDescriprion, expectedStatus, expectedDuration, expectedStartTime,
                6);
        manager.addNewTask(task);
        Task resultTask = manager.searchTaskById(expectedId);
        Assertions.assertEquals(expectedName, resultTask.getName());
        Assertions.assertEquals(expectedId, resultTask.getId());
        Assertions.assertEquals(expectedDescriprion, resultTask.getDescription());
        Assertions.assertEquals(expectedStatus, resultTask.getStatus());
        Assertions.assertEquals(Duration.ofMinutes(expectedDuration), resultTask.getDuration());
        Assertions.assertEquals(expectedStartTime, resultTask.getStartTime());

    }

    @Test
    public void ShouldNotBeChangedFieldsOfEpicWhenAdd() {
        var manager = Managers.getDefault();
        String expectedName = "EpicName";
        String expectedDescription = "EpicDescription";
        Status expectedStatus = Status.NEW;
        int expectedId = 6;
        Epic epic = Epic.createdEpicWithStatus(expectedName, expectedDescription, expectedStatus, expectedId);
        manager.addNewTask(epic);
        Task resultTask = manager.searchTaskById(expectedId);
        Assertions.assertEquals(expectedName, resultTask.getName());
        Assertions.assertEquals(expectedId, resultTask.getId());
        Assertions.assertEquals(expectedDescription, resultTask.getDescription());
        Assertions.assertEquals(expectedStatus, resultTask.getStatus());
    }

    @Test
    public void ShouldNotBeChangedFieldsOfSubtaskWhenAdd() {
        var manager = Managers.getDefault();
        String expectedName = "SubtaskName";
        String expectedDescription = "SubtaskDescription";
        Status expectedStatus = Status.IN_PROGRESS;
        int expectedId = 6;
        LocalDateTime expectedStartTime = LocalDateTime.of(2024, Month.NOVEMBER, 1, 13, 0);
        Integer expectedDuration = 40;
        Epic epic = new Epic("EpicName", "EpicDescription");
        Subtask subtask = new Subtask(expectedName, expectedDescription, expectedStatus, expectedDuration,
                expectedStartTime, expectedId, epic);
        manager.addNewEpic(epic);
        manager.addNewSubtask(subtask);
        Subtask resultTask = manager.searchSubtaskById(expectedId);
        Assertions.assertEquals(expectedName, resultTask.getName());
        Assertions.assertEquals(expectedId, resultTask.getId());
        Assertions.assertEquals(expectedDescription, resultTask.getDescription());
        Assertions.assertEquals(expectedStatus, resultTask.getStatus());
        Assertions.assertEquals(Duration.ofMinutes(expectedDuration), resultTask.getDuration());
        Assertions.assertEquals(expectedStartTime, resultTask.getStartTime());
    }

    @Test
    public void searchTask() {
        Task expectedTask = new Task("task1", "descriptionForTask1" +
                " спринта", Status.NEW, 1);
        Assertions.assertEquals(taskManager.searchTaskById(1), expectedTask, "Поиск Task'ov не работает");

    }

    @Test
    public void searchEpic() {
        Epic expectedEpic = Epic.createdEpicWithStatus("epic1", "descriptionForEpic1 ", Status.NEW,
                3);
        Assertions.assertEquals(taskManager.searchEpicById(3), expectedEpic, "Поиск Epic'ov " +
                "не работает");
    }

    @Test
    public void searchSubtask() {
        Epic epic1 = new Epic("epic2", "descriptionForEpic2 ");
        Subtask expectedSubtask = new Subtask("subtask1", "descriptionForSubtask1", Status.NEW,
                45, LocalDateTime.of(2024, Month.OCTOBER, 5, 16, 15),
                5, epic1);
        Assertions.assertEquals(taskManager.searchSubtaskById(5), expectedSubtask,
                "Поиск Subtask'ov не работает");
    }

    @Test
    public void changeStatusOfTask() {
        Task newTask = new Task("task1", "descriptionForTask1", Status.IN_PROGRESS, 30,
                LocalDateTime.of(2024, Month.JUNE, 1, 9, 0), 1);
        Task oldTask = taskManager.searchTaskById(1);
        taskManager.updateTask(oldTask, newTask);
        Assertions.assertEquals(taskManager.searchTaskById(1).getStatus(), newTask.getStatus(),
                "Статус Task'a не поменялся или поменялся не правильно");

    }

    @Test
    public void changeStatusOfEpic() {
        Status expected = Status.IN_PROGRESS;
        Subtask subtask = new Subtask("subtask", "descr", Status.DONE, 50,
                LocalDateTime.of(2023, Month.DECEMBER, 25, 10, 5), 7,
                taskManager.searchEpicById(4));
        taskManager.updateSubtask(taskManager.searchSubtaskById(7), subtask);
        Assertions.assertEquals(expected, taskManager.searchEpicById(4).getStatus(), "не правильно меняется" +
                "статус");

    }

    @Test
    public void changeStatusOfSubtask() {
        Status expected = Status.DONE;
        Subtask subtask = new Subtask("subtask", "descr", Status.DONE, 50,
                LocalDateTime.of(2023, Month.DECEMBER, 25, 10, 5), 7,
                taskManager.searchEpicById(4));
        taskManager.updateSubtask(taskManager.searchSubtaskById(7), subtask);
        Assertions.assertEquals(expected, taskManager.searchSubtaskById(7).getStatus(), "Не правильно меняется " +
                "статус");
    }

    @Test
    public void changeStatusOfEpicWhenDeletedSubtask() {
        Subtask subtask = new Subtask("subtask", "descr", Status.DONE, 50,
                LocalDateTime.of(2023, Month.DECEMBER, 25, 10, 5), 6,
                taskManager.searchEpicById(4));
        taskManager.updateSubtask(taskManager.searchSubtaskById(6), subtask);
        Assertions.assertEquals(Status.IN_PROGRESS, taskManager.searchEpicById(4).getStatus(),
                "Статус у Epic'a не обновился");
        taskManager.removeAllSubtask();
        Assertions.assertEquals(Status.NEW, taskManager.searchEpicById(4).getStatus(),
                "Статус у Epic'a не обновился");

    }

    @Test
    public void checkRemoveById() {
        taskManager.removeTaskById(1);
        Assertions.assertNull(taskManager.searchTaskById(1));
        taskManager.removeEpicById(3);
        Assertions.assertNull(taskManager.searchEpicById(3));
        taskManager.removeSubtaskById(7);
        Assertions.assertNull(taskManager.searchSubtaskById(7));
    }

    @Test
    public void showListOfSubtask() {
        Collection<Subtask> expected = new ArrayList<>();
        expected.add(taskManager.searchSubtaskById(6));
        expected.add(taskManager.searchSubtaskById(7));
        Collection<Subtask> result = taskManager.listSubtaskOfEpic(taskManager.searchEpicById(4));
        Assertions.assertEquals(expected, result, "Вывод Subtask'ov по Epic'u не правильно работает");

    }

    @Test
    public void shouldCorrectlyRemoveSubtaskWhenRemoveEpic() {

        Map<Integer, Subtask> expectedSubtasks = new HashMap<>();
        expectedSubtasks.put(taskManager.searchSubtaskById(5).getId(), taskManager.searchSubtaskById(5));
        taskManager.removeEpicById(4);


        Assertions.assertEquals(expectedSubtasks, taskManager.showUpSubtask(), "Не правильно удаляются subtask's" +
                "когда мы удаляем Epic");


    }

    @Test
    public void shouldCorrectlyRemoveSubtaskInEpicWhenRemoveSubtask() {
        List<Subtask> expected = List.of(taskManager.searchSubtaskById(6));
        taskManager.removeSubtaskById(7);
        Assertions.assertEquals(expected, taskManager.listSubtaskOfEpic(taskManager.searchEpicById(4)),
                "При удалении Subtask'ov не удаляются в epic'e");
    }

    @Test
    public void shouldCorrectlySortByTime() {
        List<Task> checked = new ArrayList<>(taskManager.getPrioritizedTasks());
        for (int i = 0; i < checked.size() - 1; i++) {
            Assertions.assertTrue(checked.get(i).getStartTime().isBefore(checked.get(i + 1).getStartTime()));
        }
    }

    @Test
    public void shouldNotAddTaskWithCrossTime() {
        Task cross = new Task("name", "decr", Status.NEW, 30,
                LocalDateTime.of(2024, Month.JUNE, 1, 8, 31), 8);
        boolean flag = taskManager.addNewTask(cross);
        Assertions.assertFalse(flag);

    }
    @Test
    public void shouldCorrectlySumDurationInEpic(){
       Duration expectedDuration = taskManager.searchSubtaskById(6).getDuration()
               .plus(taskManager.searchSubtaskById(7).getDuration());
       Assertions.assertEquals(expectedDuration,taskManager.searchEpicById(4).getDuration());
    }
    @Test
    public void shouldCorrectlyChangedDurationInEpic(){
        Duration expectedDuration = taskManager.searchSubtaskById(6).getDuration();
        taskManager.removeSubtaskById(7);
        Assertions.assertEquals(expectedDuration,taskManager.searchEpicById(4).getDuration());
    }


}
