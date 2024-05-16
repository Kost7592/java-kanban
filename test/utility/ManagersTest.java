package utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ManagersTest {//в первый раз, почему то, этот тест не с коммитился и не выгрузился на гитхаб

    @Test
    public void checkThatAlwaysReturnsReadyToUseTaskManager() {
        Assertions.assertNotNull(Managers.getDefault());
    }

    @Test
    public void checkThatAlwaysReturnsReadyToUseHistoryTaskManager() {
        Assertions.assertNotNull(Managers.getDefaultHistory());
    }
}
