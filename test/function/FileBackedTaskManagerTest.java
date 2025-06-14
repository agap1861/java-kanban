package function;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import java.io.*;


class FileBackedTaskManagerTest extends TaskManagerTest {
    String name = "records";
    File file;

    {
        try {
            file = File.createTempFile(name, ".csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected TaskManager createTaskManager() {
        return new FileBackedTaskManager(Managers.getDefaultHistory(), file);
    }


    @Test
    public void loadFromFile() {
        FileBackedTaskManager newManager = (FileBackedTaskManager) super.taskManager;
        newManager.loadFromFile(file);
        Assertions.assertEquals(taskManager.showUpTask(), newManager.showUpTask());
        Assertions.assertEquals(taskManager.showUpEpic(), newManager.showUpEpic());
        Assertions.assertEquals(taskManager.showUpSubtask(), newManager.showUpSubtask());

    }


}
