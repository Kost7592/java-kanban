public class Subtask extends Task{

    protected Integer epicId;

    public Subtask(String name, String description, String status, Integer epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }
}

