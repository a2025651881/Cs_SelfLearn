package hw4.puzzle;

import hw4.lib.StdOut;

public class WordPuzzleSolver {
    /***********************************************************************
     * Test routine for your Solver class. Uncomment and run to test
     * your basic functionality.
     **********************************************************************/

    public static void main(String[] args) {
        String start = "cube";
        String goal = "tubes";

        Word startState = new Word(start, goal);
        Solver solver = new Solver(startState);

        StdOut.println("Minimum number of moves = " + solver.moves());
        for (WorldState ws : solver.solution()) {
            StdOut.println(ws);
        }
    }

}
