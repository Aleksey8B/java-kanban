package tasks;

import constant.TaskType;

public class SubTask extends Task {
    protected int epicId;

    public SubTask(String name, String description) {
        super(name, description);
    }

    public void setEpicId(Epic epic) {
        this.epicId = epic.getId();
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public TaskType getType() {
        return TaskType.SUB_TASK;
    }

    @Override
    public String toString() {
        return String.format("%d;%s;%s;%s;%s;%d\n",
                getId(),
                getStatus().toString(),
                getType().toString(),
                getName(),
                getDescription(),
                getEpicId());
    }
}
