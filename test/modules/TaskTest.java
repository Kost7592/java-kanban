package modules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    public void shouldBeEqualIfIdEqual() {
        Task task1 = new Task("1", "1");
        Task task2 = new Task("1", "1");
        task1.setId(1);
        task2.setId(1);
        assertEquals(task1,task2);
    }

}