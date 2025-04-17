package function;

import TypeOfTask.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class Manager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    Print print = new Print();


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
        print.showUpTask(tasks);


    }

    public void showUpEpic() {
        print.showUpEpic(epics);
    }

    public void showUpSubtask() {
        print.showUpSubtask(subtasks);
    }

    public void showUpEpicWithSubtask() {
        print.showUpEpicWithSubtask(epics, subtasks);

    }

    public void removeAllTask() {
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

    public void removeById(int id) {
        Task foundTask = tasks.get(id);
        if (foundTask != null) {

            print.idDetectedByTask(foundTask);
            tasks.remove(id);
            return;
        }
        Epic foundEpic = epics.get(id);
        if (foundEpic != null) {
            print.idDetectedByEpic(foundEpic);
            epics.remove(id);
            for (Integer idSubtask : subtasks.keySet()) {
                if (subtasks.get(idSubtask).getIdEpic() == id) {
                    subtasks.remove(id);
                }
            }

            return;
        }
        Subtask foundSubtask = subtasks.get(id);
        if (foundSubtask != null) {
            print.idDetectedBySubtask(foundSubtask);
            subtasks.remove(id);
            int idEpic = foundSubtask.getIdEpic();
            epics.get(idEpic).getListOfSubtask().remove(foundSubtask);


            return;
        }
        print.notificationNotFoundId();


    }

    public void searchById(int id) {
        Task foundTask = tasks.get(id);
        if (foundTask != null) {
            print.notificationFoundId();
            foundTask.printTask();

            return;
        }
        Epic foundEpic = epics.get(id);
        if (foundEpic != null) {
            print.notificationFoundId();
            foundEpic.printTask();
            return;
        }
        Subtask foundSubtask = subtasks.get(id);
        if (foundSubtask != null) {
            print.notificationFoundId();
            foundSubtask.printTask();


            return;
        }
        print.notificationNotFoundId();


    }

    public void listSubtaskOfEpic(Epic foundEpic) {
        print.listSubtaskOfEpic(foundEpic, epics);
    }

    public void upateTask(Task oldTask, Task newTask) {
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

    public void updateEpic(Epic oldEpic, Epic newEpic) {
        int id = oldEpic.getId();
        epics.replace(id, newEpic);
        ArrayList<Subtask> newListOfSubtask = oldEpic.getListOfSubtask();
        newEpic.setListOfSubtask(newListOfSubtask);

        newEpic.checkStatus();
    }

    public void showSubtaskbyEpic(Epic epic) {

        print.showSubtaskbyEpic(epic);
    }


}
