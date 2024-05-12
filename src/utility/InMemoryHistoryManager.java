package utility;
import modules.Task;

import java.util.List;
import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager  {

    private static final int HISTORY_LENGTH = 10;

    private final List<Task> viewHistory = new ArrayList<>();

    @Override
    public void add(Task task) {
        viewHistory.add(task);
        if(viewHistory.size() > HISTORY_LENGTH) {
                viewHistory.removeFirst();
        }
    }

    @Override
    public List<Task> getHistory() {
        return viewHistory;
    }

}

