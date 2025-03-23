package manager;

public class Node<Task> {
    private final Task task;
    private Node<Task> prev;
    private Node<Task> next;

    public Node(Task task, Node<Task> prev, Node<Task> next) {
        this.task = task;
        this.prev = prev;
        this.next = next;
    }

    public Task getCurrent(){
        return task;
    }

    public Node<Task> getNext() {
        return next;
    }

    public Node<Task> getPrev() {
        return prev;
    }

    public void setNext(Node<Task> next) {
        this.next = next;
    }

    public void setPrev(Node<Task> prev) {
        this.prev = prev;
    }
}