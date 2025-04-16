package function;

import TypeOfTask.*;

import java.util.ArrayList;
import java.util.HashMap;


public class Manager {
    HashMap<Integer, TypeOfTask.Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();


    public void addNewTask(TypeOfTask.Task newTask) {

        tasks.put(newTask.getId(), newTask);
    }

    public void addNewEpic(Epic newEpic) {

        epics.put(newEpic.getId(), newEpic);

    }

    public void addNewSubtask(Subtask newSubtask) {

        subtasks.put(newSubtask.getId(), newSubtask);
        int foundId = newSubtask.getIdEpic();
        epics.get(foundId).getListOfSubtask().add(newSubtask);

    }

    public void showUpTask() {
        if(tasks.isEmpty()){
            System.out.println("Список задач пуст");
            return;
        }
        System.out.println("Задачи: ");

        for (Integer id : tasks.keySet()) {

            System.out.println(tasks.get(id).toConsole());
        }

    }

    public void showUpEpic() {
        if(epics.isEmpty()){
            System.out.println("Список Эпиков пуст");
            return;
        }


        for (Integer id : epics.keySet()) {
            System.out.println(epics.get(id).toConsole());


        }

    }

    public void showUpSubtask() {
        if(subtasks.isEmpty()){
            System.out.println("Список подзадач пуст");
            return;
        }
        for (Integer id : subtasks.keySet()) {

            System.out.println(subtasks.get(id).toConsole());
        }
    }

    public void showUpEpicWithSubtask() {

        for (Integer id : epics.keySet()) {
            System.out.println("Эпик:");
            System.out.println(epics.get(id).toConsole());
            System.out.println("Подзадачи:");
            for (Integer idSubtask : subtasks.keySet()) {
                if (id == subtasks.get(idSubtask).getIdEpic()) {
                    System.out.println(subtasks.get(idSubtask).toConsole());
                }
            }
        }
    }

    public  void removeAllTask() {
        tasks.clear();
    }

    public void removeAllSubtask() {
        subtasks.clear();
        for (Integer id : epics.keySet()) {
            epics.get(id).getListOfSubtask().clear();
            epics.get(id).checkStatus();
        }
    }

    public void removeAllEpic() {
        epics.clear();
        subtasks.clear();
    }
    public void removeById(int id){
        TypeOfTask.Task foundTask = tasks.get(id);
        if (foundTask != null) {
            System.out.println("id найдено, было удалено TypeOfTask.Task :"+foundTask.toConsole());
            tasks.remove(id);
            return;
        }
        Epic foundEpic = epics.get(id);
        if (foundEpic != null) {
            System.out.println("id найдено, был удален TypeOfTask.Epic :"+foundEpic.toConsole());
            epics.remove(id);
            for (Integer idSubtask: subtasks.keySet()){
                if(subtasks.get(idSubtask).getIdEpic()==id){
                    subtasks.remove(id);
                }
            }

            return;
        }
        Subtask foundSubtask = subtasks.get(id);
        if (foundSubtask != null) {
            System.out.println("id найдено, был удален TypeOfTask.Subtask :"+foundSubtask.toConsole());
            subtasks.remove(id);

            return;
        }
        System.out.println("Такого id нет");

    }

    public  void searchById(int id) {
        TypeOfTask.Task foundTask = tasks.get(id);
        if (foundTask != null) {
            System.out.println("id найдено");
            System.out.println(foundTask.toConsole());
            return;
        }
        Epic foundEpic = epics.get(id);
        if (foundEpic != null) {
            System.out.println("id найдено");
            System.out.println(foundEpic.toConsole());
            return;
        }
        Subtask foundSubtask = subtasks.get(id);
        if (foundSubtask != null) {
            System.out.println("id найдено");
            System.out.println(foundSubtask.toConsole());
            return;
        }
        System.out.println("Такого id нет");

    }

    public   void listSubtaskOfEpic(Epic foundEpic) {
        for (Integer id : epics.keySet()) {
            if (epics.get(id).equals(foundEpic)) {
                System.out.println(epics.get(id).getListOfSubtask());
                return;
            }
        }
        System.out.println("Такого TypeOfTask.Epic'a нет");
    }

    // Знаю что писать нельзя так, но я просто не понял что конкретно от меня хотят в обновления и
    // если я не правильно сделал. Напиши, пожалуйста, по подробнее
    public void upateTask(TypeOfTask.Task oldTask, TypeOfTask.Task newTask) {
        int id = oldTask.getId();
        newTask.setId(id);

        tasks.replace(id, newTask);
    }

    public void updateSubtask(Subtask oldSubtask, Subtask newSubtask) {
        int id = oldSubtask.getId();
        newSubtask.setId(id);



        subtasks.replace(id, newSubtask);
        epics.get(oldSubtask.getIdEpic()).getListOfSubtask().remove(oldSubtask);
        epics.get(oldSubtask.getIdEpic()).getListOfSubtask().add(newSubtask);
        int idFOrEpic = oldSubtask.getIdEpic();
        newSubtask.setIdEpic(idFOrEpic);
        epics.get(idFOrEpic).checkStatus();

    }

    public  void updateEpic(Epic oldEpic, Epic newEpic) {
        int id = oldEpic.getId();
        epics.replace(id, newEpic);
        ArrayList<Subtask> newListOfSubtask = oldEpic.getListOfSubtask();
        newEpic.setListOfSubtask(newListOfSubtask);

        newEpic.checkStatus();
    }

    public void showSubtaskbyEpic(Epic epic) {
        ArrayList<Subtask> printSubtask = epic.getListOfSubtask();
        for (Subtask print : printSubtask){
            System.out.println(print.toConsole());
        }
    }


}
