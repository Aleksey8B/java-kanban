package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private static final Map<Integer, Node<Task>> historyMap = new HashMap<>();
    private int historySize = 0;
    private Node<Task> first;
    private Node<Task> last;

    @Override
    public void add(Task task) {
        if (task == null) return;
        removeNode(historyMap.get(task.getId()));
        historyMap.remove(task.getId());
        linkLast(task);
    }

    @Override
    public void remove(int id) {
        removeNode(historyMap.remove(id));
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    private List<Task> getTasks() {
        List<Task> historyView = new ArrayList<>();
        Node<Task> currentNode = first;
        while (currentNode != null) {
            historyView.add(currentNode.getCurrent());
            currentNode = currentNode.getNext();
        }
        return historyView;
    }

    private void linkLast(Task task) {
        final Node<Task> lastNode = last;
        final Node<Task> newNode = new Node<>(task, lastNode, null);
        last = newNode;

        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.setNext(newNode);
        }
        historySize++;
        historyMap.put(task.getId(), newNode);
    }

    private void removeNode(Node<Task> node) {
        if (historyMap.containsValue(node)) {
            if (node == first) {
                first = node.getNext();
                node.getNext().setPrev(null);
            }
            if (node == last) {
                last = node.getPrev();
                node.getPrev().setNext(null);
            }
            if(node.getPrev() != null) node.getPrev().setNext(node.getNext());
            if(node.getNext() != null) node.getNext().setPrev(node.getPrev());
            historySize--;
        }
    }



}
