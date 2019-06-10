import edu.princeton.cs.algs4.Queue;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<String> tas = new Queue<>();
        tas.enqueue("A");
        tas.enqueue("Queue");
        tas.enqueue("Items");
        tas.enqueue("Of");
        tas = QuickSort.quickSort(tas);
        assertTrue(isSorted(tas));
    }

    @Test
    public void testMergeSort() {
        Queue<String> tas = new Queue<>();
        tas.enqueue("A");
        tas.enqueue("Queue");
        tas.enqueue("Items");
        tas.enqueue("Of");
        tas = MergeSort.mergeSort(tas);
        assertTrue(isSorted(tas));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
