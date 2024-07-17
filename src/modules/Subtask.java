package modules;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Subtask extends Task {

    private Integer epicId;

    public Subtask(String name, String description, Integer epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public Subtask (Integer id, TaskType type, String name, TaskStatus status, String description,
                Integer epicId) {
        super(id, name, status, description);
        this.type = type;
        this.epicId = epicId;
    }

    public Subtask(Integer id, TaskType type, String name, TaskStatus status, String description, Duration duration,
                   LocalDateTime startTime, Integer epicId) {
        super(id, type, name, status, description, duration, startTime);
        this.epicId = epicId;
    }

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return id + "," + type + "," + name + "," + status + "," + description + "," + duration + "," +
                startTime + "," + epicId;
    }
}

