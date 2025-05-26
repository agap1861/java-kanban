package type.of.task;

public class Subtask extends Task {


    private int idEpic;

    public Subtask(String nameOfSubtask, String description, Epic epic) {
        super(nameOfSubtask, description);
        idEpic = epic.getId();
    }

    public Subtask(String nameOfSubtask, String description, Status status) {
        super(nameOfSubtask, description);
        this.setStatus(status);

    }

    public Subtask(String nameOfSubtask, String description, Status status, int subtaskId) {
        this(nameOfSubtask, description, status);
        this.setId(subtaskId);

    }

    public Subtask(String nameOfSubtask, String description, Status status, int subtaskId, Epic epic) {
        super(nameOfSubtask, description, status);
        this.setId(subtaskId);
        idEpic = epic.getId();

    }

    public int getIdEpic() {
        return idEpic;
    }

    @Override
    public String toString() {
        return this.getId() + "," + this.getClass().getSimpleName() + "," + this.getName() + "," + this.getStatus() + "," +
                this.getDescription() + "," + this.getIdEpic();
    }

    public void setIdEpic(Epic epic) {
        this.idEpic = epic.getId();
    }

}
