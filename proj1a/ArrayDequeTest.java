/** Performs some basic Array list tests. */
import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {
    @Test
    public void TestAdd() {
        ArrayDeque<Integer> input = new ArrayDeque<>();
        input.addFirst(1);
        input.addFirst(2);
        int result = input.get(0);
        int result2 = input.get(1);
        assertEquals(2, result);
        assertEquals(1, result2);
    }
}
