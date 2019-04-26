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
        int result2 = input.get(0);
        assertEquals(100, result);
        assertEquals(0, result2);
    }

    public void TestRemove() {
        ArrayDeque<Integer> input = new ArrayDeque<>();
        for (int i = 0; i < 16; i += 1) {
            input.addLast(i);
        }
        for (int i = 0; i < 7; i += 1) {
            input.removeLast();
        }
        for (int i = 0; i < 7; i += 1) {
            input.removeFirst();
        }
        int result = input.size();
        int result2 = input.get(0);
        assertEquals(2, result);
        assertEquals(8, result2);
    }

    @Test
    public void TestAdd2() {
        ArrayDeque<Integer> input = new ArrayDeque<>();
        input.isEmpty();
        input.addFirst(1);
        input.isEmpty();
        input.removeLast();
        input.isEmpty();
        input.addFirst(5);
        input.isEmpty();
        int result = input.removeLast();
        assertEquals(5, result);
    }

    @Test
    public void Testadd3() {
        ArrayDeque<Integer> input = new ArrayDeque<>();
        input.addLast(0);
        input.removeFirst();
        input.isEmpty();
        input.addLast(3);
        input.removeFirst();
    }

    @Test
    public void TestGet() {
        ArrayDeque<Integer> input = new ArrayDeque<>();
        input.addLast(0);
        input.get(0);
        input.removeLast();
        input.addFirst(3);
        input.removeFirst();
        input.addFirst(5);
        input.addLast(6);
        input.removeLast();
        input.addFirst(8);
        input.addLast(9);
        input.removeLast();
        int result = input.removeLast();
        assertEquals(5, result);
    }

    @Test
    public void TestGet2() {
        ArrayDeque<Integer> input = new ArrayDeque<>();
        input.addLast(0);
        input.addLast(1);
        input.addLast(2);
        input.removeFirst();
        int result = input.get(1);
        assertEquals(2, result);

    }

    @Test
    public void TestGet3() {
        ArrayDeque<Integer> input = new ArrayDeque<>();
        input.addLast(0);
        input.removeLast();
        input.addFirst(2);
        input.addFirst(3);
        input.removeLast();
        input.removeFirst();
        input.addFirst(6);
        input.get(0);
    }

    @Test
    public void TestResize() {
        ArrayDeque<Integer> input = new ArrayDeque<>();
        for (int i = 1; i < 64; i += 1) {
            input.addFirst(1);
        }
        for (int i = 1; i < 63; i += 1) {
            input.removeFirst();
        }
    }
    public static void main(String[] args) {
    }

}

