package type.of.task;

import java.util.ArrayList;
import java.util.Collection;

public class Epic extends Task {

    private Collection<Subtask> listOfSubtask;


    private Status status;


    public Epic(String nameOfEpic, String description) {
        super(nameOfEpic, description);
        status = Status.NEW;
        listOfSubtask = new ArrayList<>();
    }

    public Epic(String nameOfEpic, String description, int id) {
        super(nameOfEpic, description);
        status = Status.NEW;
        listOfSubtask = new ArrayList<>();
        this.setId(id);
    }

    private Epic(String nameOFTask, String description, Status status, int taskId) {
        super(nameOFTask, description, status, taskId);
        listOfSubtask = new ArrayList<>();


    }

    public static Epic setEpicForLoading(String name, String description, Status status, int id) {
        return new Epic(name, description, status, id);
    }

    @Override
    public void setStatus(Status status) {
        System.out.println("Нельзя менять статус Epic'a");
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    public Collection<Subtask> getListOfSubtask() {
        return listOfSubtask;
    }

    public void setListOfSubtask(Collection<Subtask> listOfSubtask) {
        this.listOfSubtask = listOfSubtask;
    }


    public void checkStatus() {
        Collection<Subtask> checkList = this.listOfSubtask;
        int size = checkList.size();


        if (size == 0) {

            this.status = Status.NEW;
            return;

        }
        int doneOfStatus = 0;
        int newOfStatus = 0;

        for (Subtask subtask : checkList) {

            if (subtask.getStatus().equals(Status.DONE)) {
                doneOfStatus++;
            }
            if (subtask.getStatus().equals(Status.NEW)) {
                newOfStatus++;

            }

        }
        if (doneOfStatus == size) {
            this.status = Status.DONE;
        } else if (newOfStatus == size) {
            this.status = Status.NEW;
        } else {
            this.status = Status.IN_PROGRESS;

        }


    }
}
