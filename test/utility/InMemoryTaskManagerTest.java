package utility;

import modules.Epic;
import modules.Subtask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryTaskManagerTest {
    private TaskManager manager;
    @BeforeEach
    public void initialization() {
        manager = Managers.getDefault();
    }

    @Test
    public void subtaskCannotBeEpicForItself() {
        Subtask subtask = manager.createSubtask(new Subtask("","",0));
        Assertions.assertNull(subtask.getId());
    }

    @Test
    public void checkThatManagerCanCreateAndGiveEpicById() {
        Epic epic = manager.createEpic(new Epic("",""));
        Assertions.assertNotNull(manager.getEpicById(epic.getId()));
    }

    @Test
    public void
}