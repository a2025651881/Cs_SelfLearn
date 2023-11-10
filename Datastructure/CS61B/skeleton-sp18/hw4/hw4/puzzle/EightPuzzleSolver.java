package hw4.puzzle;

import hw4.lib.In;
import hw4.lib.StdOut;

public class EightPuzzleSolver {
    /***********************************************************************
     * Test routine for your Solver class. Uncomment and run to test
     * your basic functionality.
     **********************************************************************/

    public static void main(String[] args) {

        int N = 2;
        int[][] tiles = { { 1, 2 }, { 0, 3 } };
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (WorldState ws : solver.solution()) {
            StdOut.println(ws);
        }
    }

}
