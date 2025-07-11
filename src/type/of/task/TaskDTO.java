package type.of.task;

import java.time.Duration;
import java.time.LocalDateTime;


public class TaskDTO {
    public String typeOfTask;
    public int id;
    public String name;
    public String description;
    public Status status;
    public Duration duration;
    public LocalDateTime startTime;

    public static TaskDTO convertToDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.typeOfTask = task.getClass().getSimpleName();
        dto.id = task.getId();
        dto.name = task.getName();
        dto.description = task.getDescription();
        dto.startTime = task.getStartTime();
        dto.duration = task.getDuration();
        dto.status = task.getStatus();
        return dto;
    }
}
