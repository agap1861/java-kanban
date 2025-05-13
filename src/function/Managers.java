package function;

public class Managers {


    public static TaskManager getDefault() {
        return new InMemoryTaskManager(getDefaultHistory());
    }

    static public HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
