public class Palindrome {
    /** Convert words to deque */
    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> wordDeque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i += 1) {
            wordDeque.addLast(word.charAt(i));
        }
        return wordDeque;
    }

    public boolean isPalindrome(String word) {
        return true;
    }
}
