package type.of.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;


class SubtaskTest {

    @Test
    public void shouldBeEqualsSubtask() {
        Epic epic1 = Epic.createdEpicWithStatus("epic1", "descr", Status.NEW, 1);
        Subtask subtask1 = new Subtask("subtask1", "decriptionSubtask", Status.NEW, 5,
                LocalDateTime.of(2024, Month.JUNE, 1, 9, 0), 2, epic1);
        Subtask subtask2 = new Subtask("subtask1", "decriptionSubtask", Status.NEW, 5,
                LocalDateTime.of(2024, Month.JUNE, 1, 9, 0), 2, epic1);
        boolean isEquals = subtask1.equals(subtask2);
        Assertions.assertTrue(isEquals, "объекты не равны");

    }

}