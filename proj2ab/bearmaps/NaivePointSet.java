package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {
    private List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    @Override
    public Point nearest(double x, double y) {
        Point r = new Point(x, y);
        Point result = points.get(0);
        double minDistence = Point.distance(r, result);
        for (Point p: points) {
            if (Point.distance(r, p) < minDistence) {
                minDistence = Point.distance(r, p);
                result = p;
            }
        }
        return result;
    }
}
