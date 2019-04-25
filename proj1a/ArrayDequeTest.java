/** Performs some basic Array list tests. */
import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {
    @Test
    public void TestAdd() {
        ArrayDeque<Integer> input = new ArrayDeque<>();
        for (int i = 0; i < 100; i += 1) {
            input.addLast(i);
        }
        int result = input.size();
        int result2 = input.get(99);
        assertEquals(100, result);
        assertEquals(99, result2);
    }

    public void TestRemove() {
        ArrayDeque<Integer> input = new ArrayDeque<>();
        for (int i = 0; i < 16; i += 1) {
            input.addLast(i);
        }
        for (int i = 0; i < 10; i += 1) {
            input.removeLast();
        }
        int result = input.size();
        int result2 = input.get(0);
        assertEquals(6, result);
        assertEquals(0, result2);
    }

}
