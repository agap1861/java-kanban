package type.of.task;

public enum Status {
    NEW,
    IN_PROGRESS,
    DONE;

    public static Status toStatusFromString(String status) {
        return switch (status) {
            case "NEW" -> NEW;
            case "IN_PROGRESS" -> IN_PROGRESS;
            default -> DONE;
        };

    }
}


