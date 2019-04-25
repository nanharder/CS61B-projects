public class ArrayDeque<T> {
    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int size;

    /** Create an empty ArrayDeque */
    public ArrayDeque() {
        items = (T []) new Object[8];
        nextFirst = 0;
        nextLast = 0;
        size = 0;
    }

    /** Create an ArrayDeque */
    private ArrayDeque(T item) {
        items = (T []) new Object[8];
        items[0] = item;
        nextFirst = items.length - 1;
        nextLast = size;
        size = 1;
    }

    private int checkChange(int num) {
        if (num < 0) {
            return num + items.length;
        } else if (num >= items.length) {
            return num - items.length;
        } else {
            return num;
        }
    }
    /** Resize the Array */
    private void resize(int capacity) {
        T[] copy = (T []) new Object[capacity];
        if (size == 0) {
            items = copy;
            nextLast = 0;
            nextFirst = 0;
        } else {
            if (nextLast - nextFirst <= 1) {
                int firstSize = items.length - nextFirst - 1;
                System.arraycopy(items, nextFirst + 1, copy, 0, firstSize);
                System.arraycopy(items, 0, copy, firstSize, size - firstSize);
            } else {
                System.arraycopy(items, nextFirst + 1, copy, 0, size);
            }
            items = copy;
            nextLast = size;
            nextFirst = items.length - 1;
        }
    }

    /**
     * Adds an item of type T to the front of the deque
     */
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        if (size == 0 && nextFirst == nextLast) {
            nextLast = checkChange(nextLast + 1);
        }
        nextFirst = checkChange(nextFirst - 1);
        size += 1;
    }

    /**
     * Adds an item of type T to the back of the deque.
     */
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = item;
        if (size == 0 && nextFirst == nextLast) {
            nextFirst = checkChange(nextFirst - 1);
        }
        nextLast = checkChange(nextLast + 1);
        size += 1;
    }

    /**
     * Returns true if deque is empty, false otherwise.
     */
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
        int i = nextFirst + 1;
        while (i < size) {
            System.out.print(items[i]);
            System.out.print(" ");
            i += 1;
        }
        i = 0;
        while (i < nextLast) {
            System.out.print(items[i]);
            System.out.print(" ");
            i += 1;
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
        nextFirst = checkChange(nextFirst + 1);
        T result = items[nextFirst];
        items[nextFirst] = null;
        size -= 1;
        if ((double) size / items.length < 0.25 && items.length >= 16) {
            resize(items.length / 2);
        }
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
        nextLast = checkChange(nextLast - 1);
        T result = items[nextLast];
        items[nextLast] = null;
        size -= 1;
        if ((double) size / items.length < 0.25 && items.length >= 16) {
            resize(items.length / 2);
        }
        return result;

    }

    /**
     * Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists, returns null.
     */
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        if (nextLast - nextFirst <= 1) {
            return items[checkChange(index + nextFirst + 1)];
        } else {
            return items[index - nextFirst + 1];
        }
    }

    /** Creating a deep copy means that you create an entirely new ArrayDeque */
    private ArrayDeque(ArrayDeque other) {
        items = (T []) new Object[other.items.length];
        System.arraycopy(other.items, 0, items, 0, other.size);
        nextFirst = other.nextFirst;
        nextLast = other.nextLast;
        size = other.size;
    }

}
