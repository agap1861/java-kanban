package function;

import TypeOfTask.*;

import java.util.ArrayList;
import java.util.HashMap;


public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    HistoryManager historyManager = Managers.getDefaultHistory();





    @Override
    public boolean addNewTask(Task newTask) {
        if(tasks.containsValue(newTask)){
            return false;
        }else {
            tasks.put(newTask.getId(), newTask);
            return true;
        }

    }

    @Override
    public boolean addNewEpic(Epic newEpic) {
        if(epics.containsValue(newEpic)){
            return false;
        }else {
            epics.put(newEpic.getId(), newEpic);
            return true;
        }

    }

    @Override
    public boolean addNewSubtask(Subtask newSubtask) {

        int foundId = newSubtask.getIdEpic();
        boolean relevantEpic = epics.get(foundId).getListOfSubtask().contains(newSubtask);
        if( relevantEpic){
            return false;
        }else {
            subtasks.put(newSubtask.getId(), newSubtask);
            epics.get(foundId).getListOfSubtask().add(newSubtask);
            return true;
        }

    }

    @Override
    public HashMap<Integer, Task> showUpTask() {

        return tasks;


    }

    @Override
    public HashMap<Integer, Epic> showUpEpic() {
        return epics;

    }

    @Override
    public HashMap<Integer, Subtask> showUpSubtask() {

        return subtasks;
    }


    @Override
    public void removeAllTask() {
        tasks.clear();
    }

    @Override
    public void removeAllSubtask() {
        subtasks.clear();
        for (Integer id : epics.keySet()) {
            epics.get(id).getListOfSubtask().clear();
            epics.get(id).checkStatus();
        }
    }

    @Override
    public void removeAllEpic() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void removeTaskById(int id) {

        Task foundTask = tasks.get(id);
        if (foundTask != null) {
            tasks.remove(id);

        }

    }

    @Override
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

    @Override
    public void removeSubtaskById(int id) {
        Subtask foundSubtask = subtasks.get(id);
        if (foundSubtask != null) {

            subtasks.remove(id);
            int idEpic = foundSubtask.getIdEpic();
            epics.get(idEpic).getListOfSubtask().remove(foundSubtask);

        }

    }


    @Override
    public Task searchTaskById(int id) {
        Task foundTask = tasks.get(id);
        if(foundTask != null)
            historyManager.add(foundTask);//тут добавил
        return foundTask;

    }

    @Override
    public Epic searchEpicById(int id) {
        Epic foundEpic = epics.get(id);
        historyManager.add(foundEpic);//тут добавил
        return foundEpic;

    }

    @Override
    public Subtask searchSubtaskById(int id) {
        Subtask foundSubtask = subtasks.get(id);
        historyManager.add(foundSubtask);//тут добавил
        return foundSubtask;

    }

    @Override
    public ArrayList<Subtask> listSubtaskOfEpic(Epic foundEpic) {
        ArrayList<Subtask> listOfSubtasl = foundEpic.getListOfSubtask();
        return listOfSubtasl;


    }

    @Override
    public void upateTask(Task oldTask, Task newTask) {
        int id = oldTask.getId();
        newTask.setId(id);

        tasks.replace(id, newTask);
    }

    @Override
    public void updateSubtask(Subtask oldSubtask, Subtask newSubtask) {
        int id = oldSubtask.getId();
        newSubtask.setId(id);


        subtasks.replace(id, newSubtask);
        epics.get(oldSubtask.getIdEpic()).getListOfSubtask().remove(oldSubtask);
        epics.get(oldSubtask.getIdEpic()).getListOfSubtask().add(newSubtask);
        int idForEpic = oldSubtask.getIdEpic();
        newSubtask.setIdEpic(searchEpicById(idForEpic));
        epics.get(idForEpic).checkStatus();

    }

    @Override
    public void updateEpic(Epic oldEpic, Epic newEpic) {
        int id = oldEpic.getId();
        epics.replace(id, newEpic);
        ArrayList<Subtask> newListOfSubtask = oldEpic.getListOfSubtask();
        newEpic.setListOfSubtask(newListOfSubtask);

        newEpic.checkStatus();
    }
    @Override
    public ArrayList<Task> getHistory() {// добавил этот метод

        return historyManager.getHistory();

    }


}
