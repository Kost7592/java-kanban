import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final Integer id = 0;


    public void createTask(Task task, HashMap tasks) {

        tasks.put(id,task);
    }
    public void createSubTask(Task subTask, HashMap subTasks) {

        subTasks.put(id,subTask);
    }
    public void createEpic(Epic epic, HashMap epics) {

        epics.put(id,epic);
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, Subtask> getSubTasks() {
        return subTasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public Integer getId() {
        return id;
    }
}


