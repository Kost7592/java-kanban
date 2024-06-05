package utility;

import modules.Epic;
import modules.Subtask;
import modules.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

class InMemoryHistoryManagerTest {
    private TaskManager manager;
    Task task1;
    Task task2;

    @BeforeEach
    public void initialization() {
        manager = Managers.getDefault();
        task1 = new Task("Task 1", "Task 1 description");
        task2 = new Task("Task 2", "Task 2 description");
        manager.createTask(task1);
        manager.createTask(task2);
    }

    @Test
    public void checkingThatTheHistoryIsBeingAdded() {
        List<Task> tasks = new LinkedList<>();
        tasks.add(manager.getTaskById(task1.getId()));
        tasks.add(manager.getTaskById(task2.getId()));
        Assertions.assertNotNull(manager.getHistory());
        Assertions.assertEquals(manager.getHistory(), tasks);
    }
    @Test
    public void checkingThatEpicDoesNotChangeInHistory() {
        Epic epic1 = new Epic("Epic1", "Description");
        manager.createEpic(epic1);
        manager.getEpicById(epic1.getId());
        epic1.setName("Epic 11");
        manager.updateEpic(epic1);
        Assertions.assertNotEquals(manager.getHistory().getFirst().getName(),
                                   manager.getEpicById(epic1.getId()));
    }

    @Test
    public void checkingThatDuplikatesRemovesFromHistory() {
        manager.getTaskById(task1.getId());
        manager.getTaskById(task2.getId());
        manager.getTaskById(task1.getId());
        List<Task> tasks = manager.getHistory();
        Assertions.assertEquals(2, tasks.size());
    }

    @Test
    public void checkingThatSubtaskDoesNotChangeInHistory() {
        Epic epic = manager.createEpic(new Epic("", ""));
        Subtask subtask = manager.createSubtask(new Subtask("a", "", epic.getId()));
        manager.getSubtaskById(subtask.getId());
        subtask.setName("b");
        manager.updateSubtask(subtask);
        Assertions.assertNotEquals(manager.getHistory().getFirst().getName(),
                                   manager.getSubtaskById(subtask.getId()).getName());
    }

    @Test
    public void checkingThatTaskDoesNotChangeInHistory() {
        Task task = manager.createTask(new Task("a", ""));
        manager.getTaskById(task.getId());
        task.setName("b");
        manager.updateTask(task);
        Assertions.assertNotEquals(
                manager.getHistory().getFirst().getName(),
                manager.getTaskById(task.getId()).getName());
    }

}