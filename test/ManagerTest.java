import manager.HistoryManager;
import manager.Manager;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeAll;
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

    @BeforeAll
    public static void beforeAll() {
        taskManager = Manager.getDefault();
        historyManager = Manager.getDefaultHistory();
    }
    @BeforeEach
    public void beforEach() {
        task1 = new Task("1","1");
        task2 = new Task("2", "2");
        epic1 = new Epic("1","1");
        epic2 = new Epic("2", "2");
        subTask1 = new SubTask("1","1");
        subTask2 = new SubTask("2","2");
    }

    @Test
    public void managersShouldBeNotNull() {
        assertNotNull(taskManager);
        assertNotNull(historyManager);
    }

    @Test
    public void addTask() {
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        assertEquals(task1, taskManager.getTaskById(task1.getId()));
        assertEquals(task2, taskManager.getTaskById(task2.getId()));
        assertNotNull(taskManager.getTasks());

    }

    @Test
    public void addEpic() {
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        assertEquals(epic1, taskManager.getEpicById(epic1.getId()));
        assertEquals(epic2, taskManager.getEpicById(epic2.getId()));
        assertNotNull(taskManager.getEpics());

    }

    @Test
    public void addSubTask() {
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        assertEquals(subTask1,taskManager.getSubTaskById(subTask1.getId()));
        assertEquals(subTask2,taskManager.getSubTaskById(subTask2.getId()));


    }

    @Test
    void addHistory() {
        historyManager.addHistory(task1);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }


    @Test
    public void shouldBeEqualsAfterAdd() {
        taskManager.addTask(task1);
        Task taskT = taskManager.getTaskById(task1.getId());
        assertEquals(task1.getId(), taskT.getId());
        assertEquals(task1.getName(), taskT.getName());
        assertEquals(task1.getDescription(), taskT.getDescription());
        assertEquals(task1.getStatus(), taskT.getStatus());
    }
}
