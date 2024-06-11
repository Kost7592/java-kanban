package utility;

import modules.Task;

public class Node { //узел двусвязного списка
    public Node(Node prev, Task task, Node next) {
        this.prev = prev;
        this.task = task;
        this.next = next;
    }

    private Node prev; // предыдущая задача
    private Task task; // текущая задача
    private Node next; //следующая задача

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
