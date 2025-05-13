package function;

import TypeOfTask.Task;


import java.util.Collection;

public interface HistoryManager {
    void add(Task task) ;
    Collection<Task> getHistory();
    void remove(int id);




}
