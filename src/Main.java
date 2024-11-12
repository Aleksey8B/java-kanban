import Tasks.Epic;
import Tasks.SubTask;
import Tasks.Task;

public class Main {
    public static void main(String[] args) {
        Task task1 = new Task(TaskManager.getTasksId(), "Task 1", "Task 1 description");
        Task task2 = new Task(TaskManager.getTasksId(), "Task 2", "Task 2 description");
        Epic epic1 = new Epic(TaskManager.getTasksId(), "Epic 1", "Epic 1 description");
        Epic epic2 = new Epic(TaskManager.getTasksId(), "Epic 2", "Epic 2 description");
        SubTask subTask1 =  new SubTask(TaskManager.getTasksId(), "SubTask 1", "SubTask 1 description", epic1.getId());
        SubTask subTask2 =  new SubTask(TaskManager.getTasksId(), "SubTask 2", "SubTask 2 description", epic1.getId());
        SubTask subTask3 =  new SubTask(TaskManager.getTasksId(), "SubTask 3", "SubTask 3 description", epic2.getId());

        TaskManager.addTask(task1);
        TaskManager.addTask(task2);
        TaskManager.addEpic(epic1);
        TaskManager.addEpic(epic2);
        TaskManager.addSubTask(subTask1,epic1);
        TaskManager.addSubTask(subTask2,epic1);
        TaskManager.addSubTask(subTask3,epic2);

        System.out.println(TaskManager.getTasks());
        System.out.println(TaskManager.getEpics());
        System.out.println(TaskManager.getSubTasks());

        TaskManager.updateTask(task1);
        TaskManager.updateSubTask(subTask1);
        TaskManager.updateSubTask(subTask1);
        TaskManager.updateSubTask(subTask2);
        TaskManager.updateSubTask(subTask2);
        TaskManager.updateSubTask(subTask3);
        System.out.println(TaskManager.getEpics());
        TaskManager.removeTaskById(task1.getId());
        TaskManager.removeSubTaskById(subTask3.getId());
        TaskManager.removeEpicById(epic1.getId());

        System.out.println(TaskManager.getTasks());
        System.out.println(TaskManager.getEpics());
        System.out.println(TaskManager.getSubTasks());
    }
}
