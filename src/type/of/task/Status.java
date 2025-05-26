package type.of.task;


import function.WrongStatusExcaption;

public enum Status {
    NEW,
    IN_PROGRESS,
    DONE;

    public static Status toStatusFromString(String status) {
        return switch (status) {
            case "NEW" -> NEW;
            case "IN_PROGRESS" -> IN_PROGRESS;
            case "DONE" -> DONE;
            default -> throw new WrongStatusExcaption("Такого статуса задачи не существует");
        };

    }
}


