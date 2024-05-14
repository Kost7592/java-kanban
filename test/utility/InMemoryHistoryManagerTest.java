package utility;

import modules.Epic;
import modules.Subtask;
import modules.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryHistoryManagerTest {
    private TaskManager manager;
    private HistoryManager historyManager;
    @BeforeEach
    public void initialization() {
        manager = Managers.getDefault();
        historyManager = Managers.getDefaultHistory();
    }

    @Test
    public void checkingThatEpicDoesNotChangeInHistory() {
        Epic epic = manager.createEpic(new Epic("a", ""));
        manager.getEpicById(epic.getId());
        epic.setName("b");
        manager.updateEpic(epic);

        Assertions.assertNotEquals(historyManager.getHistory().getFirst().getName(),
                manager.getEpicById(epic.getId()).getName());
    }

    @Test
    public void checkingThatSubtaskDoesNotChangeInHistory() {
        Epic epic = manager.createEpic(new Epic("", ""));
        Subtask subtask = manager.createSubtask(new Subtask("a", "", epic.getId()));
        manager.getSubtaskById(subtask.getId());
        subtask.setName("b");
        manager.updateSubtask(subtask);

        Assertions.assertNotEquals( historyManager.getHistory().getFirst().getName(),
                                    manager.getSubtaskById(subtask.getId()).getName());
    }

    @Test
    public void checkingThatTaskDoesNotChangeInHistory() {
        Task task = manager.createTask(new Task("a", ""));
        manager.getTaskById(task.getId());
        task.setName("b");
        manager.updateTask(task);

        Assertions.assertNotEquals(
                historyManager.getHistory().getFirst().getName(),
                manager.getTaskById(task.getId()).getName());
    }

}