package modules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    public void shouldBeEqualIfIdEqual() {
        Subtask subtask1 = new Subtask("1", "1",1);
        Subtask subtask2 = new Subtask("1", "1", 1);
        subtask1.setId(1);
        subtask2.setId(1);
        assertEquals(subtask1, subtask2);
    }
}