package function;

import type.of.task.Epic;
import type.of.task.Subtask;
import type.of.task.Task;

import java.util.*;


public class InMemoryTaskManager implements TaskManager {
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, Subtask> subtasks = new HashMap<>();
    private HistoryManager historyManager;
    private TreeSet<Task> priority = new TreeSet<>(Comparator.comparing(Task::getStartTime));


    InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    @Override
    public void addNewTask(Task newTask) throws ConcurrentTaskException, DuplicateTaskException {
        if (tasks.containsValue(newTask)) {
            throw new DuplicateTaskException();


        } else {
            if (isCrossedAnyTasks(newTask)) {
                throw new ConcurrentTaskException();
            }
            tasks.put(newTask.getId(), newTask);
            if (newTask.getStartTime() != null)
                priority.add(newTask);

        }
    }

    @Override
    public void addNewEpic(Epic newEpic) throws DuplicateTaskException {
        if (epics.containsValue(newEpic)) {
            throw new DuplicateTaskException();
        } else {
            epics.put(newEpic.getId(), newEpic);

        }

    }


    @Override
    public void addNewSubtask(Subtask newSubtask) throws ConcurrentTaskException, DuplicateTaskException {

        int foundId = newSubtask.getIdEpic();

        if (subtasks.containsValue(newSubtask)) {
            throw new DuplicateTaskException();
        } else {
            if (isCrossedAnyTasks(newSubtask)) {
                throw new ConcurrentTaskException();
            }
            Epic epic = epics.get(foundId);
            subtasks.put(newSubtask.getId(), newSubtask);
            epic.getListOfSubtask().add(newSubtask);
            if (epic.getStartTime() != null && priority.contains(epic)) {
                priority.remove(epic);
                epic.calculateStartAndDuration();
                priority.add(epic);
            } else {
                epic.calculateStartAndDuration();
                priority.add(epic);
            }
        }

    }

    @Override
    public Map<Integer, Task> showUpTask() {

        return tasks;


    }

    @Override
    public Map<Integer, Epic> showUpEpic() {
        return epics;

    }

    @Override
    public Map<Integer, Subtask> showUpSubtask() {

        return subtasks;
    }


    @Override
    public void removeAllTask() {
        for (int id : tasks.keySet()) {
            historyManager.remove(id);
            priority.remove(tasks.remove(id));
        }
        tasks.clear();
    }

    @Override
    public void removeAllSubtask() {
        for (int id : subtasks.keySet()) {
            historyManager.remove(id);
        }
        subtasks.clear();
        for (Integer id : epics.keySet()) {
            priority.remove(epics.get(id));
            epics.get(id).getListOfSubtask().clear();
            epics.get(id).checkStatus();
            epics.get(id).calculateStartAndDuration();
        }
    }

