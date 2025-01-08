package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private static class Node {
        Task task;
        Node prev;
        Node next;

        public Node(Task task, Node prev, Node next) {
            this.task = task;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node first;
    private Node last;
    private int historySize = 0;
    private Map<Integer, Node> historyMap;

    public InMemoryHistoryManager() {
        historyMap = new HashMap<>();
    }

    @Override
    public void addHistory(Task task) {
        if (task == null) return;
        removeNode(historyMap.get(task.getId()));
        addToEnd(task);
    }

    @Override
    public void remove(int id) {
        removeNode(historyMap.remove(id));
    }

    @Override
    public List<Task> getHistory() {
        List<Task> historyView = new ArrayList<>();
        Node currentNode = first;
        while (currentNode != null) {
            historyView.add(currentNode.task);
            currentNode = currentNode.next;
        }
        return historyView;
    }

    private void addToEnd(Task task) {
        final Node lastNode = last;
        final Node newNode = new Node(task, lastNode, null);
        last = newNode;

        if (first == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }

        historySize++;
        historyMap.put(task.getId(), newNode);
    }

    private void removeNode(Node node) {
        if (node == null || historySize == 0) return;

        Node prevNode = node.prev;
        Node nextNode = node.next;

        if (prevNode == null) {
            first = nextNode;
            nextNode.prev = null;
        } else if (nextNode == null) {
            last = prevNode;
            prevNode.next = null;
        } else {
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }
        historySize--;
    }



}
