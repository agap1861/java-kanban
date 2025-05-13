package function;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



class ManagersTest {

    @Test
    public void checkInicilizetiom() {
        TaskManager manager = Managers.getDefault();
        Assertions.assertNotNull(manager);
    }


}