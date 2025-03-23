package manager;

import constant.*;
import exception.*;
import tasks.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        super();
        this.file = file;
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while (br.ready()) {
                switch (line) {
                    case "Last task id:" -> {
                        manager.tasksId = Integer.parseInt(br.readLine());
                        line = br.readLine();
                    }
                    case "Task list:" -> {
                        while (true) {
                            line = br.readLine();
                            if (line.equals("History list:")) break;
                            if (line.equals("id;status;type;name;description;epic id")) continue;
                            Task task = load(line);
                            switch (task.getType()) {
                                case TASK -> manager.tasks.put(task.getId(), task);
                                case EPIC -> manager.epics.put(task.getId(), (Epic) task);
                                case SUB_TASK -> manager.subTasks.put(task.getId(), (SubTask) task);
                            }
                        }
                        for (SubTask subTask : manager.getSubTasks()) {
                            Epic epic = manager.epics.get(subTask.getEpicId());
                            epic.addSubTask(subTask);
                            manager.epics.put(epic.getId(), epic);
                        }
                    }
                    case "History list:" -> {
                        while (br.ready()) {
                            line = br.readLine();
                            String[] ids = line.split(CSVConstant.CSV_DELIMITER);
                            List<Integer> idFromHistory = new ArrayList<>();
                            for (String str : ids) {
                                idFromHistory.add(Integer.parseInt(str));
                            }
                            for (Integer id : idFromHistory) {
                                if (manager.tasks.containsKey(id)) manager.historyManager.add(manager.tasks.get(id));
                                if (manager.epics.containsKey(id)) manager.historyManager.add(manager.epics.get(id));
                                if (manager.subTasks.containsKey(id)) manager.historyManager.add(manager.subTasks.get(id));
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка загрузки файла!", e);
        }
        return manager;
    }

    private void save() {
        int lastId = 0;
        for (Task task : getTasks()) {
            if (lastId < task.getId()) lastId = task.getId();
        }
        for (Epic epic : getEpics()) {
            if (lastId < epic.getId()) lastId = epic.getId();
        }
        for (SubTask subTask : getSubTasks()) {
            if (lastId < subTask.getId()) lastId = subTask.getId();
        }
        lastId++;
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(CSVConstant.CSV_LAST_TASK_ID);
            writer.write(lastId + "\n");
            writer.write(CSVConstant.CSV_TASK_LIST_HEADER);
            writer.write(CSVConstant.CSV_COLUMN_HEADER);
            for (Task task : getTasks()) {
                writer.write(task.toString());
            }
            for (Epic epic : getEpics()) {
                writer.write(epic.toString());
                for (SubTask subTask : getEpicsSubTasks(epic)) {
                    writer.write(subTask.toString());
                }
            }
            writer.write(CSVConstant.CSV_HISTORY_LIST_HEADER);
            StringBuilder idFromHistory = new StringBuilder();
            for (Task task : historyManager.getHistory()) {
                idFromHistory.append(task.getId()).append(";");
            }
            writer.write(idFromHistory.toString() + "\n");
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения в файл", e);
        }
    }

    private static Task load(String rawTaskInfo) {
        String[] taskInfo = rawTaskInfo.split(CSVConstant.CSV_DELIMITER);
        int id = Integer.parseInt(taskInfo[0]);
        TaskStatus status = TaskStatus.valueOf(taskInfo[1]);
        TaskType type = TaskType.valueOf(taskInfo[2]);
        String name = taskInfo[3];
        String description = taskInfo[4];

        switch (type) {
            case TASK -> {
                Task tmpTask = new Task(name,description);
                tmpTask.setId(id);
                tmpTask.setStatus(status);
                return tmpTask;
            }

            case EPIC -> {
                Epic tmpEpic = new Epic(name,description);
                tmpEpic.setId(id);
                tmpEpic.setStatus(status);
                return tmpEpic;
            }

            case SUB_TASK -> {
                SubTask tmpSubTask = new SubTask(name, description);
                tmpSubTask.setId(id);
                tmpSubTask.setStatus(status);
                tmpSubTask.setEpicId(Integer.parseInt(taskInfo[5]));
                return tmpSubTask;
            }

            default -> throw new IllegalArgumentException("Неизвестный тип задачи" + type);
        }
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubTask(SubTask subTask) {
        super.addSubTask(subTask);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void removeTaskById(int taskId) {
        super.removeTaskById(taskId);
        save();
    }

    @Override
    public void removeEpicById(int epicId) {
        super.removeEpicById(epicId);
        save();
    }

    @Override
    public void removeSubTaskById(int subTaskId) {
        super.removeSubTaskById(subTaskId);
        save();
    }

    @Override
    public void deleteTasks() {
        super.deleteTasks();
        save();
    }

    @Override
    public void deleteEpics() {
        super.deleteEpics();
        save();
    }

    @Override
    public void deleteSubTasks() {
        super.deleteSubTasks();
        save();
    }

    @Override
    public Task getTaskById(int taskId) {
        Task task = super.getTaskById(taskId);
        save();
        return task;
    }

    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = super.getEpicById(epicId);
        save();
        return epic;
    }

    @Override
    public SubTask getSubTaskById(int subTaskId) {
        SubTask subTask = super.getSubTaskById(subTaskId);
        save();
        return subTask;
    }
}
