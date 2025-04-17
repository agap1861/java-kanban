package TypeOfTask;

import java.util.Objects;

public class Task {


    private int id;
    private String name;


    private Status status;
    private String description;

    public Task(String nameOfTask, String description) {
        this.name = nameOfTask;
        this.description = description;
        this.status = Status.NEW;
        this.id = CreateNewId();
    }

    public Task(String nameOFTask, String description, Status status) {
        this.name = nameOFTask;
        this.description = description;
        this.status = status;
    }

    private static int currentId = 0;

    private static int CreateNewId() {
        currentId++;
        return currentId;
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
        String result = "name={" + this.name + "} id={" + this.id + "} description={" + this.description + "} Status={" +
                this.status + "}";
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
