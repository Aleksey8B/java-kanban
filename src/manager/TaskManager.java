package manager;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.List;

public interface TaskManager {

    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubTask(SubTask subTask);

    List<Task> getAllTasks();

    List<Task> getTasks();

    List<Epic> getEpics();

    List<SubTask> getSubTasks();

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateEpicStatus(Epic epic);

    void updateSubTask(SubTask subTask);

    Task getTaskById(int taskId);

    Epic getEpicById(int epicId);

    SubTask getSubTaskById(int subTaskId);

    List<SubTask> getEpicsSubTasks(Epic epic);

    void removeTaskById(int taskId);

    void removeEpicById(int epicId);

    void removeSubTaskById(int subTaskId);

    void deleteTasks();

    void deleteEpics();

    void deleteSubTasks();

    List<Task> getHistory();

}
