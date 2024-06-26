package utility;

import modules.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager  {
    private final Map<Integer, Node> nodes = new HashMap<>();
    private Node head;
    private Node tail;

    @Override
    public void addView(Task task) { //добавление просмотра в историю
        Task taskCopy = new Task(task.getName(), task.getDescription());
        taskCopy.setId(task.getId());
        if (nodes.containsKey(taskCopy.getId())) {
            Node node = nodes.remove(taskCopy.getId());
            removeNode(node);
        }
        linkLast(taskCopy);
        nodes.put(taskCopy.getId(), tail);
    }

    @Override
    public void remove(int id) { //удаление просмотра из истории
        if (nodes.containsKey(id)) {
            Node node = nodes.get(id);
            nodes.remove(id);
            removeNode(node);
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    } // получение списка истории

    private void linkLast(Task task) { //добавление задачи в конец списка
        Node oldTail = tail;
        Node newNode = new Node(oldTail,task,null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.setNext(newNode);
        }
        int id = task.getId();
        nodes.put(id, newNode);
    }

    private List<Task> getTasks() { //реализация логики получения списка истории
        List<Task> taskList = new ArrayList<>();
        Node node = head;
        while (node != null) {
            taskList.add(node.getTask());
            node = node.getNext();
        }
        return taskList;
    }

    private void removeNode(Node node) { //релаизация логики удаления записи из истории
        Node next = node.getNext();
        Node prev = node.getPrev();
        if (prev == null) {
            head = next;
        } else {
            prev.setNext(next);
            node.setPrev(null);
        }
        if (next == null) {
            tail = prev;
        } else {
            next.setPrev(prev);
            node.setNext(null);
        }
        node.setTask(null);
    }
}

