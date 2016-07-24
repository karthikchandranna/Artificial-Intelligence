import entrants.pacman.karthik.MiniMaxPacMan;
import entrants.pacman.karthik.MyBFSPacMan;
import entrants.pacman.karthik.MyGBFSPacMan;
import examples.commGhosts.POCommGhosts;
import pacman.controllers.examples.RandomGhosts;
import pacman.Executor;


/**
 * Created by pwillic on 06/05/2016.
 */
public class Main {

    public static void main(String[] args) {

        Executor executor = new Executor(false, true);
        // Latest implementations are MyBFSPacMan and MyGBFSPacMan        
        //executor.runGameTimed(new MyBFSPacMan(), new RandomGhosts(), true);
        executor.runGameTimed(new MiniMaxPacMan(), new POCommGhosts(50), true);
        
        //Ignore UninformedPacMan.java and InformedPacMan.java
    }
}

/*
I have implemented Uninformed Search using Breadth First Search (BFS)
Space and Time complexity - 
Let b be the branching factor of the graph i.e the average out-degree for a node in the graph.
Let d be the depth of the graph until which I will explore the goal state.

The worst case Time complexity is given by O(b ^ d+1) 
The worst case Space complexity is given by O(b ^ d+1)

So in the algorithm implemented in this solution, the max depth was set to 20 and the branching factor at
max can be 4 since every node can have only 4 neighbors. So the Space and Time complexity is O(4 ^ 21).
*/

/*
I have implemented Informed Search using Greedy Best First Search (GBFS).
Space and Time complexity - 
Let b be the branching factor of the graph i.e the average out-degree for a node in the graph.
Let m be the depth of the shallowest goal.

The worst case Time complexity is given by O(b ^ m) 
The worst case Space complexity is given by O(b ^ m)

So in the algorithm implemented in this solution, the branching factor at max can be 4 since every node 
can have only 4 neighbors, and in worst case the shallowest goal can be at depth 20. 
So the Space and Time complexity is O(4 ^ 20).
*/