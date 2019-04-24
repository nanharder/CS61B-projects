public class LinkedListDeque<T> {
    /**
     * first item is at sentinel.next
     */
    private ItemNode sentinel;
    private int size;

    public class ItemNode {
        public ItemNode prev;
        public T item;
        public ItemNode next;

        public ItemNode(T s, ItemNode p, ItemNode n) {
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
    public LinkedListDeque(T x) {
        sentinel = new ItemNode(null, null, null);
        sentinel.next = new ItemNode(x, sentinel, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }

    /**
     * Creates a deep copy of other
     */
    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new ItemNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = other.size;
        ItemNode curr = other.sentinel.next;
        while (curr.item != null) {
            sentinel.prev = new ItemNode(curr.item, sentinel.prev, sentinel);
            curr = curr.next;
        }
    }

    /**
     * Adds an item of type T to the front of the deque
     */
    public void addFirst(T item) {
        sentinel.next = new ItemNode(item, sentinel, sentinel.next);
        size += 1;
    }

    /**
     * Adds an item of type T to the back of the deque.
     */
    public void addLast(T item) {
        sentinel.prev = new ItemNode(item, sentinel.prev, sentinel);
        size += 1;
    }

    /**
     * Returns true if deque is empty, false otherwise.
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
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
        ItemNode curr = sentinel;
        while (curr.item != null) {
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
        ItemNode first = sentinel.next;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return first.item;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    public T removeLast() {
        ItemNode last = sentinel.prev;
        sentinel.prev = sentinel.prev.prev;
        size -= 1;
        return last.item;

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
        return (T)copy.getRecursiveHelper(index);
    }
}