package type.of.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Epic extends Task {

    private Collection<Subtask> listOfSubtask;

    public Epic(String nameOfEpic, String description) {
        super(nameOfEpic, description);
        listOfSubtask = new ArrayList<>();
    }

    public Epic(String name, String description, int id) {
        super(name, description, id);
        listOfSubtask = new ArrayList<>();
    }

    private Epic(String nameOFTask, String description, Status status, int taskId) {
        super(nameOFTask, description, status, taskId);
        listOfSubtask = new ArrayList<>();


    }

    private Epic(String nameOFTask, String description, Status status, Integer minutes, LocalDateTime startTime, int taskId) {
        super(nameOFTask, description, status, minutes, startTime, taskId);
        listOfSubtask = new ArrayList<>();
    }

    public static Epic createdEpicWithStatus(String name, String description, Status status, int id) {
        return new Epic(name, description, status, id);
    }

    public static Epic setEpicForLoading(String nameOFTask, String description, Status status, Integer minutes,
                                         LocalDateTime startTime, int taskId) {
        return new Epic(nameOFTask, description, status, minutes, startTime, taskId);
    }

    @Override
    public void setStatus(Status status) {
        throw new IllegalArgumentException();
    }

    @Override
    public Status getStatus() {
        return super.getStatus();
    }

    @Override
    public LocalDateTime getStartTime() {
        return super.getStartTime();
    }

    @Override
    public void setStartTime(LocalDateTime startTime) {
        throw new IllegalArgumentException();
    }

    @Override
    public Duration getDuration() {

        return super.getDuration();
    }

    @Override
    public void setDuration(Duration duration) {
        throw new IllegalArgumentException();
    }
    private void updateDuration(Duration duration){
        super.setDuration(duration);
    }
    private void updateStartTime(LocalDateTime startTime){
        super.setStartTime(startTime);
    }


    @Override
    public LocalDateTime getEndTime() {
        return   listOfSubtask.stream()
                .map(Task::getEndTime)
                .max(LocalDateTime::compareTo)
                .orElse(null);

    }

    public Collection<Subtask> getListOfSubtask() {
        return listOfSubtask;
    }

    public void setListOfSubtask(Collection<Subtask> listOfSubtask) {
        this.listOfSubtask = listOfSubtask;
    }

    public void calculateStartAndDuration() {
        if (listOfSubtask.isEmpty()){
            updateDuration(null);
            updateStartTime(null);
            return;
        }
        listOfSubtask.stream()
                .min((subtask1, subtask2) -> subtask1.getStartTime().compareTo(subtask2.getStartTime()))
                .ifPresent(subtask -> super.setStartTime(subtask.getStartTime()));

        super.setDuration(listOfSubtask.stream()
                .map(subtask -> subtask.getDuration())
                .reduce(Duration.ZERO, Duration::plus));

    }


    public void checkStatus() {
        Collection<Subtask> checkList = this.listOfSubtask;
        int size = checkList.size();


        if (size == 0) {

            super.setStatus(Status.NEW);
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
            super.setStatus(Status.DONE);
        } else if (newOfStatus == size) {
            super.setStatus(Status.NEW);
        } else {
            super.setStatus(Status.IN_PROGRESS);

        }


    }
}
