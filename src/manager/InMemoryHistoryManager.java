package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> historyMap = new HashMap<>();
    private Node first;
    private Node last;

    @Override
    public void add(Task task) {
        if (task == null) return;
        removeNode(historyMap.get(task.getId()));
        historyMap.remove(task.getId());
        linkLast(task);
    }

    @Override
    public void remove(int id) {
        removeNode(historyMap.get(id));
        historyMap.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    private List<Task> getTasks() {
        List<Task> historyView = new ArrayList<>();
        Node currentNode = first;
        while (currentNode != null) {
            historyView.add(currentNode.getCurrent());
            currentNode = currentNode.getNext();
        }
        return historyView;
    }

    private void linkLast(Task task) {
        final Node lastNode = last;
        final Node newNode = new Node(task, lastNode, null);
        last = newNode;

        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.setNext(newNode);
        }
        historyMap.put(task.getId(), newNode);
    }

    private void removeNode(Node node) {
        if (historyMap.containsValue(node)) {
            if (node == first) {
                first = node.getNext();
                node.getNext().setPrev(null);
            }
            if (node == last) {
                last = node.getPrev();
                node.getPrev().setNext(null);
            }
            if (node.getPrev() != null) node.getPrev().setNext(node.getNext());
            if (node.getNext() != null) node.getNext().setPrev(node.getPrev());
        }
    }



}
