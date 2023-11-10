package hw4.puzzle;

import java.util.ArrayList;

public class Board implements WorldState {
    public int[][] boardTiles;
    public int N;

    // Constructs a board from an N-by-N array of tiles where
    // tiles[i][j] = tile at row i, column j
    public Board(int[][] tiles) {
        boardTiles = tiles;
        N = tiles[0].length;
    }

    // Returns value of tile at row i, column j (or 0 if blank)
    public int tileAt(int i, int j) {
        if (i > N - 1 || j > N - 1)
            throw new ArrayIndexOutOfBoundsException();
        return boardTiles[i][j];
    }

    // Returns the board size N
    public int size() {
        return N;
    }

    // Returns the neighbors of the current board
    public Iterable<WorldState> neighbors() {
        ArrayList<WorldState> neighors = new ArrayList<WorldState>();

        int pos = 0;
        for (int i = 0; i < N * N; i++) {
            if (0 == boardTiles[i / N][i % N]) {
                pos = i;
                break;
            }
        }
        // upper
        if (pos / N != 0) {
            int[][] copyTiles = new int[N][N];
            for (int i = 0; i < N * N; i++) {
                copyTiles[i / N][i % N] = boardTiles[i / N][i % N];
            }
            copyTiles[pos / N][pos % N] = copyTiles[pos / N - 1][pos % N];
            copyTiles[pos / N - 1][pos % N] = 0;
            WorldState temp1 = new Board(copyTiles);
            neighors.add(temp1);
        }
        // behind
        if (pos / N != N - 1) {
            int[][] copyTiles = new int[N][N];
            for (int i = 0; i < N * N; i++) {
                copyTiles[i / N][i % N] = boardTiles[i / N][i % N];
            }
            copyTiles[pos / N][pos % N] = copyTiles[pos / N + 1][pos % N];
            copyTiles[pos / N + 1][pos % N] = 0;
            WorldState temp2 = new Board(copyTiles);
            neighors.add(temp2);
        }
        // left
        if (pos % N != 0) {
            int[][] copyTiles = new int[N][N];
            for (int i = 0; i < N * N; i++) {
                copyTiles[i / N][i % N] = boardTiles[i / N][i % N];
            }
            copyTiles[pos / N][pos % N] = copyTiles[pos / N][pos % N - 1];
            copyTiles[pos / N][pos % N - 1] = 0;
            WorldState temp3 = new Board(copyTiles);
            neighors.add(temp3);
        }
        // right
        if (pos % N != N - 1) {
            int[][] copyTiles = new int[N][N];
            for (int i = 0; i < N * N; i++) {
                copyTiles[i / N][i % N] = boardTiles[i / N][i % N];
            }
            copyTiles[pos / N][pos % N] = copyTiles[pos / N][pos % N + 1];
            copyTiles[pos / N][pos % N + 1] = 0;
            WorldState temp4 = new Board(copyTiles);
            neighors.add(temp4);
        }
        return neighors;
    }

    // Hamming estimate described below
    public int hamming() {
        int wrongSize = 0;
        for (int i = 0; i < N * N - 1; i++) {
            if (i + 1 != boardTiles[i / N][i % N])
                wrongSize++;
        }
        return wrongSize;
    }

    // Manhattan estimate described below
    public int manhattan() {
        int manHattan = 0;
        for (int i = 0; i < (N * N - 1); i++) {
            int temp = boardTiles[i / N][i % N];
            if (i + 1 != temp && temp != 0) {
                manHattan += Math.abs((temp - 1) / N - i / N) + Math.abs((temp - 1) % N - i % N);
            }
        }
        int temp = boardTiles[N - 1][N - 1];
        if (temp != 0) {
            manHattan += Math.abs((temp - 1) / N - (N - 1)) + Math.abs((temp - 1) % N - (N - 1));
        }
        return manHattan;
    }

    // Estimated distance to goal. This method should
    // simply return the results of manhattan() when submitted to
    // Gradescope.
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    // Returns true if this board's tile values are the same
    // position as y's
    public boolean equals(Object y) {
        Board o = (Board) y;
        for (int i = 0; i < N * N; i++) {
            if (o.boardTiles[i / N][i % N] != this.boardTiles[i / N][i % N])
                return false;
        }
        return true;
    }

    /**
     * Returns the string representation of the board.
     * Uncomment this method.
     */

    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
