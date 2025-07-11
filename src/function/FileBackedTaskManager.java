package function;

import type.of.task.Epic;
import type.of.task.Status;
import type.of.task.Subtask;
import type.of.task.Task;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;


public class FileBackedTaskManager extends InMemoryTaskManager {
    private File autoSave;


    FileBackedTaskManager(HistoryManager historyManager, File autoSave) throws ManagerSaveException {
        super(historyManager);
        this.autoSave = autoSave;
    }


    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(autoSave.getPath(), StandardCharsets.UTF_8))) {

            for (Integer id : showUpTask().keySet()) {
                String line = showUpTask().get(id).toString();
                writer.write(line);
                writer.newLine();
            }
            for (Integer id : showUpEpic().keySet()) {
                String line = showUpEpic().get(id).toString();
                writer.write(line);
                writer.newLine();
            }
            for (Integer id : showUpSubtask().keySet()) {
                String line = showUpSubtask().get(id).toString();
                writer.write(line);
                writer.newLine();
            }


        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранение", e);
        }


    }

    public void loadFromFile(File file) {

        try (BufferedReader reader = new BufferedReader(new FileReader(file.getPath(), StandardCharsets.UTF_8))) {
            while (reader.ready()) {

                String line = reader.readLine();
                var task = fromString(line);
                if (task.getClass().equals(Task.class)) {
                    showUpTask().put(task.getId(), task);
                } else if (task.getClass().equals(Epic.class)) {
                    showUpEpic().put(task.getId(), (Epic) task);
                } else {
                    showUpSubtask().put(task.getId(), (Subtask) task);
                }

            }

        } catch (IOException e) {
            throw new ManagerLoadException("Ошибка при сохранение");
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    private Task fromString(String value) throws NotFoundException {
        String[] split = value.split(",");

        int id = Integer.parseInt(split[0]);
        String typeOfTask = split[1];
        String nameOfTask = split[2];
        Status status = Status.valueOf(split[3]);
        String description = split[4];
        LocalDateTime startTime = LocalDateTime.parse(split[5]);
        Duration duration = Duration.parse(split[6]);
        if (split.length == 7) {

            if (typeOfTask.equals("Task")) {
                Task task = new Task(nameOfTask, description, status, (int) duration.toMinutes(), startTime, id);
                return task;

            } else {
                Epic epic = Epic.setEpicForLoading(nameOfTask, description, status,
                        (int) duration.toMinutes(), startTime, id);
                return epic;

            }

        } else {
            int idEpic = Integer.parseInt(split[7]);
            Epic epic = searchEpicById(idEpic);
            Subtask subtask = new Subtask(nameOfTask, description, status,
                    (int) duration.toMinutes(), startTime, id, epic);
            return subtask;
        }


    }

    @Override
    public void addNewSubtask(Subtask newSubtask) throws ConcurrentTaskException, DuplicateTaskException {
        super.addNewSubtask(newSubtask);
        save();
    }

    @Override
    public void addNewEpic(Epic newEpic) throws DuplicateTaskException {
        super.addNewEpic(newEpic);
        save();

    }

    @Override
    public void addNewTask(Task newTask) throws ConcurrentTaskException, DuplicateTaskException {
        super.addNewTask(newTask);
        save();

    }

    @Override
    public void removeAllTask() {
        super.removeAllTask();
        save();
    }

    @Override
    public void removeAllSubtask() {
        super.removeAllSubtask();
        save();
    }

    @Override
    public void removeAllEpic() {
        super.removeAllEpic();
        save();
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void removeEpicById(int id) {
        super.removeEpicById(id);
        save();
    }

    @Override
    public void removeSubtaskById(int id) {
        super.removeSubtaskById(id);
        save();
    }

    @Override
    public void updateEpic(Epic oldEpic, Epic newEpic) {
        super.updateEpic(oldEpic, newEpic);
        save();
    }

    @Override
    public void updateSubtask(Subtask oldSubtask, Subtask newSubtask) throws NotFoundException {
        super.updateSubtask(oldSubtask, newSubtask);
        save();
    }

    @Override
    public void updateTask(Task oldTask, Task newTask) throws NotFoundException {
        super.updateTask(oldTask, newTask);
        save();
    }

}
