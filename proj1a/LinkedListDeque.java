public class LinkedListDeque<T> {
    /** first item is at sentinel.next */
    private ItemNode sentinel;
    private int size;

    private class ItemNode {
        private ItemNode prev;
        private T item;
        private ItemNode next;

        private ItemNode(T s, ItemNode p, ItemNode n) {
            prev = p;
            item = s;
            next = n;
        }
    }

    /**
     * Create an empty Deque
     */
    public LinkedListDeque() {
        sentinel = new ItemNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /**
     * Create a new Deque
     */
    private LinkedListDeque(T x) {
        sentinel = new ItemNode(null, null, null);
        sentinel.next = new ItemNode(x, sentinel, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }

    /** Creates a deep copy of other */
    private LinkedListDeque(LinkedListDeque other) {
        LinkedListDeque copy = new LinkedListDeque();
        size = other.size;
        ItemNode curr = other.sentinel.next;
        while (!curr.equals(other.sentinel)) {
            copy.addLast(curr.item);
            curr = curr.next;
        }
        sentinel = copy.sentinel;
    }
    //不只要sentinel的两端，还有另一边也要修改
    /**
     * Adds an item of type T to the front of the deque
     */
    public void addFirst(T item) {
        ItemNode toFront = new ItemNode(item, sentinel, sentinel.next);
        sentinel.next.prev = toFront;
        sentinel.next = toFront;
        size += 1;
    }

    /**
     * Adds an item of type T to the back of the deque.
     */
    public void addLast(T item) {
        ItemNode toEnd = new ItemNode(item, sentinel.prev, sentinel);
        sentinel.prev.next = toEnd;
        sentinel.prev = toEnd;
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Returns the number of items in the deque.
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    public void printDeque() {
        ItemNode curr = sentinel.next;
        while (!curr.equals(sentinel)) {
            System.out.print(curr.item);
            System.out.print(" ");
            curr = curr.next;
        }
        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T result = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return result;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T result = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return result;

    }

    /**
     * Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists, returns null.
     */
    public T get(int index) {
        if (index >= size) {
            return null;
        } else {
            ItemNode curr = sentinel.next;
            while (index > 0) {
                curr = curr.next;
                index -= 1;
            }
            return curr.item;
        }
    }

    private T getRecursiveHelper(int index) {
        if (index == 0) {

            return sentinel.next.item;
        } else {
            removeFirst();
            return getRecursiveHelper(index - 1);
        }
    }

    /**
     * Same as get, but uses recursion
     */
    public T getRecursive(int index) {
        LinkedListDeque copy = new LinkedListDeque(this);
        return (T) copy.getRecursiveHelper(index);
    }
}
