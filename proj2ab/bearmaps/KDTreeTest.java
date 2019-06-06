package bearmaps;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {

    @Test
    public void basicTest() {
        ArrayList<Point> test = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Point p = new Point(StdRandom.uniform(), StdRandom.uniform());
            test.add(p);
        }
        KDTree kd = new KDTree(test);
    }

    @Test
    public void findRandomNearestTest() {
        ArrayList<Point> test = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            Point p = new Point(StdRandom.uniform(), StdRandom.uniform());
            test.add(p);
        }
        Stopwatch sw = new Stopwatch();
        KDTree kd = new KDTree(test);
        System.out.println("Constructor of KDTree costs: " + sw.elapsedTime() + " s");

        sw = new Stopwatch();
        NaivePointSet np = new NaivePointSet(test);
        System.out.println("Constructor of Native costs: " + sw.elapsedTime() + " s");

        sw = new Stopwatch();
        Point nearest = np.nearest(0.5, 0.5);
        System.out.println(nearest);
        System.out.println("Nearest of Native costs: " + sw.elapsedTime() + " s");

        sw = new Stopwatch();
        Point kdNearest = kd.nearest(0.5, 0.5);
        System.out.println(kdNearest);
        System.out.println("Nearest of KDTree costs: " + sw.elapsedTime() + " s");
        assertEquals(nearest, kdNearest);
    }

    @Test
    public void basicFinNearestTest(){
        ArrayList<Point> test = new ArrayList<>();
        test.add(new Point(4, 1));
        test.add(new Point(3, 7));
        test.add(new Point(5, 2));
        test.add(new Point(7, 5));
        test.add(new Point(5, 5));
        Stopwatch sw = new Stopwatch();
        KDTree kd = new KDTree(test);
        System.out.println("Constructor of KDTree costs: " + sw.elapsedTime() + " s");

        sw = new Stopwatch();
        NaivePointSet np = new NaivePointSet(test);
        System.out.println("Constructor of Native costs: " + sw.elapsedTime() + " s");

        sw = new Stopwatch();
        Point nearest = np.nearest(5, 5);
        System.out.println(nearest);
        System.out.println("Nearest of Native costs: " + sw.elapsedTime() + " s");

        sw = new Stopwatch();
        Point kdNearest = kd.nearest(5, 5);
        System.out.println(kdNearest);
        System.out.println("Nearest of KDTree costs: " + sw.elapsedTime() + " s");
        assertEquals(nearest, kdNearest);
    }
}
