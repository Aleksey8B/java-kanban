package Tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subTasksId;
    public Epic(int id, String name, String description) {
        super(id, name, description);
        subTasksId = new ArrayList<>();
    }

    public ArrayList<Integer> getSubTasksId() {
        return subTasksId;
    }
    public void addSubTask(int subTaskId) {
        subTasksId.add(subTaskId);
    }
    public void removeSubTask(int subTaskId) {
        subTasksId.remove(subTasksId.indexOf(subTaskId));
    }
    public void clearSubTasksId() {
        subTasksId.clear();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", subTasksId=" + subTasksId +
                '}';
    }
}
