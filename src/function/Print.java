package function;

import java.util.ArrayList;
import java.util.HashMap;

import TypeOfTask.*;

public class Print {
    public void showUpTask(HashMap<Integer, Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст");
            return;
        }
        System.out.println("Задачи: ");

        for (Integer id : tasks.keySet()) {
            System.out.println(tasks.get(id));


        }

    }

    public void showUpEpic(HashMap<Integer, Epic> epics) {
        if (epics.isEmpty()) {
            System.out.println("Список Эпиков пуст");
            return;
        }
        System.out.println("Эпики: ");


        for (Integer id : epics.keySet()) {

            System.out.println(epics.get(id));


        }

    }

    public void showUpSubtask(HashMap<Integer, Subtask> subtasks) {
        if (subtasks.isEmpty()) {
            System.out.println("Список подзадач пуст");
            return;
        }
        System.out.println("Подзадачи: ");
        for (Integer id : subtasks.keySet()) {
            System.out.println(subtasks.get(id));


        }
    }

    public void showUpEpicWithSubtask(HashMap<Integer, Epic> epics, HashMap<Integer, Subtask> subtasks) {

        for (Integer id : epics.keySet()) {
            System.out.println("Эпик:");
            System.out.println(epics.get(id));
            if (epics.get(id).getListOfSubtask().isEmpty()) {
                System.out.println("Список этого Эпика пуст");
                continue;
            }
            System.out.println("Подзадачи:");

            for (Integer idSubtask : subtasks.keySet()) {

                if (id == subtasks.get(idSubtask).getIdEpic()) {
                    System.out.println(subtasks.get(idSubtask));

                }
            }
        }
    }

    public void showSubtaskbyEpic(Epic epic) {
        ArrayList<Subtask> printSubtask = epic.getListOfSubtask();
        if (printSubtask.isEmpty()) {
            System.out.println("В данном эпике нет подзадач");
            return;
        }
        System.out.println("Подзадачи: ");
        for (Subtask print : printSubtask) {
            System.out.println(print);
        }
    }

    public void listSubtaskOfEpic(Epic foundEpic, HashMap<Integer, Epic> epics) {
        for (Integer id : epics.keySet()) {
            if (epics.get(id).equals(foundEpic)) {
                System.out.println(epics.get(id).getListOfSubtask());
                return;
            }
        }
        System.out.println("Такого TypeOfTask.Epic'a нет");
    }

    public void notificationNotFoundId() {
        System.out.println("Такого id нет");

    }

    public void notificationFoundId() {
        System.out.println("id найдено");

    }

    public void idDetectedByTask(Task task) {

        System.out.println("id найдено, было удалено " + task.getClass().getSimpleName()+":  " + task);
    }

    public void idDetectedByEpic(Epic epic) {
        System.out.println("id найдено, было удалено " + epic.getClass().getSimpleName()+":  " + epic);


    }

    public void idDetectedBySubtask(Subtask subtask) {

        System.out.println("id найдено, было удалено " + subtask.getClass().getSimpleName()+":  " + subtask);


    }



}
