package lab11.graphs;

import edu.princeton.cs.algs4.In;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private final int start;
    private final int target;
    private boolean targetFound;


    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        start = maze.xyTo1D(sourceX, sourceY);
        target = maze.xyTo1D(targetX, targetY);
        targetFound = false;
        distTo[start] = 0;
        edgeTo[start] = start;

    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        marked[start] = true;
        announce();
        Queue<Integer> fringe = new ArrayDeque<>();
        fringe.add(start);
        while (!fringe.isEmpty()) {
            int vertices = fringe.remove();
            for (int w : maze.adj(vertices)) {
                if (!marked[w]) {
                    fringe.add(w);
                    marked[w] = true;
                    distTo[w] = distTo[vertices] + 1;
                    edgeTo[w] = vertices;
                    announce();
                    if (w == target) {
                        return;
                    }
                }
            }
        }


    }


    @Override
    public void solve() {
        bfs();
    }

}

