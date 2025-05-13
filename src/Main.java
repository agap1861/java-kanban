import TypeOfTask.*;
import function.InMemoryHistoryManager;
import function.InMemoryTaskManager;
import function.Managers;
import function.TaskManager;

public class Main {
    public static void main(String[] args) {
        Task task1 = new Task("task1", "descriptionForTask1", Status.NEW);
        Task task2 = new Task("task2", "descriptionForTask2", Status.NEW);
        Epic epic1 = new Epic("epic1", "descriptionForEpic1 ");
        Epic epic2 = new Epic("epic2", "descriptionForEpic2 ");
        Subtask subtask1 = new Subtask("subtask1", "descriptionForSubtask1", Status.NEW,
                5, epic1);
        Subtask subtask2 = new Subtask("subtask2", "descriptionForSubtask2", Status.NEW,
                6, epic1);
        Subtask subtask3 = new Subtask("subtask3", "descriptionForSubtask3", Status.NEW,
                7, epic1);
        TaskManager manager = Managers.getDefault();
        manager.addNewTask(task1);
        manager.addNewEpic(epic1);
        manager.addNewSubtask(subtask1);
        manager.addNewTask(task2);
        manager.addNewEpic(epic2);
        manager.addNewSubtask(subtask2);
        manager.addNewSubtask(subtask3);
        manager.searchEpicById(epic1.getId());
        manager.searchSubtaskById(subtask2.getId());
        manager.searchTaskById(task2.getId());
        manager.searchTaskById(task1.getId());
        manager.searchTaskById(task1.getId());
        manager.searchEpicById(epic1.getId());
        manager.searchEpicById(epic2.getId());
        System.out.println(manager.getHistory());
        manager.removeTaskById(task1.getId());
        System.out.println(manager.getHistory());
        manager.removeEpicById(epic1.getId());
        System.out.println(manager.getHistory());

    }
}
