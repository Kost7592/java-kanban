public class Task {
    protected String name;
    protected String description;
    protected TaskStatus status;

    public Task(String name, String description, String status) {
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
    }
}




