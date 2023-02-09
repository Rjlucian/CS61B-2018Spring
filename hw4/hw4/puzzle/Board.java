package hw4.puzzle;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Board implements WorldState {

    private int[][] tiles;

    public Board(int[][] tiles) {
        this.tiles = new int[tiles.length][];
        for (int i = 0; i < tiles.length; i++) {
            this.tiles[i] = new int[tiles.length];
            System.arraycopy(tiles[i], 0, this.tiles[i], 0, tiles.length);
        }
    }

    private Board(int[][] tiles, int sign) {
        this.tiles = tiles;
    }

    /**
     *
     * @param i tile's row
     * @param j tile's column
     * @return value of tile at row i, column j, or 0 if blank
     */
    public int tileAt(int i, int j) {
        validate(i);
        validate(j);
        return tiles[i][j];
    }

    /**
     * @return the board size of N
     */
    public int size() {
        return tiles.length;
    }


    @Override
    public Iterable<WorldState> neighbors() {
        Set<WorldState> set = new HashSet<>();
        int place = findBlank();
        int blankRow = toI(place);
        int blankColumn = toJ(place);
        if (inBounds(blankRow, blankColumn - 1)) {
            int[][] newTiles = newArray(tiles);
            newTiles[blankRow][blankColumn] = newTiles[blankRow][blankColumn - 1];
            newTiles[blankRow][blankColumn - 1] = 0;
            WorldState neighbor = new Board(newTiles, 1);
            set.add(neighbor);
        }
        if (inBounds(blankRow + 1, blankColumn)) {
            int[][] newTiles = newArray(tiles);
            newTiles[blankRow][blankColumn] = newTiles[blankRow + 1][blankColumn];
            newTiles[blankRow + 1][blankColumn] = 0;
            WorldState neighbor = new Board(newTiles, 1);
            set.add(neighbor);
        }
        if (inBounds(blankRow, blankColumn + 1)) {
            int[][] newTiles = newArray(tiles);
            newTiles[blankRow][blankColumn] = newTiles[blankRow][blankColumn + 1];
            newTiles[blankRow ][blankColumn + 1] = 0;
            WorldState neighbor = new Board(newTiles, 1);
            set.add(neighbor);
        }
        if (inBounds(blankRow - 1, blankColumn)) {
            int[][] newTiles = newArray(tiles);
            newTiles[blankRow][blankColumn] = newTiles[blankRow - 1][blankColumn];
            newTiles[blankRow - 1][blankColumn] = 0;
            WorldState neighbor = new Board(newTiles, 1);
            set.add(neighbor);
        }
        return set;
    }

    public int hamming() {
        int ans = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (tileAt(i, j) != 0 && tileAt(i, j) != ijTo1D(i, j) + 1) {
                    ans++;
                }
            }
        }
        return ans;
    }

    public int manhattan() {
        int ans = 0;

        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (tileAt(i, j) == 0) {
                    continue;
                }
                int curNum = tileAt(i, j);
                int correctI = getCorrectI(curNum);
                int correctJ = getCorrectJ(curNum);
                ans += (Math.abs(i - correctI) + Math.abs(j - correctJ));
            }
        }
        return ans;
    }

    private int getCorrectI(int num) {
        return (num - 1) / size();
    }

    private int getCorrectJ(int num) {
        return (num - 1) % size();
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Board that = (Board) obj;
        if (this.size() != that.size()) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N).append("\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }


    private void validate(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index: " + index + "out of bounds");
        }
    }

    private int ijTo1D(int i, int j) {
        validate(i);
        validate(j);
        return i * size() + j;
    }

    private int toI(int num) {
        return num / size();
    }

    private int toJ(int num) {
        return num % size();
    }

    private int findBlank() {
        int row = 0;
        int column = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (tiles[i][j] == 0) {
                    row = i;
                    column = j;
                }
            }
        }
        return ijTo1D(row, column);
    }

    private boolean inBounds(int i, int j) {
        return i >= 0 && i < size() && j >= 0 && j < size();
    }

    private static int[][] newArray(int[][] arr) {
        int[][] na = new int[arr.length][];
        for (int i = 0; i < arr.length; i++) {
            na[i] = new int[arr.length];
            System.arraycopy(arr[i], 0, na[i], 0, arr.length);
        }
        return na;
    }

}
