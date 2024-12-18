package manager;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int tasksId;
    private final Map<Integer, Task> tasks;
    private final Map<Integer, Epic> epics;
    private final Map<Integer, SubTask> subTasks;
    private final HistoryManager historyManager = Manager.getDefaultHistory();
    public InMemoryTaskManager() {
        tasksId = 0;
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subTasks = new HashMap<>();
    }

    private int getTasksId() {
        return tasksId++;
    }

    @Override
    public void addTask(Task task) {
        task.setId(getTasksId());
        tasks.put(task.getId(),task);
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(getTasksId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public void addSubTask(SubTask subTask) {
        subTask.setId(getTasksId());
        subTasks.put(subTask.getId(),subTask);
        updateEpicStatus(epics.get(subTask.getEpicId()));
    }

    @Override
    public List<Task> getAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (int i = 0; i < (tasks.size() + epics.size() + subTasks.size()); i++) {
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

    @Override
    public List<Task> getTasks() {
        if (tasks.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getEpics() {
        if (epics.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<SubTask> getSubTasks() {
        if (subTasks.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(),task);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(),epic);
    }

    @Override
    public void updateEpicStatus(Epic epic) {
        ArrayList<SubTask> epicSubTasks = new ArrayList<>(getEpicsSubTasks(epic));
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

    @Override
    public void updateSubTask(SubTask subTask) {
        updateEpicStatus(epics.get(subTask.getEpicId()));
        subTasks.put(subTask.getId(),subTask);
    }

    @Override
    public Task getTaskById(int taskId) {
        if (tasks.containsKey(taskId)) {
            Task taskTmp = tasks.get(taskId);
            historyManager.addHistory(taskTmp);
            return taskTmp;
        }
        return null;
    }

    @Override
    public Epic getEpicById(int epicId) {
        if (epics.containsKey(epicId)) {
            Epic epicTmp = epics.get(epicId);
            historyManager.addHistory(epicTmp);
            return epicTmp;
        }
        return null;
    }

    @Override
    public SubTask getSubTaskById(int subTaskId) {
        if (subTasks.containsKey(subTaskId)) {
            SubTask subTaskTmp = subTasks.get(subTaskId);
            historyManager.addHistory(subTaskTmp);
            return subTaskTmp;
        }
        return null;
    }

    @Override
    public List<SubTask> getEpicsSubTasks(Epic epic) {
        ArrayList<SubTask> epicsSubTasks = new ArrayList<>();
        if(!subTasks.isEmpty()) {
            for (int subId : epic.getSubTasksId()) {
                epicsSubTasks.add(subTasks.get(subId));
            }
        }
        return epicsSubTasks;
    }

    @Override
    public void removeTaskById(int taskId) {
        tasks.remove(taskId);
    }

    @Override
    public void removeEpicById(int epicId) {
        ArrayList<SubTask> tmp = new ArrayList<>(subTasks.values());
        for (SubTask subTask : tmp) {
            if (subTask.getEpicId() == epicId){
                subTasks.remove(subTask.getId());
            }
        }
        epics.remove(epicId);
    }

    @Override
    public void removeSubTaskById(int subTaskId) {
        SubTask subTask = subTasks.get(subTaskId);
        Epic epic = epics.get(subTask.getEpicId());
        epic.removeSubTask(subTask);
        updateEpicStatus(epic);
        subTasks.remove(subTaskId);
    }

    @Override
    public void deleteTasks() {
        tasks.clear();
    }

    @Override
    public void deleteEpics() {
        if(!epics.isEmpty()) {
            epics.clear();
        }
        if (!subTasks.isEmpty()) {
            deleteSubTasks();
        }
    }

    @Override
    public void deleteSubTasks() {
        if (!subTasks.isEmpty()) {
            subTasks.clear();
        }
        for (Epic epic : epics.values()) {
            epic.clearSubTasksId();
            updateEpicStatus(epic);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }



}
