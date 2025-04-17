import TypeOfTask.*;
import function.Manager;

public class Main {
    public static void main(String[] args) {
        Test();

    }

    static public void Test() {
        Manager manager = new Manager();
        Task task1 = new Task("task1", "descriptionForTask1" +
                " спринта");
        Task task2 = new Task("task2", "descriptionForTask2");
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        Epic epic1 = new Epic("epic1", "descriptionForEpic1 ");
        Epic epic2 = new Epic("epic2", "descriptionForEpic2 ");
        manager.addNewEpic(epic1);
        manager.addNewEpic(epic2);
        Subtask subtask1 = new Subtask("subtask1", "descriptionForSubtask1", epic1);
        Subtask subtask2 = new Subtask("subtask2", "descriptionForSubtask2", epic1);
        Subtask subtask3 = new Subtask("subtask3", "descriptionForSubtask3", epic2);
        manager.addNewSubtask(subtask1);
        manager.addNewSubtask(subtask2);
        manager.addNewSubtask(subtask3);
        System.out.println(manager.showUpTask().values());
        System.out.println(manager.showUpEpic().values());
        System.out.println(manager.showUpSubtask().values());
        Task task3 = new Task("task1", "1descriptionForTask2", Status.DONE);
        manager.upateTask(task1, task3);
        Subtask subtask4 = new Subtask("subtask3", "descriptionForSubtask3", Status.DONE);
        manager.updateSubtask(subtask1, subtask4);
        System.out.println("---");
        System.out.println(manager.showUpTask().values());
        System.out.println(manager.showUpEpic().values());
        System.out.println(manager.showUpSubtask().values());
        System.out.println("--");
        Subtask subtask5 = new Subtask("subtask3", "descriptionForSubtask3", Status.DONE);
        manager.updateSubtask(subtask2, subtask5);
        System.out.println(manager.showUpEpic().values());
        System.out.println(manager.showUpSubtask().values());
        System.out.println("---");
        System.out.println("---");
        System.out.println(manager.showUpTask().values());
        manager.removeTaskById(1);
        System.out.println(manager.showUpTask().values());
        System.out.println("---");
        System.out.println(manager.showUpSubtask().values());
        manager.removeSubtaskById(6);
        System.out.println(manager.showUpSubtask().values());


    }
}
