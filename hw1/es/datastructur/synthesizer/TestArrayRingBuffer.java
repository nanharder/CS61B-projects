package es.datastructur.synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        for (int i = 0; i < 5; i+= 1) {
            arb.enqueue(i);
        }
        assertEquals(5, arb.fillCount());
        assertEquals(0, (int) arb.peek());
        assertEquals(0, (int) arb.dequeue());
        assertEquals(1, (int) arb.peek());
    }
}
