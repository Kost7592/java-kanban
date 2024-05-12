package utility;

import modules.Epic;
import modules.Subtask;
import modules.Task;

import java.util.ArrayList;

public interface TaskManager {
    ArrayList<Task> getTasks();

    ArrayList<Subtask> getSubtasks();

    ArrayList<Epic> getEpics();

    Task getTaskById(Integer id);

    Subtask getSubtaskById(Integer id);

    Epic getEpicById(Integer id);

    void removeTaskById(Integer id);

    void removeAllTasks();

    void removeEpicById(Integer id);

    ArrayList<Subtask> getEpicSubtasks(Integer id);

    void removeAllEpics();

    void removeSubtaskById(Integer id);

    void removeAllSubtasks();

    void createTask(Task newTask);

    void createEpic(Epic newEpic);

    void createSubtask(Subtask newSubtask);

    void updateTask(Task newTask);

    void updateSubtask(Subtask newSubtask);

    void updateEpic(Epic newEpic);
}
