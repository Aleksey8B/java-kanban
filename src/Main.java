import manager.Manager;
import manager.TaskManager;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskStatus;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Manager.getDefault();
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
        subTask1.setEpicId(epic1);
        subTask2.setEpicId(epic1);
        subTask3.setEpicId(epic2);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);
        epic1.addSubTask(subTask1);
        epic1.addSubTask(subTask2);
        epic2.addSubTask(subTask3);
        taskManager.updateEpic(epic1);
        taskManager.updateEpic(epic2);
        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubTasks());

        task1.setStatus(TaskStatus.DONE);
        taskManager.updateTask(task1);

        subTask1.setStatus(TaskStatus.DONE);
        subTask2.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateSubTask(subTask1);
        taskManager.updateSubTask(subTask2);

        subTask3.setStatus(TaskStatus.DONE);
        taskManager.updateSubTask(subTask3);

        System.out.println(taskManager.getEpics());
        //taskManager.removeTaskById(task1.getId());
        //taskManager.removeSubTaskById(subTask3.getId());
        //taskManager.removeEpicById(epic1.getId());

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubTasks());

        System.out.println("\n");
        System.out.println(taskManager.getTaskById(task1.getId()));
        System.out.println(taskManager.getEpicById(epic1.getId()));
        System.out.println(taskManager.getSubTaskById(subTask1.getId()));
        System.out.println(taskManager.getTaskById(task2.getId()));
        System.out.println(taskManager.getTaskById(task1.getId()));
        System.out.println("getHistory: \n");
        System.out.println(taskManager.getHistory());
        //System.out.println("getAllTasks: \n");
        //System.out.println(taskManager.getAllTasks());


    }
}
