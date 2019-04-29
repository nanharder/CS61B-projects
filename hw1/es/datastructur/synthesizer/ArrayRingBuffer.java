package es.datastructur.synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T []) new Object[capacity];
        fillCount = 0;
        first = 0;
        last = 0;

    }

    private int checkNum(int n) {
        if (n > capacity() - 1) {
            return n - capacity();
        } else {
            return n;
        }
    }
    /**
     * return the fillcount of queue
     */
    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * return the capacitu of queue
     */
    @Override
    public int capacity() {
        return rb.length;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring Buffer overflow");
        }
        rb[last] = x;
        last = checkNum(last + 1);
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw  new RuntimeException("Ring Buffer underflow");
        }
        T de = rb[first];
        rb[first] = null;
        first = checkNum(first + 1);
        fillCount -= 1;
        return de;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        return rb[first];
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        ArrayRingBuffer<T> other = (ArrayRingBuffer<T>) o;
        if (other.capacity() != this.capacity()) {
            return false;
        }
        Iterator<T> otherIterator = other.iterator();
        for (T item : this) {
            if (item != otherIterator.next()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int wizpos;
        ArrayRingBufferIterator() {
            wizpos = 0;
        }

        @Override
        public boolean hasNext() {
            return (checkNum(wizpos + first) < last);
        }

        @Override
        public T next() {
            return rb[checkNum(wizpos + first)];
        }
    }
}

