package utility;

import exception.TaskVerifiedException;
import modules.Epic;
import modules.Subtask;
import modules.Task;
import modules.TaskStatus;

import java.util.*;
import java.time.LocalDateTime;
import java.time.Duration;

public class InMemoryTaskManager implements TaskManager {

    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Subtask> subtasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    protected final HistoryManager historyManager = new InMemoryHistoryManager();
    protected TreeSet<Task> sortedTasks = new TreeSet<>();
    protected Integer countOfId = 0;

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
    public Task getTaskById(Integer id) { //получение задачи по id
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.addView(tasks.get(id));
        }
        return task;
    }

    @Override
    public Subtask getSubtaskById(Integer id) {
        Subtask subtask = subtasks.get(id);//получение подзадачи по id
        if (subtask != null) {
            historyManager.addView(subtasks.get(id));
        }
        return subtask;
    }

    @Override
    public Epic getEpicById(Integer id) { // получение эпика по id
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.addView(epics.get(id));
        }
        return epic;
    }

    @Override
    public void removeTaskById(Integer id) { //удаление задачи по id
        tasks.remove(id);
        historyManager.remove(id);
        updateSortedTasks();
    }

    @Override
    public void removeAllTasks() { //удаление всех задач
        tasks.clear();
        updateSortedTasks();
    }

    @Override
    public void removeEpicById(Integer id) { //удаление эпика по id
        Epic epic = getEpicById(id);
        if (epics.containsKey(id)) {
            for (Integer subtaskId : epic.getSubtasksId()) {
                subtasks.remove(subtaskId);
                historyManager.remove(subtaskId);
            }
        }
        epics.remove(id);
        historyManager.remove(id);
        updateSortedTasks();
    }

    @Override
    public List<Subtask> getEpicSubtasks(Integer id) { //получение всех подзадач одного эпика
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
    public void removeAllEpics() { //удаление всех эпиков
        if (!(subtasks.isEmpty())) {
            removeAllSubtasks();
        }
        epics.clear();
        updateSortedTasks();
    }

    @Override
    public void removeSubtaskById(Integer id) { //удаление подзадачи по id
        Subtask removedSubtask = subtasks.get(id);
        Epic epic = getEpicById(removedSubtask.getEpicId());
        epic.getSubtasksId().remove(id);
        epic.setStartTime(getStartTime(epic));
        epic.setEndTime(getEndTime(epic));
        epic.setDuration(getDuration(epic));
        subtasks.remove(id);
        historyManager.remove(id);
        updateEpicStatus(removedSubtask.getEpicId());
        updateSortedTasks();
    }

    @Override
    public void removeAllSubtasks() { //удаление всех подзадач
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.setSubtasksId(new ArrayList<>());
            updateEpicStatus(epic.getId());
        }
        updateSortedTasks();
    }

    @Override
    public Task createTask(Task newTask) { //создание задачи
        if (newTask != null) {
            checkTaskStartTime(newTask);
            newTask.setId(getNewId());
            tasks.put(newTask.getId(), newTask);
            updateSortedTasks();
        }
        return newTask;
    }

    @Override
    public Epic createEpic(Epic newEpic) { //создание эпика
        if (newEpic != null) {
            newEpic.setId(getNewId());
            epics.put(newEpic.getId(), newEpic);
            updateSortedTasks();
        }
        return newEpic;
    }

    @Override
    public Subtask createSubtask(Subtask newSubtask) { //создание подзадачи
        if (newSubtask != null) {
            checkTaskStartTime(newSubtask);
            newSubtask.setId(getNewId());
            if (epics.containsKey(getEpicIdOfSubtask(newSubtask))) {
                subtasks.put(newSubtask.getId(), newSubtask);
                Epic epic = epics.get(newSubtask.getEpicId());
                epic.getSubtasksId().add(newSubtask.getId());
                epic.setDuration(getDuration(epic));
                epic.setStartTime(getStartTime(epic));
                epic.setEndTime(getEndTime(epic));
                subtasks.put(newSubtask.getId(), newSubtask);
                updateEpicStatus(newSubtask.getEpicId());
                updateSortedTasks();
            }
        }
        return newSubtask;
    }

    @Override
    public void updateTask(Task newTask) { //обновление задачи
        checkTaskStartTime(newTask);
        if (tasks.containsKey(newTask.getId())) {
            tasks.put((newTask.getId()), newTask);
            updateSortedTasks();
        }
    }

    @Override
    public void updateSubtask(Subtask newSubtask) { //обновление подзадачи
        Epic epic = epics.get(newSubtask.getEpicId());
        if (subtasks.containsKey(newSubtask.getId()) && epics.containsKey(newSubtask.getEpicId())) {
            subtasks.put(newSubtask.getId(), newSubtask);
            epic.setDuration(getDuration(epic));
            epic.setStartTime(getStartTime(epic));
            epic.setEndTime(getEndTime(epic));
            updateEpicStatus(newSubtask.getEpicId());
            updateSortedTasks();
        }

        updateSortedTasks();
    }

    @Override
    public void updateEpic(Epic newEpic) { //обновление эпика
        if (epics.containsKey(newEpic.getId())) {
            epics.put(newEpic.getId(), newEpic);
            updateSortedTasks();
        }
    }

    @Override
    public Integer getEpicIdOfSubtask(Subtask subtask) { //получение id эпика у подзадачи
        if (epics.isEmpty()) {
            return null;
        }
        Epic epic = getEpicById(subtask.getEpicId());
        return epic.getId();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    } // получение истории просмотров

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        return sortedTasks;
    } //получение отсортированного списка

    public LocalDateTime getStartTime(Epic epic) { //получение времени начала выполнения эпика
        List<Subtask> subtasks = getEpicSubtasks(epic.getId());
        LocalDateTime startTime = null;
        for (Subtask subtask : subtasks) {
            if (!(subtask.getStartTime() == null)) {
                LocalDateTime currentStartTime = subtask.getStartTime();
                if (startTime == null || currentStartTime.isBefore(startTime)) {
                    startTime = currentStartTime;
                }
            }
        }
        return startTime;
    }

    public LocalDateTime getEndTime(Epic epic) { //получение времени конца выполнения эпика
        Duration duration = epic.getDuration();
        LocalDateTime startTime = epic.getStartTime();
        if (startTime == null) {
            return null;
        }
        return startTime.plus(duration);
    }

    public Duration getDuration(Epic epic) { //получение продолжительности эпика
        List<Subtask> subtasks = getEpicSubtasks(epic.getId());
        Duration totalDuration = Duration.ofMinutes(0);
        for (Subtask subtask : subtasks) {
            if (subtask.getDuration() != null) {
                totalDuration = totalDuration.plus(subtask.getDuration());
            }
        }
        return totalDuration;
    }


    private void updateEpicStatus(Integer id) { //обновление статуса эпика
        int countOfDone = 0;
        int countOfNew = 0;
        Epic epic = getEpicById(id);
        List<Subtask> epicSubtasks = getEpicSubtasks(id);
        for (Subtask subtask : epicSubtasks) {
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

    @Override
    public void clearSortedTasks() { //очистка отсортированного по времени списка
        sortedTasks.clear();
    }

    private void updateSortedTasks() { //обновление отсортированного по времени списка
        List<Task> tasks = getTasks();
        List<Subtask> subtasks = getSubtasks();
        tasks.addAll(subtasks);
        List<Task> tasksWithStartTime = tasks.stream()
                .filter(task -> task.getStartTime() != null)
                .toList();
        sortedTasks.addAll(tasksWithStartTime);
    }

    private void checkTaskStartTime(Task task) { // проверка на пересечение времени у задач
        if (task.getStartTime() == null) {
            return;
        }
        boolean timeCoincidence = sortedTasks.stream()
                .anyMatch(task1 -> {

                    LocalDateTime endTime = task.getStartTime().plus(task.getDuration());
                    return (endTime.isAfter(task1.getStartTime()) && task.getStartTime().isBefore(task1.getStartTime()
                            .plus(task1.getDuration())));
                });
        if (timeCoincidence) {
            throw new TaskVerifiedException("Обнаружено пересечение времени у задач");
        }
    }

    private Integer getNewId() { //получение нового id счетчиком
        return countOfId++;
    }

}
