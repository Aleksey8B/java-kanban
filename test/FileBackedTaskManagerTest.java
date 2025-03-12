import manager.FileBackedTaskManager;
import tasks.*;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import constant.*;

public class FileBackedTaskManagerTest {
    private FileBackedTaskManager manager;
    private File file;

    @BeforeEach
    void beforeEach() throws IOException {
        file = File.createTempFile(CSVConstant.FILE_NAME, CSVConstant.FILE_EXTENSION);
        manager = new FileBackedTaskManager(file);
    }

    @AfterEach
    void afterEach() {
        file.delete();
        manager.deleteTasks();
        manager.deleteEpics();
        manager.deleteSubTasks();
    }

    @Test
    void shouldAddAndSaveTask() {
        Task task = new Task("Task", "Task description");
        manager.addTask(task);

        List<Task> tasks = manager.getTasks();
        assertEquals(1, tasks.size());
        assertEquals(task, tasks.getFirst());

        manager = FileBackedTaskManager.loadFromFile(file);
        tasks = manager.getTasks();
        assertEquals(1, tasks.size());
        assertEquals(task, tasks.getFirst());
    }

    @Test
    void shouldAddAndSaveEpic() {
        Epic epic = new Epic("Epic", "Epic description");
        SubTask subTask = new SubTask("SubTask", "SubTask description");
        SubTask subTask1 = new SubTask("SubTask1", "SubTask1 description");
        epic.addSubTask(subTask);
        epic.addSubTask(subTask1);
        manager.addEpic(epic);

        List<Epic> epics = manager.getEpics();
        assertEquals(1, epics.size());
        assertEquals(epic, epics.getFirst());

        manager = FileBackedTaskManager.loadFromFile(file);
        epics = manager.getEpics();
        assertEquals(1, epics.size());
        assertEquals(epic, epics.getFirst());
    }

    @Test
    void shouldAddAndSaveSubTask() {
        Epic epic = new Epic("Epic", "Epic description");
        SubTask subTask = new SubTask("SubTask", "SubTask description");

        manager.addEpic(epic);
        manager.addSubTask(subTask);
        epic.addSubTask(subTask);
        subTask.setEpicId(epic);
        manager.updateEpic(epic);
        manager.updateSubTask(subTask);

        List<SubTask> subTasks = manager.getSubTasks();
        assertEquals(1, subTasks.size());
        assertEquals(subTask, subTasks.getFirst());

        manager = FileBackedTaskManager.loadFromFile(file);
        subTasks = manager.getSubTasks();
        assertEquals(1, subTasks.size());
        assertEquals(subTask, subTasks.getFirst());
    }
}
