package utility;

import modules.Epic;
import modules.Subtask;
import modules.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryTaskManagerTest {
    private TaskManager manager;
    Task task1;
    Task task2;
    Epic epic1;
    Subtask subtask1;

    Subtask subtask2;

    @BeforeEach
    public void initialization() {
        manager = Managers.getDefault();
        task1 = new Task("Task 1", "Task 1 description");
        task2 = new Task("Task 2", "Task 2 description");
        epic1 = new Epic("Epic", "Epic description");
        subtask1 = new Subtask("Subtask 1", "Subtask 1 description", epic1.getId());
        subtask2 = new Subtask("Subtask 2", "Subtask 2 description", epic1.getId());
        manager.createTask(task1);
        manager.createTask(task2);
        manager.createEpic(epic1);
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);
    }

    @Test
    public void checkThatManagerCanCreateTaskAndGiveItById() {
        Assertions.assertEquals(task1, manager.getTaskById(task1.getId()));
    }

    @Test
    public void checkThatManagerCanCreateEpicAndGiveItById() {
        Assertions.assertEquals(epic1, manager.getEpicById(epic1.getId()));
    }

    @Test
    public void checkThatManagerCanCreateSubtaskAndGiveItById() {
        manager.createSubtask(subtask1);
        Assertions.assertEquals(subtask1, manager.getSubtaskById(subtask1.getId()));
    }

    @Test
    public void checkingThatTheTaskIsSavedInTheEpic() {
        manager.createEpic(epic1);
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);
        Assertions.assertNotNull(manager.getEpicSubtasks(epic1.getId()));
    }
    @Test
    public void checkTheImmutabilityOfTheTaskAfterItsCreation() {
        manager.createTask(task1);
        Assertions.assertEquals(task1.getName(), manager.getTaskById(task1.getId()).getName());
        Assertions.assertEquals(task1.getDescription(), manager.getTaskById(task1.getId()).getDescription());
        Assertions.assertEquals(task1.getId(), manager.getTaskById(task1.getId()).getId());
    }

    @Test
    public void checkTheImmutabilityOfTheEpicAfterItsCreation() {
        manager.createEpic(epic1);
        Assertions.assertEquals(epic1.getName(), manager.getEpicById(epic1.getId()).getName());
        Assertions.assertEquals(epic1.getDescription(), manager.getEpicById(epic1.getId()).getDescription());
        Assertions.assertEquals(epic1.getId(), manager.getEpicById(epic1.getId()).getId());
    }

    @Test
    public void checkTheImmutabilityOfTheSubtaskAfterItsCreation() {
        manager.createEpic(epic1);
        manager.createSubtask(subtask1);
        Assertions.assertEquals(subtask1.getName(), manager.getSubtaskById(subtask1.getId()).getName());
        Assertions.assertEquals(subtask1.getDescription(), manager.getSubtaskById(subtask1.getId()).getDescription());
        Assertions.assertEquals(subtask1.getId(), manager.getSubtaskById(subtask1.getId()).getId());
    }

}