import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyTrieSet implements TrieSet61B {
    private Node root = new Node();

    private class Node {
        private char ch;
        private boolean isKey;
        private HashMap<Character, Node> map = new HashMap<>();

        Node(char c, boolean iK) {
            ch = c;
            isKey = iK;
        }

        Node() {
        }
    }

    @Override
    public void clear() {
        root = new Node();
    }

    @Override
    public boolean contains(String key) {
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                return false;
            }
            curr = curr.map.get(c);
        }
        return (curr.isKey);
    }

    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }

        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(c, false));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }

    private void colHelp(String s, List<String> x,Node n) {
        if (n.isKey) {
            x.add(s);
        }
        for (char c : n.map.keySet()) {
            colHelp(s + c, x, n.map.get(c));
        }
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        ArrayList<String> result = new ArrayList<>();

        Node curr = root;
        for (int i = 0, n = prefix.length(); i < n; i++) {
            char c = prefix.charAt(i);
            if (!curr.map.containsKey(c)) {
                return result;
            }
            curr = curr.map.get(c);
        }
        colHelp(prefix, result, curr);
        return result;
    }

    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }
}
