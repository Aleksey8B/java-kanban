package tasks;

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
    public String toString() {
        return "\nEpic{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", subTasksId=" + getSubTasksId() +
                '}';
    }
}
