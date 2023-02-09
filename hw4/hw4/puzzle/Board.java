package hw4.puzzle;

public class Board implements WorldState {

    public Board(int[][] tile) {

    }

    public int tileAt(int i, int j) {

    }

    public int size() {

    }

    @Override
    public Iterable<WorldState> neighbors() {

    }

    public int hamming() {

    }

    public int manhattan() {

    }

    @Override
    public int estimatedDistanceToGoal() {

    }

    @Override
    public boolean equals(Object obj) {

    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
