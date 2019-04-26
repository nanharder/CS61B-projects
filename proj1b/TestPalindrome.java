import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        String input1 = String.valueOf('c');
        boolean result1 = palindrome.isPalindrome(input1);
        assertTrue(result1);
        String input2 = "abcba";
        boolean result2 = palindrome.isPalindrome(input2);
        assertTrue(result2);
        String input3 = "abcde";
        boolean result3 = palindrome.isPalindrome(input3);
        assertFalse(result3);
        assertFalse(palindrome.isPalindrome("cat"));

    }

    @Test
    public void testIsPalindrome2() {
        assertFalse(palindrome.isPalindrome("cac"));
        assertTrue(palindrome.isPalindrome("cab"));
        assertTrue(palindrome.isPalindrome("c"));
    }
}
