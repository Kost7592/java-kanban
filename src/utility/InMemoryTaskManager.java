package utility;

import modules.Epic;
import modules.Subtask;
import modules.Task;
import modules.TaskStatus;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final HistoryManager historyManager = new InMemoryHistoryManager();
    private Integer countOfId = 0;

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public Task getTaskById(Integer id) {
        historyManager.addView(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Subtask getSubtaskById(Integer id) {
        historyManager.addView(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public Epic getEpicById(Integer id) {
        historyManager.addView(epics.get(id));
        return epics.get(id);
    }

    @Override
    public void removeTaskById(Integer id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void removeEpicById(Integer id) {
        Epic epic = getEpicById(id);
        if (epics.containsKey(id)) {
            for (Integer subtaskId: epic.getSubtasksId()) {
                subtasks.remove(subtaskId);
            }
        }
        epics.remove(id);
        historyManager.remove(id);
    }

    @Override
    public List<Subtask> getEpicSubtasks(Integer id) {
        if (epics.containsKey(id)) {
            ArrayList<Subtask> epicSubtasks = new ArrayList<>();
            Epic epic = epics.get(id);
            for (Integer subtaskId : epic.getSubtasksId()) {
                epicSubtasks.add(subtasks.get(subtaskId));
            }
            return epicSubtasks;
        }
        return new ArrayList<>();
    }

    @Override
    public void  removeAllEpics(){
        if (!(subtasks.isEmpty())) {
            removeAllSubtasks();
        }
        epics.clear();
    }

    @Override
    public void removeSubtaskById(Integer id) {
        Epic epic = getEpicById(subtasks.get(id).getEpicId());
        epic.getSubtasksId().remove(id);
        subtasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeAllSubtasks() {
        subtasks.clear();
        for (Epic epic: epics.values()) {
            epic.setSubtasksId(new ArrayList<>());
            updateEpicStatus(epic.getId());
        }
    }

    @Override
    public Task createTask(Task newTask) {
        newTask.setId(getNewId());
        tasks.put(newTask.getId(),newTask);
        return newTask;
    }

    @Override
    public Epic createEpic(Epic newEpic) {
        newEpic.setId(getNewId());
        epics.put(newEpic.getId(),newEpic);
        return newEpic;
    }

    @Override
    public Subtask createSubtask(Subtask newSubtask) {
        newSubtask.setId(getNewId());
        if (epics.containsKey(getEpicIdOfSubtask(newSubtask))) {
            subtasks.put(newSubtask.getId(), newSubtask);
            Epic epic = epics.get(newSubtask.getEpicId());
            epic.getSubtasksId().add(newSubtask.getId());
            subtasks.put(newSubtask.getId(), newSubtask);
            updateEpicStatus(newSubtask.getEpicId());
        }
        return newSubtask;
    }

    @Override
    public void updateTask(Task newTask) {
        if (tasks.containsKey(newTask.getId())) {
            tasks.put((newTask.getId()), newTask);
        }
    }

    @Override
    public void updateSubtask(Subtask newSubtask) {
        if (subtasks.containsKey(newSubtask.getId()) && epics.containsKey(newSubtask.getEpicId())) {
            subtasks.put(newSubtask.getId(), newSubtask);
            updateEpicStatus(newSubtask.getEpicId());
        }
    }

    @Override
    public void updateEpic(Epic newEpic) {
        if (epics.containsKey(newEpic.getId())) {
            epics.put(newEpic.getId(),newEpic);
            updateEpicStatus(newEpic.getId());
        }
    }

    @Override
    public Integer getEpicIdOfSubtask(Subtask subtask) {
        if (epics.isEmpty()) {
            return null;
        }
        Epic epic = getEpicById(subtask.getEpicId());
        return epic.getId();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void updateEpicStatus(Integer id) {
        int countOfDone = 0;
        int countOfNew = 0;
        Epic epic = getEpicById(id);
        List<Subtask> epicSubtasks = getEpicSubtasks(id);
        for (Subtask subtask: epicSubtasks) {
            if (subtask.getStatus() == TaskStatus.NEW) {
                countOfNew++;
            } else if (subtask.getStatus() == TaskStatus.DONE) {
                countOfDone++;
            }
        }
        if (countOfDone == epicSubtasks.size()) {
            epic.setStatus(TaskStatus.DONE);
        } else if (countOfNew == epicSubtasks.size()) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    private Integer getNewId() {
        return countOfId++;
    }
}
