package function;

import TypeOfTask.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List<Task> history = new ArrayList<>();
    private static final  int SIZE_OF_LIST = 10;
    @Override
    public void add(Task task) {
        if (history.size() == SIZE_OF_LIST) {
            history.removeFirst();
        }
        history.add(task);

    }
    @Override
    public List<Task> getHistory(){

        return history;
    }
}
