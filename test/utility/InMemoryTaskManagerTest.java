package utility;

import modules.Epic;
import modules.Subtask;
import modules.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryTaskManagerTest {
    private InMemoryTaskManager manager;

    @BeforeEach
    public void initialization() {
        manager = new InMemoryTaskManager();
    }

    @Test
    public void subtaskCannotBeEpicForItself() {
        Subtask subtask = manager.createSubtask(new Subtask("","",0));
        Assertions.assertNull(subtask.getId());
    }

    @Test
    public void checkThatManagerCanCreateEpicAndGiveItById() {
        Epic epic = manager.createEpic(new Epic("",""));
        Assertions.assertNotNull(epic.getId());
    }

    @Test
    public void checkThatManagerCanCreateSubtaskAndGiveItById() {
        Epic epic = manager.createEpic(new Epic("",""));
        Subtask subtask = manager.createSubtask(new Subtask("", "", epic.getId()));
        Assertions.assertNotNull(manager.getSubtaskById(subtask.getId()));
    }

    @Test
    public void checkThatManagerCanCreateTaskAndGiveItById() {
        Task task = manager.createTask(new Task("", ""));
        Assertions.assertNotNull(manager.getTaskById(task.getId()));
    }

    @Test
    public void epicShouldNotChangeWhenValueChanged() {
        Epic epic = manager.createEpic(new Epic("", ""));
        epic.setName("Другое имя");
        Assertions.assertNotEquals(epic.getName(), manager.getEpicById(epic.getId()).getName());
    }

    @Test
    public void epicShouldNotChangeWhenSourceChanged() {
        Epic source = new Epic("", "");
        Epic epic = manager.createEpic(source);
        source.setName("Другое имя");
        Assertions.assertNotEquals(source.getName(), manager.getEpicById(epic.getId()).getName());
    }

    @Test
    public void subtaskShouldNotChangeWhenValueChanged() {
        Epic epic = manager.createEpic(new Epic("", ""));
        Subtask subtask = manager.createSubtask(new Subtask("a", "", epic.getId()));
        subtask.setName("b");
        Assertions.assertNotEquals(subtask.getName(), manager.getSubtaskById(subtask.getId()).getName());
    }

    @Test
    public void subtaskShouldNotChangeWhenSourceChanged() {
        Epic epic = manager.createEpic(new Epic("", ""));
        Subtask source = manager.createSubtask(new Subtask("", "", epic.getId()));
        Subtask subtask = manager.createSubtask(new Subtask("" ,"", epic.getId()));
        source.setName("Другое имя");
        Assertions.assertNotEquals(source.getName(), manager.getSubtaskById(subtask.getId()).getName());
    }

    @Test
    public void taskShouldNotChangeWhenValueChanged() {
        Task task = manager.createTask(new Task("", ""));
        task.setName("Другое имя");
        Assertions.assertNotEquals(task.getName(), manager.getEpicById(task.getId()).getName());
    }

    @Test
    public void taskShouldNotChangeWhenSourceChanged() {
        Task source = new Task("", "");
        Task task = manager.createTask(new Task("", ""));
        source.setName("Другое имя");
        Assertions.assertNotEquals(source.getName(), manager.getTaskById(task.getId()).getName());
    }

}