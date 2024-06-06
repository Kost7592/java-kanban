package utility;

import modules.Task;

public class Node {
    public Node(Node prev, Task task, Node next) {
        this.prev = prev;
        this.task = task;
        this.next = next;
    }

    private Node prev;
    private Task task;
    private Node next;

    public Node getPrev() {
        return prev;
    }

    public Task getTask() {
        return task;
    }

    public Node getNext() {
        return next;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
