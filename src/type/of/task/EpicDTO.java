package type.of.task;


public class EpicDTO extends TaskDTO {


    public static EpicDTO convertToDTO(Epic epic) {
        EpicDTO dto = new EpicDTO();
        dto.typeOfTask = epic.getClass().getSimpleName();
        dto.id = epic.getId();
        dto.name = epic.getName();
        dto.description = epic.getDescription();
        dto.startTime = epic.getStartTime();
        dto.duration = epic.getDuration();
        dto.status = epic.getStatus();
        return dto;
    }


}
