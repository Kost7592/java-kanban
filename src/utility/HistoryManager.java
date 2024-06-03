package utility;

import modules.Task;

import java.util.List;

public interface HistoryManager {

    void addView(Task task);

    void remove (int id);

    List<Task> getHistory();
}

