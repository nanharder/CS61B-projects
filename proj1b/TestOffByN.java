import org.junit.Test;
import static org.junit.Assert.*;


public class TestOffByN {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offBy5 = new OffByN(5);

    // Your tests go here.
    @Test
    public void testEqualChars() {
        assertTrue(offBy5.equalChars('a', 'f'));
        assertFalse(offBy5.equalChars('c', 'c'));
        assertFalse(offBy5.equalChars('f', 'h'));
    }
}
