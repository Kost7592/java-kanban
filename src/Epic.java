import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task{

    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    public Epic(String name, String description) {
        super(name, description);
    }
    public void addSubtask(Subtask newSubTask) {
        subtasks.put(newSubTask.getId(), newSubTask);
    }

    public void updateSubtask(Subtask newSubtask) {
        if(subtasks.containsKey(newSubtask.getId())) {
            subtasks.put(newSubtask.getId(), newSubtask);
        }
    }

    public void removeAllSubtasks() {
        subtasks.clear();
    }

    public void removeSubtask (Integer id) {
        subtasks.remove(id);
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtasks=" + subtasks +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", id=" + id +
                '}';
    }

    @Override
    public void setTaskStatus (TaskStatus taskStatus){

    }
    public void UpdateStatus() {
        int countOfDone = 0;
        int countOfNew = 0;

        for (Subtask subtask: subtasks.values()) {
            if (subtask.getStatus() == TaskStatus.NEW) {
                countOfNew++;
            } else if (subtask.getStatus() == TaskStatus.DONE) {
                countOfDone++;
            }
        }
        if (countOfDone == subtasks.size()) {
            taskStatus = TaskStatus.DONE;
        } else if (countOfNew == subtasks.size()) {
            taskStatus = TaskStatus.NEW;
        } else {
            taskStatus = TaskStatus.IN_PROGRESS;
        }
    }

}
