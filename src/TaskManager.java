import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private Integer countOfId = 0;
    private TaskStatus taskStatus;

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public Task getTaskById (Integer id) {
        return tasks.get(id);
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public Subtask getSubtaskById (Integer id) {
        return subtasks.get(id);
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public Epic getEpicById(Integer id) {
        return epics.get(id);
    }

    public void setTaskStatus (TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    private Integer getNewId() {
        return countOfId++;
    }

    public void removeTaskById (Integer id) {
        tasks.remove(id);
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeEpicById(Integer id) {
        if(epics.containsKey(id)) {
            for (Subtask subtask: getEpicSubtasks(epics.get(id))) {
                subtasks.remove(subtask.getId());
            }
        }
        epics.remove(id);
    }

    public void  removeAllEpics(){
        epics.clear();
    }

    public void removeSubtaskById(Integer id) {
        getEpicById(subtasks.get(id).getEpicId());
        removeSubtask(id);
        updateStatus();
        subtasks.remove(id);
    }

    public void removeSubtask (Integer id) {
        subtasks.remove(id);
    }
    public void removeAllSubtasks() {
        subtasks.clear();
    }

    public void createTask(Task newTask) {
        newTask.setId(getNewId());
        tasks.put(newTask.getId(),newTask);
    }

    public void createEpic(Epic newEpic) {
        newEpic.setId(getNewId());
        epics.put(newEpic.getId(),newEpic);
    }

    public void createSubtask(Subtask newSubtask) {
        newSubtask.setId(getNewId());
        subtasks.put(newSubtask.getId(),newSubtask);
        Epic epic = epics.get(newSubtask.getEpicId());
        addSubtask((newSubtask));
        updateStatus();
    }

    public void addSubtask(Subtask newSubTask) {
        subtasks.put(newSubTask.getId(), newSubTask);
    }
    public void updateTask (Task newTask) {
        if (tasks.containsKey(newTask.getId())) {
            tasks.put((newTask.getId()), newTask);
        }
    }

    public void updateSubtask(Subtask newSubtask) {
        if(subtasks.containsKey(newSubtask.getId()) && epics.containsKey(newSubtask.getEpicId())) {
            subtasks.put(newSubtask.getId(), newSubtask);
            updateStatus();
        }
    }

    public void updateEpic(Epic newEpic) {
        if (epics.containsKey(newEpic.getId())) {
            Epic epic = epics.get(newEpic.getId());
            epic.setName(newEpic.getName());
            epic.setDescription(newEpic.getDescription());
        }
    }

    public void updateStatus() {
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

    public ArrayList<Subtask> getEpicSubtasks(Epic epic) {
        if(epics.containsKey(epic.getId())) {
            return getSubtasks();
        }
        return new ArrayList<>();
    }

}


