package function;

import type.of.task.Epic;
import type.of.task.Status;
import type.of.task.Subtask;
import type.of.task.Task;


import java.io.*;
import java.nio.charset.StandardCharsets;


public class FileBackedTaskManager extends InMemoryTaskManager {
    private File autoSave;

    FileBackedTaskManager(HistoryManager historyManager, File autoSave) throws ManagerSaveException {
        super(historyManager);
        this.autoSave = autoSave;
        if (!autoSave.exists()) {
            try {
                autoSave.createNewFile();

            } catch (IOException e) {
                throw new ManagerSaveException("Ошибка при сохранение", e);
            }
        }
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


        } catch (FileNotFoundException e) {
            throw new ManagerLoadException("Ошибка при сохранение");
        } catch (IOException e) {
            throw new ManagerLoadException("Ошибка при сохранение");
        }

    }


    private Task fromString(String value) {
        String[] split = value.split(",");

        int id = Integer.parseInt(split[0]);
        String typeOfTask = split[1];
        String nameOfTask = split[2];
        Status status = Status.toStatusFromString(split[3]);
        String description = split[4];
        if (split.length == 5) {

            if (typeOfTask.equals("Task")) {
                Task task = new Task(nameOfTask, description, status, id);
                return task;

            } else {
                Epic epic = Epic.setEpicForLoading(nameOfTask, description, status, id);
                return epic;

            }

        } else {
            int idEpic = Integer.parseInt(split[5]);
            Epic epic = searchEpicById(idEpic);
            Subtask subtask = new Subtask(nameOfTask, description, status, id, epic);
            return subtask;
        }


    }

    @Override
    public boolean addNewSubtask(Subtask newSubtask) {
        boolean flag = super.addNewSubtask(newSubtask);
        save();
        return flag;
    }

    @Override
    public boolean addNewEpic(Epic newEpic) {
        boolean flag = super.addNewEpic(newEpic);

        save();
        return flag;
    }

    @Override
    public boolean addNewTask(Task newTask) {
        boolean flag = super.addNewTask(newTask);
        save();
        return flag;
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
    public void updateSubtask(Subtask oldSubtask, Subtask newSubtask) {
        super.updateSubtask(oldSubtask, newSubtask);
        save();
    }

    @Override
    public void updateTask(Task oldTask, Task newTask) {
        super.updateTask(oldTask, newTask);
        save();
    }

    public static void main(String[] args) throws IOException {
        String nameOfFile = "records";
        File file = File.createTempFile(nameOfFile, ".csv");
        FileBackedTaskManager manager = new FileBackedTaskManager(Managers.getDefaultHistory(), file);
        manager.save();
        manager.loadFromFile(file);
        Task task1 = new Task("task1", "descriptionForTask1", Status.NEW, 1);
        Task task2 = new Task("task2", "descriptionForTask2", Status.NEW, 2);
        Epic epic1 = new Epic("epic1", "descriptionForEpic1 ", 3);
        Epic epic2 = new Epic("epic2", "descriptionForEpic2 ", 4);
        Subtask subtask1 = new Subtask("subtask1", "descriptionForSubtask1", Status.NEW,
                5, epic1);
        Subtask subtask2 = new Subtask("subtask2", "descriptionForSubtask2", Status.NEW,
                6, epic1);
        Subtask subtask3 = new Subtask("subtask3", "descriptionForSubtask3", Status.NEW,
                7, epic2);
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        manager.addNewEpic(epic1);
        manager.addNewEpic(epic2);
        manager.addNewSubtask(subtask1);
        manager.addNewSubtask(subtask2);
        manager.addNewSubtask(subtask3);
        FileBackedTaskManager managerCheck = new FileBackedTaskManager(Managers.getDefaultHistory(), file);
        managerCheck.loadFromFile(file);
        System.out.println(managerCheck.showUpTask());
        System.out.println(managerCheck.showUpEpic());
        System.out.println(managerCheck.showUpSubtask());
        System.out.println("-----");
        managerCheck.removeEpicById(3);
        FileBackedTaskManager managerCheck3 = new FileBackedTaskManager(Managers.getDefaultHistory(), file);
        managerCheck3.loadFromFile(file);
        System.out.println(managerCheck.showUpTask());
        System.out.println(managerCheck.showUpEpic());
        System.out.println(managerCheck.showUpSubtask());


    }


}
