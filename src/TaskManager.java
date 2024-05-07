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

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public Task getTaskById (Integer id) {
        return tasks.get(id);
    }

    public Subtask getSubtaskById (Integer id) {
        return subtasks.get(id);
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
            for (Subtask subtask: getEpicSubtasks(id)) {
                subtasks.remove(subtask.getId());
            }
        }
        epics.remove(id);
    }

    public ArrayList<Subtask> getEpicSubtasks(Integer id) {
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            for (Subtask subtask : getSubtasks()) {
                if (subtask.getEpicId() == epic.getId()) {
                    subtasks.put(subtask.getId(), subtask);
                }
                return new ArrayList<>(subtasks.values());
            }
        }
        return new ArrayList<>();
    }

    public void  removeAllEpics(){
        if(!(subtasks.isEmpty())) {
            removeAllSubtasks();
        }
        epics.clear();
    }

    public void removeSubtaskById(Integer id) {
        getEpicById(subtasks.get(id).getEpicId());
        subtasks.remove(id);
        updateEpicStatus();
    }

    public void removeAllSubtasks() {
        subtasks.clear();
        getEpics();
        for(Epic epic: epics.values()) {
            epic.setSubtasksId(null);
        }
        updateEpicStatus();
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
        epic.subtasksId.add(newSubtask.getId());
        subtasks.put(newSubtask.getId(),newSubtask);
        updateEpicStatus(newSubtask.getEpicId());
    }

    public void updateTask (Task newTask) {
        if (tasks.containsKey(newTask.getId())) {
            tasks.put((newTask.getId()), newTask);
        }
    }

    public void updateSubtask(Subtask newSubtask) {
        if(subtasks.containsKey(newSubtask.getId()) && epics.containsKey(newSubtask.getEpicId())) {
            subtasks.put(newSubtask.getId(), newSubtask);
            updateEpicStatus(newSubtask.getEpicId());
        }
    }

    public void updateEpic(Epic newEpic) {
        if (epics.containsKey(newEpic.getId())) {
            Epic epic = epics.get(newEpic.getId());
            epic.setName(newEpic.getName());
            epic.setDescription(newEpic.getDescription());
        }
    }
    public void updateEpicStatus(Integer id) {
        int countOfDone = 0;
        int countOfNew = 0;
        Epic epic = getEpicById(id);
        getEpicSubtasks(id);
        for (Subtask subtask: subtasks.values()) {
            if (subtask.getStatus() == TaskStatus.NEW) {
                countOfNew++;
            } else if (subtask.getStatus() == TaskStatus.DONE) {
                countOfDone++;
            }
        }
        if (countOfDone == subtasks.size()) {
            epic.setStatus(TaskStatus.DONE);
        } else if (countOfNew == subtasks.size()) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }
}