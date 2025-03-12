package manager;

import constant.*;
import exception.*;
import tasks.*;

import java.io.*;
import java.util.ArrayList;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public static void main(String[] args) {

    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String rawTaskInfo = br.readLine();
            while (br.ready()) {
                rawTaskInfo = br.readLine();
                Task task = load(rawTaskInfo);
                switch (task.getType()) {
                    case TASK -> manager.addTask(task);
                    case EPIC -> manager.addEpic((Epic) task);
                    case SUB_TASK -> manager.addSubTask((SubTask) task);
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка загрузки файла!", e);
        }
        return manager;
    }

    private void save() {
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(CSVConstant.CSV_HEADER);
            for (Task task : getTasks()) {
                writer.write(task.toString());
            }
            for (Epic epic : getEpics()) {
                writer.write(epic.toString());
                for (SubTask subTask : getEpicsSubTasks(epic)) {
                    writer.write(subTask.toString());
                }
            }
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
                if (!taskInfo[6].isEmpty()) {
                    String[] subTaskids = taskInfo[6].split(",");
                    ArrayList<Integer> arrSubTaskIds = new ArrayList<>();
                    for (String str : subTaskids) {
                        arrSubTaskIds.add(Integer.parseInt(str));
                    }
                    tmpEpic.setSubTasksId(arrSubTaskIds);
                }
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
    public void updateEpicStatus(Epic epic) {
        super.updateEpicStatus(epic);
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
}
