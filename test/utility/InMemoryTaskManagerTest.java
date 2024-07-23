package utility;

import exception.TaskVerifiedException;
import modules.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private TaskManager manager;
    Task task1;
    Task task2;
    Epic epic1;
    Subtask subtask1;
    Subtask subtaskWithTime;

    @BeforeEach
    public void initialization() { //инициализация
        manager = Managers.getDefault();
        task1 = new Task("Task 1", "Task 1 description");
        task2 = new Task("Task 2", "Task 2 description");
        epic1 = new Epic("Epic", "Epic description");
        manager.createEpic(epic1);
        subtask1 = new Subtask("Subtask 1", "Subtask 1 description", epic1.getId());
        subtaskWithTime = new Subtask(4, TaskType.SUBTASK, "Subtask 2", TaskStatus.NEW,
                "Subtask 2 description", Duration.ofMinutes(120), LocalDateTime.now(), epic1.getId());
        manager.createTask(task1);
        manager.createTask(task2);
        manager.createSubtask(subtask1);
        manager.createSubtask(subtaskWithTime);
    }

    @Test
    public void checkThatManagerCanCreateTaskAndGiveItById() { //менеджер задач может создавать и выдавать задачу по id
        assertEquals(task1, manager.getTaskById(task1.getId()));
    }

    @Test
    public void checkThatManagerCanCreateEpicAndGiveItById() { //менеджер задач может создавать и выдавать эпик по id
        assertEquals(epic1, manager.getEpicById(epic1.getId()));
    }

    @Test
    public void checkThatManagerCanCreateSubtaskAndGiveItById() { //менеджер задач может создавать и выдавать
        manager.createSubtask(subtask1);                          // подзадачу по id
        assertEquals(subtask1, manager.getSubtaskById(subtask1.getId()));
    }

    @Test
    public void checkThatTheSubTaskIsSavedInTheEpic() { //подзадача сохраняется в эпике
        assertNotNull(manager.getEpicSubtasks(epic1.getId()));
    }

    @Test
    public void checkTheImmutabilityOfTheTaskAfterItsCreation() { //проверка неизменности задачи после ее создания
        manager.createTask(task1);
        assertEquals(task1.getName(), manager.getTaskById(task1.getId()).getName());
        assertEquals(task1.getDescription(), manager.getTaskById(task1.getId()).getDescription());
        assertEquals(task1.getId(), manager.getTaskById(task1.getId()).getId());
    }

    @Test
    public void checkTheImmutabilityOfTheEpicAfterItsCreation() { //проверка неизменности эпика после его создания
        assertEquals(epic1.getName(), manager.getEpicById(epic1.getId()).getName());
        assertEquals(epic1.getDescription(), manager.getEpicById(epic1.getId()).getDescription());
        assertEquals(epic1.getId(), manager.getEpicById(epic1.getId()).getId());
    }

    @Test
    public void checkTheImmutabilityOfTheSubtaskAfterItsCreation() { //проверка неизменности подзадачи после ее создания
        assertEquals(subtask1.getName(), manager.getSubtaskById(subtask1.getId()).getName());
        assertEquals(subtask1.getDescription(), manager.getSubtaskById(subtask1.getId()).getDescription());
        assertEquals(subtask1.getId(), manager.getSubtaskById(subtask1.getId()).getId());
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

    @Test
    public void checkThatSubtaskIsNotEmpty() { //проверка корректности полей subtask и автоподсчете полей времени epic
        Epic epic = manager.getEpicById(subtaskWithTime.getEpicId());
        assertNotNull(subtaskWithTime);
        assertEquals(subtaskWithTime.getDuration(), epic.getDuration());
        assertEquals(subtaskWithTime.getStartTime(), epic.getStartTime());
        assertEquals(epic.getEndTime(), epic.getStartTime().plus(epic.getDuration()));
    }

    @Test
    public void checkThatCheckingOfTaskTimeIsWork() { //проверка срабатывания исключения на пересечение времени у задач
        task1 = new Task(1, TaskType.TASK, "Task1", TaskStatus.NEW, "Task1 descrption",
                Duration.ofMinutes(120), LocalDateTime.now());
        assertThrowsExactly(TaskVerifiedException.class, () -> {
            manager.createTask(task1);
        });
        assertThrowsExactly(TaskVerifiedException.class, () -> {
            manager.updateTask(task1);
        });
    }

    @Test
    public void checkThatEpicStatusIsNew() { //проверка обновления статуса эпика на NEW
        Epic epic = new Epic(0, TaskType.EPIC, "Epic", TaskStatus.DONE, "Epic Description",
                new ArrayList());
        manager.createEpic(epic);
        Subtask subtask1 = new Subtask(1, TaskType.SUBTASK, "Subtask1", TaskStatus.NEW,
                "Subtask1 description", epic.getId());
        Subtask subtask2 = new Subtask(2, TaskType.SUBTASK, "Subtask2", TaskStatus.NEW,
                "Subtask2 description", epic.getId());
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);
        assertEquals(TaskStatus.NEW, epic.getStatus());
    }

    @Test
    public void checkThatEpicStatusIsDone() { //проверка обновления статуса эпика на DONE
        Epic epic = new Epic(0, TaskType.EPIC, "Epic", TaskStatus.DONE, "Epic Description",
                new ArrayList());
        manager.createEpic(epic);
        Subtask subtask1 = new Subtask(1, TaskType.SUBTASK, "Subtask1", TaskStatus.DONE,
                "Subtask1 description", epic.getId());
        Subtask subtask2 = new Subtask(2, TaskType.SUBTASK, "Subtask2", TaskStatus.DONE,
                "Subtask2 description", epic.getId());
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);
        assertEquals(TaskStatus.DONE, epic.getStatus());
    }

    @Test
    public void checkThatEpicStatusIsInProgress() { //проверка обновления статуса эпика на IN_PROGRESS
        Epic epic = new Epic(0, TaskType.EPIC, "Epic", TaskStatus.NEW, "Epic Description",
                new ArrayList());
        manager.createEpic(epic);
        Subtask subtask1 = new Subtask(1, TaskType.SUBTASK, "Subtask1", TaskStatus.DONE,
                "Subtask1 description", epic.getId());
        Subtask subtask2 = new Subtask(2, TaskType.SUBTASK, "Subtask2", TaskStatus.NEW,
                "Subtask2 description", epic.getId());
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
    }
}