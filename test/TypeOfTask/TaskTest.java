package TypeOfTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
class TaskTest {


    @Test
    public void shouldBeEqualsTask(){
        Task task1 = new Task("task1","descriphtionTask1",Status.NEW , 1);
        Task task2 = new Task("tasdsgdgk1","descriphtionTask2",Status.NEW,1);
        boolean isEquals = task1.equals(task2);
        Assertions.assertTrue(isEquals,"ID не равны");
    }



}