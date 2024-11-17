package manager;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;

public  class TaskManager {
    private int tasksId = 0;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();

    private int getTasksId() {
        return tasksId++;
    }

    public void addTask(Task task) {
        task.setId(getTasksId());
        tasks.put(task.getId(),task);
    }

    public void addEpic(Epic epic) {
        epic.setId(getTasksId());
        epics.put(epic.getId(), epic);
    }

    public void addSubTask(SubTask subTask, Epic epic) {
        subTask.setId(getTasksId());
        subTask.setEpicId(epic.getId());
        epic.addSubTask(subTask.getId());
        subTasks.put(subTask.getId(),subTask);
        updateEpicStatus(epic);
        updateEpic(epic);
    }

    public ArrayList<Object> getAllTasks() {
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

    public ArrayList<Task> getTasks() {
        if (tasks.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getEpics() {
        if (epics.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(epics.values());
    }

    public ArrayList<SubTask> getSubTasks() {
        if (subTasks.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(subTasks.values());
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(),task);
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(),epic);
    }

    private void updateEpicStatus(Epic epic) {
        ArrayList<SubTask> epicSubTasks = getEpicsSubTasks(epic);
        int doneSubCount = 0;
        if (!epicSubTasks.isEmpty()){
            for(SubTask subTask : epicSubTasks) {
                if (subTask.getStatus().equals(TaskStatus.DONE)){
                    doneSubCount++;
                }
            }
            if (doneSubCount < epicSubTasks.size()) {
                epic.setStatus(TaskStatus.IN_PROGRESS);
            } else if (doneSubCount == epicSubTasks.size()) {
                epic.setStatus(TaskStatus.DONE);
            }
        } else {
            if (!epic.getSubTasksId().isEmpty()) {
                epic.clearSubTasksId();
            }
            epic.setStatus(TaskStatus.NEW);
        }
        updateEpic(epic);
    }

    public void updateSubTask(SubTask subTask) {
        updateEpicStatus(epics.get(subTask.getEpicId()));
        subTasks.put(subTask.getId(),subTask);
    }

    public Task getTaskById(int taskId) {
        if (tasks.containsKey(taskId)) return tasks.get(taskId);
        return null;
    }

    public Epic getEpicById(int epicId) {
        if (epics.containsKey(epicId)) return epics.get(epicId);
        return null;
    }

    public SubTask getSubTaskById(int subTaskId) {
        if (subTasks.containsKey(subTaskId)) return subTasks.get(subTaskId);
        return null;
    }

    public ArrayList<SubTask> getEpicsSubTasks(Epic epic) {
        ArrayList<SubTask> epicsSubTasks = new ArrayList<>();
        if(!subTasks.isEmpty()) {
            for (int subId : epic.getSubTasksId()) {
                epicsSubTasks.add(subTasks.get(subId));
            }
        }
        return epicsSubTasks;
    }

    public void removeTaskById(int taskId) {
        tasks.remove(taskId);
    }

    public void removeEpicById(int epicId) {
        ArrayList<SubTask> tmp = new ArrayList<>(subTasks.values());
        for (SubTask subTask : tmp) {
            if (subTask.getEpicId() == epicId){
                subTasks.remove(subTask.getId());
            }
        }
        epics.remove(epicId);
    }

    public void removeSubTaskById(int subTaskId) {
        SubTask subTask = subTasks.get(subTaskId);
        Epic epic = epics.get(subTask.getEpicId());
        epic.removeSubTask(subTaskId);
        updateEpicStatus(epic);
        subTasks.remove(subTaskId);
    }

    public void deleteTasks() {
        tasks.clear();
    }

    public void deleteEpics() {
        if(!epics.isEmpty()) {
            epics.clear();
        }
        if (!subTasks.isEmpty()) {
            deleteSubTasks();
        }
    }

    public void deleteSubTasks() {
        if (!subTasks.isEmpty()) {
            subTasks.clear();
        }
        for (Epic epic : epics.values()) {
            epic.clearSubTasksId();
            updateEpicStatus(epic);
        }
    }
}
