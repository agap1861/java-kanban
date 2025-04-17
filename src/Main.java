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
        manager.searchById(3);
        manager.showUpTask();
        manager.showUpEpic();
        manager.showUpSubtask();
        manager.showSubtaskbyEpic(epic2);
        manager.removeById(7);
        manager.showUpSubtask();
        manager.showSubtaskbyEpic(epic2);
        manager.showUpEpicWithSubtask();
        Epic epic3 = new Epic("epic3","discription");
        manager.addNewEpic(epic3);
        Subtask subtask4 = new Subtask("subtask4", "descriptionForSubtask2",epic3);
        Subtask subtask5 = new Subtask("subtask5", "descriptionForSubtask3",epic3);
        manager.addNewSubtask(subtask4);
        manager.addNewSubtask(subtask5);
        System.out.println("----");
        manager.showUpEpicWithSubtask();
        Subtask subtask6 = new Subtask("subtask6", "descriptionForSubtask2",Status.DONE);
        manager.updateSubtask(subtask4,subtask6);
        System.out.println("---");
        manager.showUpEpicWithSubtask();
        Subtask subtask7 = new Subtask("subtask6", "descriptionForSubtask2",Status.DONE);
        manager.updateSubtask(subtask5,subtask7);
        manager.showUpEpic();
       /* manager.removeAllSubtask();
        manager.showUpEpic();
        manager.removeAllTask();
        manager.removeAllEpic();
        manager.showUpTask();
        manager.showUpEpic();
        manager.showUpSubtask();*/
        System.out.println("---");
        manager.searchById(6);
















    }
}
