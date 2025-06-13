package type.of.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {


    private int id;
    private String name;
    private Status status;
    private String description;
    private Duration duration;
    private LocalDateTime startTime;


    public Task(String nameOfTask, String description, Integer minutes, LocalDateTime startTime) {
        this(nameOfTask, description, Status.NEW, minutes, startTime, createNewId());
    }

    public Task(String nameOfTask, String description) {
        this.name = nameOfTask;
        this.description = description;
        this.status = Status.NEW;
        this.id = createNewId();
    }

    public Task(String nameOfTask, String description, int id) {
        this.name = nameOfTask;
        this.description = description;
        this.status = Status.NEW;
        this.id = id;
    }

    public Task(String nameOfTask, String description, Status status, int taskId) {
        this(nameOfTask, description);
        this.status = status;
        this.id = taskId;
    }

    public Task(String nameOFTask, String description, Integer minutes, LocalDateTime startTime, Status status) {
        this(nameOFTask, description, status, minutes, startTime, createNewId());
    }

    public Task(String nameOFTask, String description, Status status, Integer minutes, LocalDateTime startTime, int taskId) {
        this.name = nameOFTask;
        this.description = description;
        this.status = status;
        this.duration = Duration.ofMinutes(minutes);
        this.startTime = startTime;
        this.id = taskId;
    }


    private static int currentId = 0;

    private static int createNewId() {
        currentId++;
        return currentId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Task task = (Task) object;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public String toString() {
        String result = this.getId() + "," + this.getClass().getSimpleName() + "," + this.getName() + "," + this.getStatus() + "," +
                this.getDescription() + "," + this.getStartTime() + "," + this.getDuration();
        return result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


}