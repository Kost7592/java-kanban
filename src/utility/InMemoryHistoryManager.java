package utility;
import modules.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager  {

    private static final int HISTORY_LENGTH = 10;

    private final List<Task> viewHistory = new LinkedList<>();

    @Override
    public void addView(Task task) {
        Task taskCopy = new Task(task.getName(), task.getDescription());
        taskCopy.setId(task.getId());
        viewHistory.add(taskCopy);
        if(viewHistory.size() > HISTORY_LENGTH) {
                viewHistory.removeFirst();
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Task> getHistory() {
        return viewHistory;
    }

}

