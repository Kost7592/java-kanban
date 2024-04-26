import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final Integer id = 0;


    public void removeTaskById () {
        tasks.remove(id);
    }
    public void removeAllTasks() {
        tasks.clear();
    }
    public void removeEpicById() {
        epics.remove(id);
    }

    public void createSubTask(Task subTask, HashMap subtasks) {

        subtasks.put(id,subTask);
    }
    public void createEpic(Epic epic, HashMap epics) {

        epics.put(id,epic);
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, Subtask> getsubtasks() {
        return subtasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public Integer getId() {
        return id;
    }
}


