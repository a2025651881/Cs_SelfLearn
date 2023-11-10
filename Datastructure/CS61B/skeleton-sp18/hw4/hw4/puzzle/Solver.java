package hw4.puzzle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;

import hw4.lib.MinPQ;

public class Solver {
    private ArrayList<WorldState> arrayList = new ArrayList<WorldState>();

    /**
     * PQnode
     */
    public class MyComparator implements Comparator<node> {
        @Override
        public int compare(node o1, node o2) {
            return o1.world.estimatedDistanceToGoal() - o2.world.estimatedDistanceToGoal();
        }

    }

    public class node {
        WorldState world;
        int moves;
        node prenode;

        public node(WorldState world, int moves, node prenode) {
            this.world = world;
            this.moves = moves;
            this.prenode = prenode;
        }
    }

    /*
     * Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial) {
        MinPQ<node> pq = new MinPQ<node>(new MyComparator());
        node first = new node(initial, 0, null);
        pq.insert(first);
        while (!pq.isEmpty()) {
            node Qnode = pq.delMin();
            if (Qnode.world.isGoal()) {
                addToList(Qnode);
                break;
            }
            for (WorldState i : Qnode.world.neighbors()) {
                node temp = new node(i, Qnode.moves + 1, Qnode);
                pq.insert(temp);
            }
        }

    }

    private void addToList(node Qnode) {
        Stack<WorldState> worldStack = new Stack<WorldState>();
        while (Qnode.prenode != null) {
            worldStack.push(Qnode.world);
            Qnode = Qnode.prenode;
        }
        worldStack.push(Qnode.world);
        while (!worldStack.empty()) {
            arrayList.add(worldStack.pop());
        }
    }

    // Returns the minimum number of moves to solve the puzzle starting
    // at the initial WorldState.
    public int moves() {
        return arrayList.size() - 1;
    }

    // Returns a sequence of WorldStates from the initial WorldState
    // to the solution.
    public Iterable<WorldState> solution() {
        return arrayList;
    }

}
