package TypeOfTask;


import function.InMemoryTaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



class EpicTest {

/*    @Test
    public void CantAddSubtaskAsEpic(){
        Epic epic = new Epic("test","test");

        Subtask subtask1 = new Subtask("testSub1", "", epic);
        Subtask subtask2 = new Subtask("testSub2", "", epic);

        subtask1.setIdEpic(subtask2.getId());

        Assertions.assertEquals(epic.getId(), subtask1.getIdEpic());
    }// ПОдумать что с этим сделать

    @Test
    public void CantAddSubtaskAsEpic1(){
        Epic epic = new Epic("test","test");

        Subtask subtask = new Subtask("testSub1", "", epic);
        subtask.setIdEpic(subtask.getId());

        Assertions.assertEquals(epic.getId(), subtask.getIdEpic());
    }// тоже самая ошибка, что и сверху*/
    @Test
    public void shouldBeEqualsEpic(){
        Epic epic1 = new Epic("epic1","descriptionEpic1",1);
        Epic epic2 = new Epic("epic1","descriptionEpic1",1);
        boolean isEquals = epic1.equals(epic2);
        Assertions.assertTrue(isEquals,"Id не равны");
    }


}