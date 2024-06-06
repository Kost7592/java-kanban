package utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ManagersTest {
    @Test
    public void checkThatAlwaysReturnsReadyToUseTaskManager() {
        Assertions.assertNotNull(Managers.getDefault());
    }

    @Test
    public void checkThatAlwaysReturnsReadyToUseHistoryTaskManager() {
        Assertions.assertNotNull(Managers.getDefaultHistory());
    }
}
