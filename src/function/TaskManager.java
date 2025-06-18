package function;

import type.of.task.Epic;
import type.of.task.Subtask;
import type.of.task.Task;

import java.util.Collection;
import java.util.Map;
import java.util.TreeSet;

public interface TaskManager {
    boolean addNewTask(Task newTask);

    boolean addNewEpic(Epic newEpic);

    boolean addNewSubtask(Subtask newSubtask);

    Map<Integer, Task> showUpTask();

    Map<Integer, Epic> showUpEpic();

    Map<Integer, Subtask> showUpSubtask();

    void removeAllTask();

    void removeAllSubtask();

    void removeAllEpic();

    void removeTaskById(int id);

    void removeEpicById(int id);

    void removeSubtaskById(int id);

    Task searchTaskById(int id);

    Epic searchEpicById(int id);

    Subtask searchSubtaskById(int id);

    void updateTask(Task oldTask, Task newTask);

    void updateSubtask(Subtask oldSubtask, Subtask newSubtask);

    void updateEpic(Epic oldEpic, Epic newEpic);

    Collection<Subtask> listSubtaskOfEpic(Epic foundEpic);

    Collection<Task> getHistory();

    TreeSet<Task> getPrioritizedTasks();

    boolean isCrossedTask(Task task1, Task task2);

    boolean isCrossedAnyTasks(Task task1);


}
