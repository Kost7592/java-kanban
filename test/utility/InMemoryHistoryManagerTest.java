package utility;

import modules.Epic;
import modules.Subtask;
import modules.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

class InMemoryHistoryManagerTest {
    private TaskManager manager;
    Task task1;
    Task task2;

    @BeforeEach
    public void initialization() { //инициализация
        manager = Managers.getDefault();
        task1 = new Task("Task 1", "Task 1 description");
        task2 = new Task("Task 2", "Task 2 description");
        manager.createTask(task1);
        manager.createTask(task2);
    }

    @Test
    public void checkingThatTheHistoryIsBeingAdded() { //проверка заполнения истории
        List<Task> tasks = new LinkedList<>();
        tasks.add(manager.getTaskById(task1.getId()));
        tasks.add(manager.getTaskById(task2.getId()));
        Assertions.assertNotNull(manager.getHistory());
        Assertions.assertEquals(manager.getHistory(), tasks);
    }

    @Test
    public void checkingThatEpicDoesNotChangeInHistory() { //проверка неизменности эпика в истории
        Epic epic1 = new Epic("a", "");
        manager.createEpic(epic1);
        manager.getEpicById(epic1.getId());
        epic1.setName("b");
        manager.updateEpic(epic1);
        Assertions.assertNotEquals(manager.getHistory().get(0).getName(),
                                   manager.getEpicById(epic1.getId()).getName());
    }

    @Test
    public void checkingThatDuplicateRemoveFromHistory() { //проверка удаления дублирующейся записи из истории
        manager.getTaskById(task1.getId());
        manager.getTaskById(task2.getId());
        manager.getTaskById(task1.getId());
        List<Task> tasks = manager.getHistory();
        Assertions.assertEquals(2, tasks.size());
    }

    @Test
    public void checkingThatSubtaskDoesNotChangeInHistory() { //проверка неизменности подзадачи в истории
        Epic epic = manager.createEpic(new Epic("", ""));
        Subtask subtask = manager.createSubtask(new Subtask("a", "", epic.getId()));
        manager.getSubtaskById(subtask.getId());
        subtask.setName("b");
        manager.updateSubtask(subtask);
        Assertions.assertNotEquals(manager.getHistory().getLast().getName(),
                                   manager.getSubtaskById(subtask.getId()).getName());
    }

    @Test
    public void checkingThatTaskDoesNotChangeInHistory() { //проверка неизменности задачи в истории
        Task task = manager.createTask(new Task("a", ""));
        manager.getTaskById(task.getId());
        task.setName("b");
        manager.updateTask(task);
        Assertions.assertNotEquals(manager.getHistory().getFirst().getName(),
                                   manager.getTaskById(task.getId()).getName());
    }
}