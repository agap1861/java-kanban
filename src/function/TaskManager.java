package function;

import type.of.task.Epic;
import type.of.task.Subtask;
import type.of.task.Task;

import java.util.Collection;
import java.util.Map;
import java.util.TreeSet;

public interface TaskManager {
    void addNewTask(Task newTask) throws ConcurrentTaskException, DuplicateTaskException;

    void addNewEpic(Epic newEpic) throws DuplicateTaskException;

    void addNewSubtask(Subtask newSubtask) throws ConcurrentTaskException, DuplicateTaskException;

    Map<Integer, Task> showUpTask();

    Map<Integer, Epic> showUpEpic();

    Map<Integer, Subtask> showUpSubtask();

    void removeAllTask();

    void removeAllSubtask();

    void removeAllEpic();

    void removeTaskById(int id);

    void removeEpicById(int id);

    void removeSubtaskById(int id);

    Task searchTaskById(int id) throws NotFoundException;

    Epic searchEpicById(int id) throws NotFoundException;

    Subtask searchSubtaskById(int id) throws NotFoundException;

    void updateTask(Task oldTask, Task newTask) throws NotFoundException;

    void updateSubtask(Subtask oldSubtask, Subtask newSubtask) throws NotFoundException, ConcurrentTaskException;

    void updateEpic(Epic oldEpic, Epic newEpic) throws NotFoundException;

    Collection<Subtask> listSubtaskOfEpic(Epic foundEpic);

    Collection<Task> getHistory();

    TreeSet<Task> getPrioritizedTasks();

    boolean isCrossedTask(Task task1, Task task2);

    boolean isCrossedAnyTasks(Task task1);

    Epic getEpicById(int id) throws NotFoundException;

    Task getTaskById(int id) throws NotFoundException;

    Subtask getSubtaskById(int id) throws NotFoundException;


}
