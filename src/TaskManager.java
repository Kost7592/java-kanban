import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    Scanner scanner = new Scanner();
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Subtask> subTasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    Integer id;

    public void createTask(Task task, HashMap tasks) {
        task.name = Scanner.nextLine();
        task.description = Scanner.nextLine();
        task.status = String.valueOf(TaskStatus.NEW);
        id++;
        tasks.put(id,task);
    }
    public void createSubTask(Task subTask, HashMap subTasks) {
        subTask.name = Scanner.nextLine();
        subTask.description = Scanner.nextLine();
        subTask.status = String.valueOf(TaskStatus.NEW);
        id++;
        subTasks.put(id,subTask);
    }
    public void createEpic(Epic epic, HashMap epics) {
        epic.name = Scanner.nextLine();
        epic.description = Scanner.nextLine();
        epic.status = String.valueOf(TaskStatus.NEW);
        id++;
        epics.put(id,epic);
    }
}


