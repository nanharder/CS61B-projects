package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.introcs.Stopwatch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private LinkedList<Vertex> solution;
    private double solutionWeight;
    private int numStatesExplored;
    private double explorationTime;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        solution = new LinkedList<>();
        solutionWeight = 0;
        numStatesExplored = 0;

        ArrayHeapMinPQ<Vertex> fringe = new ArrayHeapMinPQ<>();
        HashMap<Vertex, Double> bestDist = new HashMap<>();
        HashMap<Vertex, Vertex> edgeTo = new HashMap<>();
        fringe.add(start, 0);
        bestDist.put(start, 0.0);
        Vertex source = start;

        while(fringe.size() != 0 && fringe.getSmallest() != end) {
            Vertex p = fringe.removeSmallest();
            numStatesExplored += 1;
            List<WeightedEdge<Vertex>> neighborEdges = input.neighbors(p);
            for (WeightedEdge e : neighborEdges) {
                Vertex f = (Vertex) e.from();
                double w = e.weight();
                Vertex t = (Vertex) e.to();
                if (bestDist.get(f) + w < bestDist.getOrDefault(t, Double.POSITIVE_INFINITY)) {
                    bestDist.put(t, bestDist.get(f) + e.weight());
                    if (fringe.contains(t)) {
                        fringe.changePriority(t, bestDist.get(t) + input.estimatedDistanceToGoal(t, end));
                    } else {
                        fringe.add(t, bestDist.get(t) + input.estimatedDistanceToGoal(t, end));
                    }
                    edgeTo.put(t, f);
                }
            }
        }

        explorationTime = sw.elapsedTime();
        if (fringe.size() == 0) {
            outcome = SolverOutcome.UNSOLVABLE;
            solution = new LinkedList<>();
            solutionWeight = 0;
        } else {
            outcome = SolverOutcome.SOLVED;
            solutionWeight = bestDist.get(end);
            Vertex v = end;
            while (v != null) {
                solution.addFirst(v);
                v = edgeTo.get(v);
            }
        }


    }


    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    @Override
    public double explorationTime() {
        return explorationTime;
    }
}
