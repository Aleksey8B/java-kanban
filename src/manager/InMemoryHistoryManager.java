package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{

    private final int HISTORY_CAPACITY;
    private final List<Task> historyList;

    public InMemoryHistoryManager() {
        HISTORY_CAPACITY = 10;
        historyList = new ArrayList<>(HISTORY_CAPACITY);
    }
    @Override
    public void addHistory(Task task) {
        if (historyList.size() < HISTORY_CAPACITY) {
            historyList.add(task);
        } else {
            historyList.remove(0);
            historyList.add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(historyList);
    }
}
