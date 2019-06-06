package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    private Node root;

    private class Node {
        Point self;
        Node[] next;
        boolean isHorizontal;

        Node(Point p, boolean direction) {
            self = p;
            next = new Node[2];
            isHorizontal = direction;
        }
    }

    private int findNext(Node node, Point p) {
        int indicator;
        if (node.isHorizontal) {
            indicator = Double.compare(p.getY(), node.self.getY());
        } else {
            indicator = Double.compare(p.getX(), node.self.getX());
        }

        if (indicator == -1) {
            return 0;
        } else {
            return 1;
        }
    }
    private void addHelp(Node node, Point p) {
        int position = findNext(node, p);

        if (node.next[position] != null) {
            addHelp(node.next[position], p);
        } else {
            node.next[position] = new Node(p, !node.isHorizontal);
        }
    }

    public KDTree(List<Point> points) {
        root = new Node(points.get(0), false);
        for (int i = 1; i < points.size(); i ++) {
            addHelp(root, points.get(i));
        }
    }

    private boolean comparePossibleDistance(Node n, Point ref, double minDistance) {
        Point p;
        if (n.isHorizontal) {
            p = new Point(ref.getX(), n.self.getY());
        } else {
            p = new Point(n.self.getX(), ref.getY());
        }
        return Point.distance(ref, p) < minDistance;
    }

    private Point findNearest(Point ref, double minDistance, Node n, Point min) {
        /* check the Node n */
        if (Point.distance(ref, n.self) < minDistance) {
            minDistance = Point.distance(ref, n.self);
            min = n.self;
        }

        int goodSide = findNext(n, ref);
        int badSide = 1 - goodSide;
        if (n.next[goodSide] != null) {
            min = findNearest(ref, minDistance, n.next[goodSide], min);
        }
        double md = Point.distance(min, ref);

        if (n.next[badSide] != null && comparePossibleDistance(n, ref, minDistance)) {
            min = findNearest(ref, md, n.next[badSide], min);
        }
        return min;
    }

    @Override
    public Point nearest(double x, double y) {
        Point ref = new Point(x, y);
        double minDistance = Point.distance(ref, root.self);
        return findNearest(ref, minDistance, root, root.self);
    }
}