    @Override
    public void removeAllEpic() {
        for (int id : epics.keySet()) {
            priority.remove(epics.get(id));
            historyManager.remove(id);
        }
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void removeTaskById(int id) {

        Task foundTask = tasks.get(id);
        if (foundTask != null) {
            historyManager.remove(id);
            tasks.remove(id);
            priority.remove(foundTask);

        }

    }

    @Override
    public void removeEpicById(int id) {
        Epic foundEpic = epics.get(id);
        if (foundEpic != null) {
            historyManager.remove(id);
            epics.remove(id);
            priority.remove(foundEpic);
            Iterator<Subtask> iterator = subtasks.values().iterator();
            while (iterator.hasNext()) {
                Subtask subtask = iterator.next();
                if (subtask.getIdEpic() == id) {
                    historyManager.remove(subtask.getId());
                    iterator.remove();
                }
            }


        }
    }

    @Override
    public void removeSubtaskById(int id) {
        Subtask foundSubtask = subtasks.get(id);
        if (foundSubtask != null) {
            historyManager.remove(id);
            subtasks.remove(id);
            int idEpic = foundSubtask.getIdEpic();
            Epic epic = epics.get(idEpic);
            epic.getListOfSubtask().remove(foundSubtask);
            epic.checkStatus();
            priority.remove(epic);
            epic.calculateStartAndDuration();
            if (epic.getStartTime() != null && epic.getDuration() != null) {
                priority.add(epic);
            }


        }

    }


    @Override
    public Task searchTaskById(int id) throws NotFoundException {
        Task foundTask = tasks.get(id);
        if (foundTask == null) {
            throw new NotFoundException();

        }
        historyManager.add(foundTask);
        return foundTask;


    }

    @Override
    public Epic searchEpicById(int id) throws NotFoundException {
        Epic foundEpic = epics.get(id);
        if (foundEpic == null) {
            throw new NotFoundException();

        }
        historyManager.add(foundEpic);
        return foundEpic;

    }

    @Override
    public Subtask searchSubtaskById(int id) throws NotFoundException {
        Subtask foundSubtask = subtasks.get(id);
        if (foundSubtask == null) {
            throw new NotFoundException();

        }
        historyManager.add(foundSubtask);
        return foundSubtask;

    }

    @Override
    public Collection<Subtask> listSubtaskOfEpic(Epic foundEpic) {
        return foundEpic.getListOfSubtask();
    }

    @Override
    public void updateTask(Task oldTask, Task newTask) throws NotFoundException {
        int id = oldTask.getId();
        newTask.setId(id);
        tasks.replace(id, newTask);
    }

    @Override
    public void updateSubtask(Subtask oldSubtask, Subtask newSubtask) throws NotFoundException {
        int id = oldSubtask.getId();
        newSubtask.setId(id);
        subtasks.replace(id, newSubtask);
        epics.get(oldSubtask.getIdEpic()).getListOfSubtask().remove(oldSubtask);
        epics.get(oldSubtask.getIdEpic()).getListOfSubtask().add(newSubtask);
        int idForEpic = oldSubtask.getIdEpic();
        newSubtask.setIdEpic(searchEpicById(idForEpic));
        epics.get(idForEpic).checkStatus();
        epics.get(idForEpic).calculateStartAndDuration();

    }

    @Override
    public void updateEpic(Epic oldEpic, Epic newEpic) {
        int id = oldEpic.getId();
        epics.replace(id, newEpic);
        Collection<Subtask> newListOfSubtask = oldEpic.getListOfSubtask();
        newEpic.setListOfSubtask(newListOfSubtask);
        newEpic.checkStatus();
        newEpic.calculateStartAndDuration();
    }

    @Override
    public Collection<Task> getHistory() {

        return historyManager.getHistory();

    }

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        return priority;
    }

    public boolean isCrossedTask(Task task1, Task task2) {

        if (task1.getStartTime().isBefore(task2.getStartTime())) {
            return task1.getEndTime().isAfter(task2.getStartTime());
        } else {
            return task2.getEndTime().isAfter(task1.getStartTime());
        }

    }

    public boolean isCrossedAnyTasks(Task task1) {

        if (priority.isEmpty()) {
            return false;
        }
        return priority.stream()
                .anyMatch(task -> isCrossedTask(task1, task));


    }

    @Override
    public Epic getEpicById(int id) throws NotFoundException {
        Epic foundEpic = epics.get(id);
        if (foundEpic == null) {
            throw new NotFoundException();
        }
        return foundEpic;
    }

    @Override
    public Task getTaskById(int id) throws NotFoundException {
        Task foundTask = tasks.get(id);
        if (foundTask == null) {
            throw new NotFoundException();

        }
        historyManager.add(foundTask);
        return foundTask;

    }

    @Override
    public Subtask getSubtaskById(int id) throws NotFoundException {
        Subtask foundSubtask = subtasks.get(id);
        if (foundSubtask == null) {
            throw new NotFoundException();

        }
        historyManager.add(foundSubtask);
        return foundSubtask;
    }
}



