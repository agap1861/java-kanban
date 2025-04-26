package function;

import TypeOfTask.Epic;
import TypeOfTask.Subtask;
import TypeOfTask.Task;

import java.util.ArrayList;
import java.util.HashMap;

public interface TaskManager {
    //    void addNewTask(Task newTask);
    boolean addNewTask(Task newTask);

    boolean addNewEpic(Epic newEpic);

    boolean addNewSubtask(Subtask newSubtask);

    HashMap<Integer, Task> showUpTask();

    HashMap<Integer, Epic> showUpEpic();

    HashMap<Integer, Subtask> showUpSubtask();

    void removeAllTask();

    void removeAllSubtask();

    void removeAllEpic();

    void removeTaskById(int id);

    void removeEpicById(int id);

    void removeSubtaskById(int id);

    Task searchTaskById(int id);

    Epic searchEpicById(int id);

    Subtask searchSubtaskById(int id);

    void upateTask(Task oldTask, Task newTask);

    void updateSubtask(Subtask oldSubtask, Subtask newSubtask);

    void updateEpic(Epic oldEpic, Epic newEpic);

    ArrayList<Subtask> listSubtaskOfEpic(Epic foundEpic);

    ArrayList<Task> getHistory();


}
