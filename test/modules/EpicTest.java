package modules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    public void shouldBeEqualIfIdsEqual(){
        Epic epic1 = new Epic("1", "1");
        Epic epic2 = new Epic("1", "1");
        epic1.setId(1);
        epic2.setId(1);
        assertEquals(epic1, epic2);
    }
}