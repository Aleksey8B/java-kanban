package manager;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    //int getTasksId();

    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubTask(SubTask subTask, Epic epic);

    ArrayList<Task> getAllTasks();

    ArrayList<Task> getTasks();

    ArrayList<Epic> getEpics();

    ArrayList<SubTask> getSubTasks();

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateEpicStatus(Epic epic);

    void updateSubTask(SubTask subTask);

    Task getTaskById(int taskId);

    Epic getEpicById(int epicId);

    SubTask getSubTaskById(int subTaskId);

    ArrayList<SubTask> getEpicsSubTasks(Epic epic);

    void removeTaskById(int taskId);

    void removeEpicById(int epicId);

    void removeSubTaskById(int subTaskId);

    void deleteTasks();

    void deleteEpics();

    void deleteSubTasks();

    List<Task> getHistory();

}
