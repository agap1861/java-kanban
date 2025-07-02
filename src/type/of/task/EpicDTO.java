package type.of.task;

import java.util.List;
import java.util.Optional;

public class EpicDTO extends TaskDTO {

 /*   public static EpicDTO convertToDTONew(Epic epic) {
        EpicDTO dto = new EpicDTO();
        dto.id = epic.getId();
        dto.name = epic.getName();
        dto.description = epic.getDescription();

        return dto;
    }*/

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
