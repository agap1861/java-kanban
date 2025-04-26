package TypeOfTask;


import function.InMemoryTaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



class EpicTest {


    @Test
    public void shouldBeEqualsEpic(){
        Epic epic1 = new Epic("epic1","descriptionEpic1",1);
        Epic epic2 = new Epic("epic1","descriptionEpic1",1);
        boolean isEquals = epic1.equals(epic2);
        Assertions.assertTrue(isEquals,"Id не равны");
    }


}