package TypeOfTask;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.HashSet;
import java.util.Stack;


class EpicTest {


    @Test
    public void shouldBeEqualsEpic() {
        Epic epic1 = new Epic("epic1", "descriptionEpic1", 1);
        Epic epic2 = new Epic("epic1", "descriptionEpic1", 1);
        boolean isEquals = epic1.equals(epic2);
        Assertions.assertTrue(isEquals, "Id не равны");

        epic2.setListOfSubtask(new HashSet<>());
        epic2.setListOfSubtask(new Stack<>());
    }


}