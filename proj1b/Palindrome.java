public class Palindrome {
    /** Convert words to deque */
    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> wordDeque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i += 1) {
            wordDeque.addLast(word.charAt(i));
        }
        return wordDeque;
    }

    private boolean isPalindromeHelper(Deque wordDeque) {
        if (wordDeque.size() <= 1) {
            return true;
        }
        if (wordDeque.removeFirst().equals(wordDeque.removeLast())) {
            return isPalindromeHelper(wordDeque);
        } else {
            return false;
        }
    }

    /** Check if the word is Palindrome */
    public boolean isPalindrome(String word) {
        Deque<Character> wordDeque = wordToDeque(word);
        return isPalindromeHelper(wordDeque);
    }

    private boolean isPalindromeHelper(Deque wordDeque, CharacterComparator cc) {
        if (wordDeque.size() <= 1) {
            return true;
        }
        if (cc.equalChars((char) wordDeque.removeFirst(), (char) wordDeque.removeLast())) {
            return isPalindromeHelper(wordDeque, cc);
        } else {
            return false;
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> wordDeque = wordToDeque(word);
        return isPalindromeHelper(wordDeque, cc);
    }
}
