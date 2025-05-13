package type.of.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class SubtaskTest {

    @Test
    public void shouldBeEqualsSubtask() {
        Subtask subtask1 = new Subtask("subtask1", "decriptionSubtask", Status.DONE, 1);
        Subtask subtask2 = new Subtask("subtask1", "decriptionSubtask", Status.DONE, 1);
        boolean isEquals = subtask1.equals(subtask2);
        Assertions.assertTrue(isEquals, "Id не равны");

    }

}