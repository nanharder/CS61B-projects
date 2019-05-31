package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    @Test
    public void addTest() {
        ArrayHeapMinPQ<String> b = new ArrayHeapMinPQ<>();
        NaiveMinPQ<String> c = new NaiveMinPQ<>();
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 1000; i += 1) {
            b.add("hi" + i, StdRandom.uniform());
            assertEquals(b.size(), i + 1);
        }
        System.out.println("Test:Add total time elapsed: " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        for (int i = 0; i < 1000; i += 1) {
            c.add("hi" + i, StdRandom.uniform());
            assertEquals(c.size(), i + 1);
        }
        System.out.println("Reference:Add total time elapsed: " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        for (int i = 0; i < 1000; i += 1) {
            assertTrue(b.contains("hi" + i));
            assertFalse(b.contains("hj" + i));
        }
        System.out.println("Test:Contains total time elapsed: " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        for (int i = 0; i < 1000; i += 1) {
            assertTrue(c.contains("hi" + i));
            assertFalse(c.contains("hj" + i));
            }
        System.out.println("Reference:Contains total time elapsed: " + sw.elapsedTime() +  " seconds.");
    }

    @Test
    public void testGetSmallest() {
        ArrayHeapMinPQ<String> b = new ArrayHeapMinPQ<>();
        NaiveMinPQ<String> c = new NaiveMinPQ<>();
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 10000; i += 1) {
            b.add("hi" + i, -i);
            assertEquals(b.getSmallest(), "hi" + i);
        }
        System.out.println("Test:Add total time elapsed: " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        for (int i = 9999; i >= 0; i -= 1) {
            assertEquals(b.removeSmallest(), "hi" + i);
        }
        System.out.println("Test:remove total time elapsed: " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        for (int i = 0; i < 10000; i += 1) {
            c.add("hi" + i, -i);
            c.getSmallest();
        }
        System.out.println("Reference:Add total time elapsed: " + sw.elapsedTime() +  " seconds.");

        sw = new Stopwatch();
        for (int i = 9999; i >= 0; i -= 1) {
            assertEquals(c.removeSmallest(), "hi" + i);
        }
        System.out.println("Reference:remove total time elapsed: " + sw.elapsedTime() +  " seconds.");
    }

    @Test
    public void changePiriotyTest() {
        ArrayHeapMinPQ<String> b = new ArrayHeapMinPQ<>();
        NaiveMinPQ<String> c = new NaiveMinPQ<>();
        for (int i = 0; i < 10000; i += 1) {
            b.add("hi" + i, -i);
        }

        Stopwatch sw = new Stopwatch();
        for (int i = 9999; i >= 1; i -= 1) {
            b.changePriority("hi" + i, i);
            assertEquals(b.getSmallest(), "hi" + (i - 1));
        }
        System.out.println("Test:Add total time elapsed: " + sw.elapsedTime() +  " seconds.");

        for (int i = 0; i < 10000; i += 1) {
            c.add("hi" + i, -i);
        }

        sw = new Stopwatch();
        for (int i = 9999; i >= 1; i -= 1) {
            c.changePriority("hi" + i, i);
            assertEquals(c.getSmallest(), "hi" + (i - 1));
        }
        System.out.println("Reference:Add total time elapsed: " + sw.elapsedTime() +  " seconds.");
    }


}
