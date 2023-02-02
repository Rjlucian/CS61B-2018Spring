package lab11.graphs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

import java.util.ArrayList;
import java.util.List;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        dfs();
    }

    private void dfs() {
        int pre = -1;
        marked[0] = true;
        announce();
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        while (!stack.isEmpty()) {
            int vertices = stack.peek();
            boolean flag = false;
            for (int w : maze.adj(vertices)) {
                if (!marked[w]) {
                    flag = true;
                    marked[w] = true;
                    announce();
                    pre = vertices;
                    stack.push(w);
                    break;
                } else if (w != pre) {
                    // find a cycle
                    List<Integer> list = new ArrayList<>();
                    while (stack.peek() != w) {
                        list.add(stack.pop());
                    }
                    list.add(stack.pop());
                    addEdge(list);
                    return;
                }
            }
            if (!flag) {
                stack.pop();
            }
        }
    }
    private void addEdge(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            edgeTo[list.get(i)] = list.get((i + 1) % list.size());
        }
        announce();
    }
}

