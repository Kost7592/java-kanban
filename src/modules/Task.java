package modules;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Comparable {
    protected String name;
    protected String description;
    protected TaskStatus status;
    protected Integer id;
    protected TaskType type;
    protected LocalDateTime startTime;
    protected Duration duration;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
    }

    public Task(Integer id, String name, TaskStatus status, String description) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = id;
    }

    public Task(Integer id, TaskType type, String name, TaskStatus status, String description, Duration duration,
                LocalDateTime startTime) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.description = description;
        this.type = type;
        this.duration = duration;
        this.startTime = startTime;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Integer getId() {
        return id;
    }

    public TaskType getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id + "," + type + "," + name + "," + status + "," + description + "," + duration + "," + startTime;
    }

    @Override
    public int compareTo(Object o) {
        Task task = (Task) o;
        return startTime.compareTo(task.startTime);
    }
}





