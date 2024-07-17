package utility;

import modules.Epic;
import modules.Subtask;
import modules.Task;

import java.util.List;
import java.util.TreeSet;

public interface TaskManager {
    List<Task> getTasks();

    List<Subtask> getSubtasks();

    List<Epic> getEpics();

    Task getTaskById(Integer id);

    Subtask getSubtaskById(Integer id);

    Epic getEpicById(Integer id);

    void removeTaskById(Integer id);

    void removeAllTasks();

    void removeEpicById(Integer id);

    List<Subtask> getEpicSubtasks(Integer id);

    void removeAllEpics();

    void removeSubtaskById(Integer id);

    void removeAllSubtasks();

    Integer getEpicIdOfSubtask(Subtask subtask);

    Task createTask(Task newTask);

    Epic createEpic(Epic newEpic);

    Subtask createSubtask(Subtask newSubtask);

    void updateTask(Task newTask);

    void updateSubtask(Subtask newSubtask);

    List<Task> getHistory();

    void updateEpic(Epic newEpic);

    TreeSet<Task> getPrioritizedTasks();
}
