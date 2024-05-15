package utility;

import modules.Task;

import java.util.List;

public interface HistoryManager {

    void addView(Task task);

    List<Task> getHistory();
}

