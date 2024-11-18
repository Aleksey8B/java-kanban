package tasks;

public class SubTask extends Task{
    protected int epicId;
    public SubTask(String name, String description) {
        super(name, description);

    }

    public void setEpicId(Epic epic) {
        this.epicId = epic.getId();
    }
    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "\nSubTask{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", epicId=" + getEpicId() +
                '}';
    }
}
