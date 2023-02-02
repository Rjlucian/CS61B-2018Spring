package lab11.graphs;

import lab11.heap.ArrayHeap;


/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private final int start;
    private final int target;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        start = maze.xyTo1D(sourceX, sourceY);
        target = maze.xyTo1D(targetX, targetY);
        distTo[start] = 0;
        edgeTo[start] = start;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int x = maze.toX(v);
        int y = maze.toY(v);
        int tx = maze.toX(target);
        int ty = maze.toY(target);
        return Math.abs(x - tx) + Math.abs(y- ty);
    }

    private void init(ArrayHeap<Integer> heap, int start) {
        for (int i = 0; i < maze.N() * maze.N(); i++) {
            heap.insert(i, Integer.MAX_VALUE);
        }
        heap.changePriority(start, 0);
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        ArrayHeap<Integer> fringe = new ArrayHeap<>();
        init(fringe, s);
        while (!fringe.empty()) {
            int vertices = fringe.removeMin();
            marked[vertices] = true;
            announce();
            for (int w : maze.adj(vertices)) {
                if (distTo[vertices] + 1 < distTo[w]) {
                    distTo[w] = distTo[vertices] + 1;
                    edgeTo[w] = vertices;
                    fringe.changePriority(w, h(w) + distTo[w]);
                }
            }
            if (vertices == target) {
                return;
            }
        }

    }

    @Override
    public void solve() {
        astar(start);
    }


}

