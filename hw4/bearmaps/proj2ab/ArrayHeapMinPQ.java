package bearmaps.proj2ab;

import java.util.*;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<Node> heap;
    private HashMap<T, Integer> toFindIndex;
    private int size = 0;

    private class Node {
        private double priority;
        private T item;

        Node(double p, T i) {
            priority = p;
            item = i;
        }

        T getItem() {
            return item;
        }
    }

    public ArrayHeapMinPQ(){
        heap = new ArrayList<>();
        toFindIndex = new HashMap<>();
    }

    private int leftChild(int index) {
        return 2 * index + 1;
    }

    private int rightChild(int index) {
        return 2 * index + 2;
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private Node getNode(int index) {
        if (index >= size) {
            return null;
        }
        return heap.get(index);
    }

    private void exchange(int index1, int index2) {
        toFindIndex.put(heap.get(index1).getItem(), index2);
        toFindIndex.put(heap.get(index2).getItem(), index1);
        Collections.swap(heap, index1, index2);
    }

    private void moveUp(int index) {
        int par = parent(index);
        if (getNode(par).priority > getNode(index).priority) {
            exchange(index, par);
            moveUp(par);
        }
    }

    @Override
    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentExceptionb if item is already present.
     * You may assume that item is never null. */
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        Node node = new Node(priority, item);
        size += 1;
        heap.add(node);
        toFindIndex.put(item, size - 1);
        moveUp(size - 1);
    }

    @Override
    /* Returns true if the PQ contains the given item. */
    public boolean contains(T item) {
        return (toFindIndex.getOrDefault(item, -1) >= 0);
    }

    @Override
    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        return  heap.get(0).getItem();
    }

    private int getMin(int index1, int index2) {
        Node l = getNode(index1);
        Node r = getNode(index2);
        if (l == null) {
            return index2;
        } else if (r == null) {
            return index1;
        } else if (l.priority > r.priority) {
            return index2;
        } else {
            return index1;
        }
    }
    private void sink(int index) {
        int left = leftChild(index);
        int right = rightChild(index);
        int min = getMin(index, getMin(left, right));
        if (index == min) {
            return;
        } else {
            exchange(index, min);
            sink(min);
        }
    }

    @Override
    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    public T removeSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        T item = heap.get(0).getItem();
        Collections.swap(heap, 0, size - 1);
        heap.remove(size - 1);
        size -= 1;
        toFindIndex.remove(item);
        if (size > 0) {
            sink(0);
        }
        return item;
    }

    @Override
    /* Returns the number of items in the PQ. */
    public int size() {
        return size;
    }

    @Override
    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new IllegalArgumentException();
        }
        int index = toFindIndex.get(item);
        double oldPriority = getNode(index).priority;
        heap.get(index).priority = priority;
        if (priority > oldPriority) {
            sink(index);
        } else if (priority < oldPriority){
            moveUp(index);
        }
    }

}
