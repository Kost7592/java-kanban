package modules;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Epic extends Task {

    private List<Integer> subtasksId = new ArrayList<>();
    private LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description);
    }


    public Epic(Integer id, TaskType type, String name, TaskStatus status, String description,
                List<Integer> subtasksId) {
        super(id, name, status, description);
        this.type = type;
        this.subtasksId = subtasksId;
    }

    public Epic(Integer id, TaskType type, String name, TaskStatus status, String description,
                List<Integer> subtasksId, Duration duration, LocalDateTime startTime, LocalDateTime endTime) {
        super(id, type, name, status, description, duration, startTime);
        this.subtasksId = subtasksId;
        this.endTime = endTime;
    }

    public List<Integer> getSubtasksId() {
        return subtasksId;
    }

    public void setSubtasksId(List<Integer> subtasksId) {
        this.subtasksId = subtasksId;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return id + "," + type + "," + name + "," + status + "," + description + "," + duration + "," + startTime + ","
                + endTime;
    }
}
