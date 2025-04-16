import TypeOfTask.*;
import function.Manager;

public class Main {
    public static void main(String[] args) {
        Test();

    }
    static public void Test(){
        Manager manager =  new Manager();
        TypeOfTask.Task task1 = new TypeOfTask.Task("task1","descriptionForTask1" +
                " спринта");
        TypeOfTask.Task task2 = new TypeOfTask.Task("task2","descriptionForTask2");
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        Epic epic1 = new Epic("epic1","descriptionForEpic1 ");
        Epic epic2 = new Epic("epic2","descriptionForEpic2 ");
        manager.addNewEpic(epic1);
        manager.addNewEpic(epic2);
        Subtask subtask1 = new Subtask("subtask1","descriptionForSubtask1",epic1);
        Subtask subtask2 = new Subtask("subtask2", "descriptionForSubtask2",epic1);
        Subtask subtask3 = new Subtask("subtask3", "descriptionForSubtask3",epic2);
        manager.addNewSubtask(subtask1);
        manager.addNewSubtask(subtask2);
        manager.addNewSubtask(subtask3);
        manager.showUpTask();
        manager.showUpEpicWithSubtask();
        TypeOfTask.Task task3 = new TypeOfTask.Task("Не умереть","Не умереть пока разбираешься с финальным заданием 4" +
                " спринта", Status.IN_PROGRESS);
        manager.upateTask(task1,task3);
        System.out.println("------");
        manager.showUpTask();
        Subtask subtask4 = new Subtask("subtask1","escriptionForSubtask1", Status.DONE);
        manager.updateSubtask(subtask1,subtask4);
        System.out.println("----");
        manager.showUpEpicWithSubtask();
        Subtask subtask5 = new Subtask("subtask2", "descriptionForSubtask2", Status.DONE);
        manager.updateSubtask(subtask2,subtask5);
        System.out.println("----");
        manager.showUpEpicWithSubtask();
        System.out.println("----");
        manager.showSubtaskbyEpic(epic2);
        System.out.println("----");
        manager.removeById(1);
        manager.showUpTask();
        System.out.println("----");
        manager.showUpEpic();
        manager.removeById(3);
        manager.showUpEpic();
        manager.removeAllTask();
        System.out.println("---");
        manager.showUpTask();
        manager.showUpEpic();
        manager.showUpSubtask();
        manager.removeAllSubtask();
        manager.showUpSubtask();

        TypeOfTask.Task task5 = new TypeOfTask.Task("task1","descriptionForTask1" +
                " спринта");
        TypeOfTask.Task task6 = new TypeOfTask.Task("task2","descriptionForTask2");
        manager.addNewTask(task5);
        manager.addNewTask(task6);
        manager.searchById(10);









    }
}
