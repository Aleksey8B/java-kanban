
import Manager.TaskManager;
import Tasks.Epic;
import Tasks.SubTask;
import Tasks.Task;
import Tasks.TaskStatus;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task("Task 1", "Task 1 description");
        Task task2 = new Task("Task 2", "Task 2 description");
        Epic epic1 = new Epic("Epic 1", "Epic 1 description");
        Epic epic2 = new Epic("Epic 2", "Epic 2 description");
        SubTask subTask1 =  new SubTask("SubTask 1", "SubTask 1 description");
        SubTask subTask2 =  new SubTask("SubTask 2", "SubTask 2 description");
        SubTask subTask3 =  new SubTask("SubTask 3", "SubTask 3 description");

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.addSubTask(subTask1,epic1);
        taskManager.addSubTask(subTask2,epic1);
        taskManager.addSubTask(subTask3,epic2);

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubTasks());

        taskManager.updateTaskStatus(task1, TaskStatus.DONE);
        taskManager.updateSubTaskStatus(subTask1,TaskStatus.DONE);
        taskManager.updateSubTaskStatus(subTask1,TaskStatus.IN_PROGRESS);
        taskManager.updateSubTaskStatus(subTask2,TaskStatus.IN_PROGRESS);
        taskManager.updateSubTaskStatus(subTask2,TaskStatus.DONE);
        taskManager.updateSubTaskStatus(subTask3,TaskStatus.DONE);
        System.out.println(taskManager.getEpics());
        taskManager.removeTaskById(task1.getId());
        taskManager.removeSubTaskById(subTask3.getId());
        taskManager.removeEpicById(epic1.getId());

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubTasks());

    }
}
