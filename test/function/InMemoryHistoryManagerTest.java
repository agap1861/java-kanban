package function;

import org.junit.jupiter.api.BeforeEach;
import type.of.task.Status;
import type.of.task.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

class InMemoryHistoryManagerTest extends TaskManagerTest<TaskManager> {
    List<Task> expected;


    @Override
    protected TaskManager createTaskManager() {
        return super.taskManager = Managers.getDefault();
    }

    @BeforeEach
    public void createHistory() {
        expected = new ArrayList<>();
        taskManager.searchTaskById(1);
        expected.add(taskManager.searchTaskById(1));
        taskManager.searchTaskById(2);
        expected.add(taskManager.searchTaskById(2));
        taskManager.searchSubtaskById(6);
        expected.add(taskManager.searchSubtaskById(6));
        taskManager.searchEpicById(4);
        expected.add(taskManager.searchEpicById(4));
    }


    @Test
    public void shouldCorrectlyAddInListOfHistory() {
        Assertions.assertEquals(expected, taskManager.getHistory(), "Не корректно добавляет в историю или вообще " +
                "не добавляет");
    }

    @Test
    public void ifCopyExistInHistoryRemoveFirstCopyAndAddLastCopy() {

        taskManager.searchTaskById(1);
        expected.removeFirst();
        expected.add(new Task("task1", "descriptionForTask1", Status.NEW, 30,
                LocalDateTime.of(2024, Month.JUNE, 1, 9, 0), 1));
        Assertions.assertEquals(expected, taskManager.getHistory(), "Не правильно удаляет повтор на первой позиции");
        expected.remove(taskManager.searchTaskById(2));
        taskManager.searchTaskById(2);
        expected.add(taskManager.searchTaskById(2));
        Assertions.assertEquals(expected, taskManager.getHistory(), "Не правильно удаляет дубль когда он находится" +
                "НЕ на крайних позициях");
        taskManager.searchTaskById(2);
        Assertions.assertEquals(expected, taskManager.getHistory(), "Не правильно удаляет дубль когда он находится" +
                "на крайней позиции");
    }

    @Test
    public void shouldCorrectlyRemoveSubtasksWhenWeRemoveEpicInHistory() {
        taskManager.removeEpicById(4);
        expected.remove(2);
        expected.remove(2);
        Assertions.assertEquals(expected, taskManager.getHistory());

    }

}