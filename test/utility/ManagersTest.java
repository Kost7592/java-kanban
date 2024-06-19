package utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ManagersTest {
    @Test
    public void checkThatAlwaysReturnsReadyToUseTaskManager() { //диспетчер задач всегда возвращает менеджер задач
        Assertions.assertNotNull(Managers.getDefault());
    }

    @Test
    public void checkThatAlwaysReturnsReadyToUseHistoryTaskManager() { //диспетчер задач всегда возвращает
        Assertions.assertNotNull(Managers.getDefaultHistory());        //менеджер истории
    }
}
