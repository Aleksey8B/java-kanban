import Tasks.Epic;
import Tasks.SubTask;
import Tasks.Task;
import Tasks.TaskStatuses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public  class TaskManager {
    private static int tasksId = 0;
    private static final HashMap<Integer, Task> tasks = new HashMap<>();
    private static final HashMap<Integer, Epic> epics = new HashMap<>();
    private static final HashMap<Integer, SubTask> subTasks = new HashMap<>();

    public static int getTasksId() {
        tasksId++;
        return tasksId;
    }

    public static void addTask(Task task) {
        tasks.put(task.getId(),task);
    }
    public static void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }
    public static void addSubTask(SubTask subTask, Epic epic) {
        epic.addSubTask(subTask.getId());
        epics.put(epic.getId(),epic);
        subTasks.put(subTask.getId(),subTask);
    }
    public static ArrayList<Object> getAllTasks() {
        ArrayList<Object> allTasks = new ArrayList<>();
        for (int i = 1; i < (tasks.size() + epics.size() + subTasks.size()); i++) {
            if (tasks.containsKey(i)) {
                allTasks.add(tasks.get(i));
            } else if (epics.containsKey(i)) {
                allTasks.add(epics.get(i));
            } else if (subTasks.containsKey(i)) {
                allTasks.add(subTasks.get(i));
            }
        }
        return allTasks;
    }
    public static ArrayList<Task> getTasks() {
        if (tasks.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(tasks.values());
    }
    public static ArrayList<Epic> getEpics() {
        if (epics.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(epics.values());
    }
    public static ArrayList<SubTask> getSubTasks() {
        if (subTasks.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(subTasks.values());
    }
    public static ArrayList<SubTask> getEpicsSubTask(Epic epic) {
        ArrayList<SubTask> epicsSubTasks = new ArrayList<>();
        for (int subId : epic.getSubTasksId()) {
            epicsSubTasks.add(subTasks.get(subId));
        }
        return epicsSubTasks;
    }
    public static void updateTask(Task task) {
        if (task.getStatus().equals(TaskStatuses.NEW)){
            task.setStatus(TaskStatuses.IN_PROGRESS);
        } else if (task.getStatus().equals(TaskStatuses.IN_PROGRESS)){
            task.setStatus(TaskStatuses.DONE);
        }
        tasks.put(task.getId(),task);
    }
    public static void updateEpic(Epic epic) {
        ArrayList<SubTask> epicSubTasks = getEpicsSubTask(epic);
        int doneSubCount = 0;
        if (!epicSubTasks.isEmpty()){
            for(SubTask subTask : epicSubTasks) {
                if (subTask.getStatus().equals(TaskStatuses.DONE)){
                    doneSubCount++;
                }
            }
            if (doneSubCount < epicSubTasks.size()) {
                epic.setStatus(TaskStatuses.IN_PROGRESS);
            } else if (doneSubCount == epicSubTasks.size()) {
                epic.setStatus(TaskStatuses.DONE);
            }
        } else {
            epic.setStatus(TaskStatuses.NEW);
        }
        epics.put(epic.getId(),epic);
    }
    public static void updateSubTask(SubTask subTask) {
        if (subTask.getStatus().equals(TaskStatuses.NEW)){
            subTask.setStatus(TaskStatuses.IN_PROGRESS);
        } else if (subTask.getStatus().equals(TaskStatuses.IN_PROGRESS)) {
            subTask.setStatus(TaskStatuses.DONE);
        }
        subTasks.put(subTask.getId(),subTask);
        updateEpic(epics.get(subTask.getEpicId()));
    }
    public static Task getTaskById(int taskId) {
        if (tasks.containsKey(taskId)) return tasks.get(taskId);
        return null;
    }
    public static Epic getEpicById(int epicId) {
        if (epics.containsKey(epicId)) return epics.get(epicId);
        return null;
    }
    public static SubTask getSubTaskById(int subTaskId) {
        if (subTasks.containsKey(subTaskId)) return subTasks.get(subTaskId);
        return null;
    }
    public static void removeTaskById(int taskId) {
        tasks.remove(taskId);
    }
    public static void removeEpicById(int epicId) {
        ArrayList<SubTask> tmp = new ArrayList<>(subTasks.values());
        for (SubTask subTask : tmp) {
            if (subTask.getEpicId() == epicId){
                subTasks.remove(subTask.getId());
            }
        }

        epics.remove(epicId);
    }
    public static void removeSubTaskById(int subTaskId) {
        SubTask subTask = subTasks.get(subTaskId);
        Epic epic = epics.get(subTask.getEpicId());
        epic.removeSubTask(subTaskId);
        updateEpic(epic);
        epics.put(epic.getId(),epic);
        subTasks.remove(subTaskId);
    }
}
