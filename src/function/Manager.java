package function;

import TypeOfTask.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class Manager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();


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

    public HashMap<Integer, Task> showUpTask() {

        return tasks;


    }

    public HashMap<Integer, Epic> showUpEpic() {
        return epics;

    }

    public HashMap<Integer, Subtask> showUpSubtask() {

        return subtasks;
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

    public void removeTaskById(int id) {

        Task foundTask = tasks.get(id);
        if (foundTask != null) {
            tasks.remove(id);

        }

    }

    public void removeEpicById(int id) {
        Epic foundEpic = epics.get(id);
        if (foundEpic != null) {

            epics.remove(id);
            for (Integer idSubtask : subtasks.keySet()) {
                if (subtasks.get(idSubtask).getIdEpic() == id) {
                    subtasks.remove(id);
                }
            }


        }
    }

    public void removeSubtaskById(int id) {
        Subtask foundSubtask = subtasks.get(id);
        if (foundSubtask != null) {

            subtasks.remove(id);
            int idEpic = foundSubtask.getIdEpic();
            epics.get(idEpic).getListOfSubtask().remove(foundSubtask);

        }

    }


    public Task searchTaskById(int id) {
        Task foundTask = tasks.get(id);
        return foundTask;

    }

    public Epic searchEpicById(int id) {
        Epic foundEpic = epics.get(id);
        return foundEpic;

    }

    public Subtask searchSubtaskById(int id) {
        Subtask foundSubtask = subtasks.get(id);
        return foundSubtask;

    }


    public ArrayList<Subtask> listSubtaskOfEpic(Epic foundEpic) {
        ArrayList<Subtask> listOfSubtasl = foundEpic.getListOfSubtask();
        return listOfSubtasl;


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


}
