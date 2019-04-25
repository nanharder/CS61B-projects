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

}
