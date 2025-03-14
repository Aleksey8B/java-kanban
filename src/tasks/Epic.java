package tasks;

import constant.TaskType;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subTasksId;

    public Epic(String name, String description) {
        super(name, description);
        subTasksId = new ArrayList<>();
    }

    public ArrayList<Integer> getSubTasksId() {
        return subTasksId;
    }

    public void  setSubTasksId(ArrayList<Integer> subTasksIdArr) {
        subTasksId.addAll(subTasksIdArr);
    }

    public void addSubTask(SubTask subTask) {
        subTasksId.add(subTask.getId());
    }

    public void removeSubTask(SubTask subTask) {
        subTasksId.remove(subTasksId.indexOf(subTask.getId()));
    }

    public void clearSubTasksId() {
        subTasksId.clear();
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }

    @Override
    public String toString() {
        String strSubTasksId = "";
        if (!subTasksId.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Integer id : subTasksId) {
                sb.append(id);
                sb.append(',');
            }
            strSubTasksId = sb.toString();
        }
        return String.format("%d;%s;%s;%s;%s;;%s\n",
                getId(),
                getStatus(),
                getType(),
                getName(),
                getDescription(),
                strSubTasksId);
    }
}
