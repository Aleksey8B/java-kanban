import manager.HistoryManager;
import manager.Manager;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerTest {
    static TaskManager taskManager;
    static HistoryManager historyManager;
    Task task1;
    Task task2;
    Epic epic1;
    Epic epic2;
    SubTask subTask1;
    SubTask subTask2;

    @BeforeEach
    public void beforeEach() {
        taskManager = Manager.getDefault();
        historyManager = Manager.getDefaultHistory();
        task1 = new Task("1","1");
        taskManager.addTask(task1);
        task2 = new Task("2", "2");
        taskManager.addTask(task2);
        epic1 = new Epic("1","1");
        taskManager.addEpic(epic1);
        epic2 = new Epic("2", "2");
        taskManager.addEpic(epic2);
        subTask1 = new SubTask("1","1");
        subTask1.setEpicId(epic1);
        taskManager.addSubTask(subTask1);
        subTask2 = new SubTask("2","2");
        subTask2.setEpicId(epic1);
        taskManager.addSubTask(subTask2);
    }

    @Test
    public void managersShouldBeNotNull() {
        assertNotNull(taskManager);
        assertNotNull(historyManager);
    }

    @Test
    public void addTask() {
        assertEquals(task1, taskManager.getTaskById(task1.getId()));
        assertEquals(task2, taskManager.getTaskById(task2.getId()));
        assertNotNull(taskManager.getTasks());

    }

    @Test
    public void addEpic() {
        assertEquals(epic1, taskManager.getEpicById(epic1.getId()));
        assertEquals(epic2, taskManager.getEpicById(epic2.getId()));
        assertNotNull(taskManager.getEpics());

    }

    @Test
    public void addSubTask() {
        assertEquals(subTask1,taskManager.getSubTaskById(subTask1.getId()));
        assertEquals(subTask2,taskManager.getSubTaskById(subTask2.getId()));


    }

    @Test
    public void shouldBeEqualsAfterAdd() {
        Task taskT = taskManager.getTaskById(task1.getId());
        assertEquals(task1.getId(), taskT.getId());
        assertEquals(task1.getName(), taskT.getName());
        assertEquals(task1.getDescription(), taskT.getDescription());
        assertEquals(task1.getStatus(), taskT.getStatus());
    }

    @Test
    void addHistory() {
        historyManager.add(task1);
        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void shouldDeletePrevView() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task1);
        List<Task> history = historyManager.getHistory();
        assertEquals(task1,history.get(1));
        assertEquals(task2,history.get(0));
    }

    @Test
    void shouldDeleteFirstView() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(epic1);

        historyManager.remove(0);

        List<Task> history = historyManager.getHistory();
        assertEquals(task2, history.getFirst());

    }
}
