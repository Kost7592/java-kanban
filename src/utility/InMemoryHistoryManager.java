package utility;
import modules.Task;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager  {

    private final List<Node> viewHistory = new LinkedList<>();
    private Map<Integer, Node> nodes = new HashMap<>();
    private Node head;
    private Node tail;

    @Override
    public void addView(Task task) {
        int taskId = task.getId();
        if(nodes.containsKey(taskId)) {

        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Node> getHistory() {
        return viewHistory;
    }

}

