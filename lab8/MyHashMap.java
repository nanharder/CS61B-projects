import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private static final int INIT_SIZE = 16;
    private static final double LOAD_FACTOR = 0.75;

    private int initialSize;
    private double loadFactor;
    private Item<K, V>[] map;
    private int size = 0;

    private class Item<K, V> {
        private K key;
        private V val;
        private Item next;

        public Item(K k, V v) {
            key = k;
            val = v;
            next = null;
        }
    }

    public MyHashMap() {
        this(INIT_SIZE, LOAD_FACTOR);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, LOAD_FACTOR);
    }

    public MyHashMap(int initialSize, double loadFactor) {
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        map = (Item<K, V>[]) new Item[initialSize];
    }

    @Override
    /** Removes all of the mappings from this map. */
    public void clear() {
        for (int i = 0; i < initialSize; i += 1) {
            map[i] = null;
        }
        size = 0;
    }

    private V getHelper(K key, Item item) {
        if (item == null) {
            return null;
        } else if (item.key.equals(key)) {
            return (V) item.val;
        } else {
            return (V) getHelper(key, item.next);
        }
    }
    @Override
    /** Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        int index = (key.hashCode() & 0x7fffffff) % initialSize;
        return (getHelper(key, map[index]) != null);
    }

    @Override
    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        int index = (key.hashCode() & 0x7fffffff) % initialSize;
        return getHelper(key, map[index]);
    }

    @Override
    /** Returns the number of key-value mappings in this map. */
    public int size(){
        return size;
    }

    private void putHelper(K key, V value, Item item) {
        if (item.key == key) {
            item.val = value;
        } else if (item.next == null) {
            item.next = new Item<>(key, value);
            size += 1;
        } else {
            putHelper(key, value, item.next);
        }
    }

    private void resize() {
        MyHashMap<K, V> temp = new MyHashMap<>(initialSize * 2);
        Set<K> keys = keySet();
        for (K key: keys) {
            temp.put(key, get(key));
        }
        initialSize = temp.initialSize;
        map = temp.map;
    }

    @Override
    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    public void put(K key, V value) {
        int index = (key.hashCode() & 0x7fffffff) % initialSize;
        if (map[index] == null) {
            map[index] = new Item<>(key, value);
            size += 1;
        } else {
            putHelper(key, value, map[index]);
        }

        if ((double) size / initialSize > loadFactor) {
            resize();
        }
    }

    @Override
    /** Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (int i = 0; i < initialSize; i += 1) {
            Item item = map[i];
            while (item != null) {
                keys.add((K) item.key);
                item = item.next;
            }
        }
        return keys;
    }

    @Override
    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator iterator() {
        return new MyHashMapIter();
    }

    /** An iterator that iterates over the keys of the dictionary. */
    private class MyHashMapIter implements Iterator<K> {
        private Item cur;
        private int index;
        /**
         * Create a new MyHashMapIter by setting cur to the first node in the
         * linked list that stores the key-value pairs.
         */
        MyHashMapIter() {
            index = 0;
            cur = getItem(map[index]);
        }

        @Override
        public boolean hasNext() {
            return (cur != null);
        }

        @Override
        public K next() {
            K ret = (K) cur.key;
            cur = getItem(cur.next);
            return ret;
        }

        private Item getItem(Item cur) {
            if (cur == null) {
                index += 1;
                if (index > initialSize - 1) {
                    return null;
                } else {
                    return getItem(map[index]);
                }
            } else {
                return cur;
            }
        }
    }
}
