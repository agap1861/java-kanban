package type.of.task;

import java.time.LocalDateTime;

public class Subtask extends Task {


    private int idEpic;

    public Subtask(String nameOfSubtask, String description, Integer minutes, LocalDateTime startTime, Epic epic) {
        super(nameOfSubtask, description, minutes, startTime);
        idEpic = epic.getId();
    }

    public Subtask(String nameOfSubtask, String description, Status status, Integer minutes, LocalDateTime startTime,
                   int subtaskId, Epic epic) {
        super(nameOfSubtask, description, status, minutes, startTime, subtaskId);
        idEpic = epic.getId();

    }

    public int getIdEpic() {
        return idEpic;
    }

    @Override
    public String toString() {
        return this.getId() + "," + this.getClass().getSimpleName() + "," + this.getName() + "," + this.getStatus() + "," +
                this.getDescription() + "," + this.getStartTime() + "," + this.getDuration() + "," + this.getIdEpic();
    }

    public void setIdEpic(Epic epic) {
        this.idEpic = epic.getId();
    }

}
