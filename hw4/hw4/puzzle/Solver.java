package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Solver {

    private int moves;
    private final MinPQ<SearchNode> pq;
    private final Map<WorldState, Integer> distances;
    private final ArrayList<WorldState> path;

    public Solver(WorldState initial) {
        pq = new MinPQ<>();
        SearchNode node = new SearchNode(initial, 0, null);
        pq.insert(node);
        distances = new HashMap<>();
        distances.put(initial, initial.estimatedDistanceToGoal());
        path = new ArrayList<>();
        solve();
    }

    public int moves() {
        return moves;
    }

    public Iterable<WorldState> solution() {
        return path;
    }

    private void solve() {
        SearchNode currentNode;
        while (true) {
            currentNode = pq.delMin();
            if (currentNode.current.isGoal()) {
                break;
            }
            for (WorldState neighbor : currentNode.current.neighbors()) {
                if (currentNode.previous != null && neighbor.equals(currentNode.previous.current)) {
                    continue;
                }
                distances.put(neighbor, neighbor.estimatedDistanceToGoal());
                pq.insert(new SearchNode(neighbor, currentNode.m + 1, currentNode));
            }
        }
        getAnswer(currentNode);
    }

    private void getAnswer(SearchNode goal) {
        moves = goal.m;
        while (goal != null) {
            path.add(goal.current);
            goal = goal.previous;
        }
        Collections.reverse(path);
    }

    private class SearchNode implements Comparable<SearchNode> {
        private final WorldState current;
        private final int m;
        private final SearchNode previous;

        SearchNode(WorldState current, int moves, SearchNode previous) {
            this.current = current;
            this.m = moves;
            this.previous = previous;
        }

        @Override
        public int compareTo(SearchNode other) {
            return m + distances.get(this.current) - other.m - distances.get(other.current);
        }
    }
}
