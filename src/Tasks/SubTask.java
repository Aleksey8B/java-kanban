package Tasks;

public class SubTask extends Task{
    protected int epicId;
    public SubTask(String name, String description) {
        super(name, description);

    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", epicId=" + getEpicId() +
                '}';
    }
}
