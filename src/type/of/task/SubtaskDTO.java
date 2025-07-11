package type.of.task;


public class SubtaskDTO extends TaskDTO {


    public int idEpic;


    public static SubtaskDTO convertToDTO(Subtask subtask) {
        SubtaskDTO dto = new SubtaskDTO();
        dto.typeOfTask = subtask.getClass().getSimpleName();
        dto.id = subtask.getId();
        dto.name = subtask.getName();
        dto.description = subtask.getDescription();
        dto.startTime = subtask.getStartTime();
        dto.duration = subtask.getDuration();
        dto.status = subtask.getStatus();
        dto.idEpic = subtask.getIdEpic();
        return dto;


    }
}
