import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private int size = 0;
    private Node root;

    private class Node {
        private K key;
        private V val;
        private Node left, right;

        public Node(K k, V v) {
            key = k;
            val = v;
        }
    }

    @Override
    /** Removes all of the mappings from this map. */
    public void clear() {
        size = 0;
        root = null;
    }

    private boolean containsKey(K key, Node node) {
        if (node == null) {
            return false;
        } else if (key.compareTo(node.key) < 0) {
            return containsKey(key, node.left);
        } else if (key.compareTo(node.key) > 0) {
            return containsKey(key, node.right);
        } else {
            return true;
        }
    }

    @Override
    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        return containsKey(key, root);
    }

    private V get(K key, Node node) {
        if (node == null) {
            return null;
        } else if (key.compareTo(node.key) < 0) {
            return get(key, node.left);
        } else if (key.compareTo(node.key) > 0) {
            return get(key, node.right);
        } else {
            return node.val;
        }
    }

    @Override
    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        return get(key, root);
    }

    @Override
    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return size;
    }

    private Node put(K key, V value, Node node) {
        if (node == null) {
            size += 1;
            return new Node(key, value);
        } else if (key.compareTo(node.key) < 0) {
            node.left = put(key, value, node.left);
        } else if (key.compareTo(node.key) == 0) {
            node.val = value;
        } else {
            node.right = put(key, value, node.right);
        }
        return node;
    }

    @Override
    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        root = put(key, value, root);
    }

    @Override
    /* Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    private void printNode(Node node) {
        if (node != null) {
            printNode(node.left);
            System.out.println(node.key + " : " + node.val);
            printNode(node.right);
        }

    }

    public void printInOrder() {
        printNode(root);
    }
}
