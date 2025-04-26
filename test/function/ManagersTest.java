package function;

import TypeOfTask.Status;
import TypeOfTask.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    public void    checkInicilizetiom(){
        TaskManager manager = Managers.getDefault();
        Assertions.assertNotNull(manager,"Готов к работе ");
    }


}