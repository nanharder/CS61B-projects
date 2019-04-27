import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testGold() {
        StudentArrayDeque<Integer> input = new StudentArrayDeque<>();
        String message = "";

        for (int i = 0; i < 10; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                input.addLast(i);
                assertEquals(message + "addLast()\n", (Object) i, input.get(input.size() - 1));
                message += "addLast(" + i + ")\n";
            } else {
                input.addFirst(i);
                assertEquals(message + "addFirst()\n", (Object) i, input.get(0));
                message += "addFirst(" + i + ")\n";
            }
        }
        for (int i = 0; i < 10; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                assertEquals(message + "removeFirst()\n", input.get(0), input.removeFirst());
                message += "removeFirst()\n";
            } else {
                assertEquals(message + "removeLast()\n",
                        input.get(input.size() - 1), input.removeLast());
                message += "removeLast()\n";
            }
        }
    }
}
