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
    public void initialization() { //инициализация
        manager = Managers.getDefault();
        task1 = new Task("Task 1", "Task 1 description");
        task2 = new Task("Task 2", "Task 2 description");
        epic1 = new Epic("Epic", "Epic description");
        manager.createEpic(epic1);
        subtask1 = new Subtask("Subtask 1", "Subtask 1 description", epic1.getId());
        subtask2 = new Subtask("Subtask 2", "Subtask 2 description", epic1.getId());
        manager.createTask(task1);
        manager.createTask(task2);
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);
    }

    @Test
    public void checkThatManagerCanCreateTaskAndGiveItById() { //менеджер задач может создавать и выдавать задачу по id
        Assertions.assertEquals(task1, manager.getTaskById(task1.getId()));
    }

    @Test
    public void checkThatManagerCanCreateEpicAndGiveItById() { //менеджер задач может создавать и выдавать эпик по id
        Assertions.assertEquals(epic1, manager.getEpicById(epic1.getId()));
    }

    @Test
    public void checkThatManagerCanCreateSubtaskAndGiveItById() { //менеджер задач может создавать и выдавать
        manager.createSubtask(subtask1);                          // подзадачу по id
        Assertions.assertEquals(subtask1, manager.getSubtaskById(subtask1.getId()));
    }

    @Test
    public void checkThatTheSubTaskIsSavedInTheEpic() { //подзадача сохраняется в эпике
        manager.createEpic(epic1);
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);
        Assertions.assertNotNull(manager.getEpicSubtasks(epic1.getId()));
    }
    @Test
    public void checkTheImmutabilityOfTheTaskAfterItsCreation() { //проверка неизменности задачи после ее создания
        manager.createTask(task1);
        Assertions.assertEquals(task1.getName(), manager.getTaskById(task1.getId()).getName());
        Assertions.assertEquals(task1.getDescription(), manager.getTaskById(task1.getId()).getDescription());
        Assertions.assertEquals(task1.getId(), manager.getTaskById(task1.getId()).getId());
    }

    @Test
    public void checkTheImmutabilityOfTheEpicAfterItsCreation() { //проверка неизменности эпика после его создания
        Assertions.assertEquals(epic1.getName(), manager.getEpicById(epic1.getId()).getName());
        Assertions.assertEquals(epic1.getDescription(), manager.getEpicById(epic1.getId()).getDescription());
        Assertions.assertEquals(epic1.getId(), manager.getEpicById(epic1.getId()).getId());
    }

    @Test
    public void checkTheImmutabilityOfTheSubtaskAfterItsCreation() { //проверка неизменности подзадачи после ее создания
        Assertions.assertEquals(subtask1.getName(), manager.getSubtaskById(subtask1.getId()).getName());
        Assertions.assertEquals(subtask1.getDescription(), manager.getSubtaskById(subtask1.getId()).getDescription());
        Assertions.assertEquals(subtask1.getId(), manager.getSubtaskById(subtask1.getId()).getId());
    }

    @Test
    public void checkThatTheEpicCanNotBeItsOwnSubtask() { //проверка, что эпик не может быть своей подзадачей
        Subtask subtask3 = new Subtask(epic1.getName(), epic1.getDescription(), epic1.getId());
        subtask3.setId(epic1.getId());
        manager.createSubtask(subtask3);
        Assertions.assertNotEquals(manager.getEpicById(epic1.getId()), subtask3);
    }

    @Test
    public void checkThatTheSubtaskCanNotBeOwnEpic() { //проверка, что подзадача не может быть своим эпиком
        Subtask subtask3 = new Subtask("Subtask3", "Subtask 3 description", epic1.getId());
        subtask3.setId(epic1.getId());
        manager.createSubtask(subtask3);
        Assertions.assertNotEquals(manager.getEpicById(epic1.getId()), subtask3);
    }
}