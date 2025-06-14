package type.of.task;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.HashSet;
import java.util.Stack;


class EpicTest {


    @Test
    public void shouldBeEqualsEpic() {
        Epic epic1 = Epic.createdEpicWithStatus("epic1", "descr", Status.NEW, 1);
        Epic epic2 = Epic.createdEpicWithStatus("epic1", "descr", Status.NEW, 1);
        boolean isEquals = epic1.equals(epic2);
        Assertions.assertTrue(isEquals, "Id не равны");
    }


}