import manager.FileBackedTaskManager;
import tasks.*;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

import constant.*;

public class FileBackedTaskManagerTest {
    private FileBackedTaskManager manager;
    private FileBackedTaskManager manager2;
    private File file;

    @BeforeEach
    void beforeEach() throws IOException {
        file = File.createTempFile(CSVConstant.FILE_NAME, CSVConstant.FILE_EXTENSION);
    }

    @Test
    void shouldAddAndSaveAndLoad() {
        manager = new FileBackedTaskManager(file);

        Task task1 = new Task("Task1", "Task1 description");
        Task task2 = new Task("Task2", "Task2 description");
        Task task3 = new Task("Task3", "Task3 description");
        Epic epic1 = new Epic("Epic1", "Epic1 description");
        Epic epic2 = new Epic("Epic2", "Epic2 description");
        SubTask subTask1 = new SubTask("SubTask1", "SubTask1 description");
        SubTask subTask2 = new SubTask("SubTask2", "SubTask2 description");
        SubTask subTask3 = new SubTask("SubTask2", "SubTask2 description");

        manager.addTask(task1);
        manager.addTask(task2);
        manager.addEpic(epic1);
        subTask1.setEpicId(epic1);
        subTask2.setEpicId(epic1);
        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        epic1.addSubTask(subTask1);
        epic1.addSubTask(subTask2);
        manager.updateEpic(epic1);
        manager.updateSubTask(subTask1);
        manager.updateSubTask(subTask2);
        manager.addEpic(epic2);
        subTask3.setEpicId(epic2);
        manager.addSubTask(subTask3);
        epic2.addSubTask(subTask3);
        manager.updateEpic(epic2);
        manager.updateSubTask(subTask3);

        manager2 = FileBackedTaskManager.loadFromFile(file);
        final List<Task> taskList1 = manager.getTasks();
        final List<Task> taskList2 = manager2.getTasks();
        final List<Epic> epicList1 = manager.getEpics();
        final List<Epic> epicList2 = manager2.getEpics();
        final List<SubTask> subTaskList1 = manager.getSubTasks();
        final List<SubTask> subTaskList2 = manager2.getSubTasks();
        manager.addTask(task3);
        manager2.addTask(task3);
        assertEquals(taskList1, taskList2);
        assertEquals(epicList1, epicList2);
        assertEquals(subTaskList1, subTaskList2);
        assertEquals(manager.getTasks().getLast().getId(), manager2.getTasks().getLast().getId());
    }

}
