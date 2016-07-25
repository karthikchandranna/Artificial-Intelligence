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
I have implemented Adversarial Search using MiniMax algorithm.

Let d be the depth of the Minimax tree that we are building.

The minimax tree is limited to a depth of 4.
The Pacman can move in only 4 possible ways.
Each of the ghosts can have 4 moves and since there are 4 ghosts, there can be 4^4 different combinations. 
So at depth 0 the branching factor is 1 since its the root node. 
At depth 1, since the pacman can make 4 moves, the branching factor is 4.
At depth 2, since the ghosts can make (4^4) moves, the branching factor is 4*(4^4)
At depth 3, the branching factor is 4*(4^4)*4 
At depth 4, it is 4*(4^4)*4*(4^4)
So total nodes will be 1 + 4 + 4*(4^4) + 4*(4^4)*4 + 4*(4^4)*4*(4^4) at depth 4.
So the time complexity at depth 4 is O(4*(4^4)*4*(4^4)) 
In general the time complexity can be written as -
O(((4^4)^(d/2))*4^(d/2)) => 16^d * 2^d => 32^d
Thus the time complexity is O(32^d)

Every node that is created is stored in memory till the very end as this is a recursive implementation. 
Hence the space complexity is 1 + 4 + 4*(4^4) + 4*(4^4)*4 + 4*(4^4)*4*(4^4)
In general the space complexity can be written as -
O(((4^4)^(d/2))*4^(d/2)) => 16^d * 2^d => 32^d
Thus the space complexity is O(32^d)
*/

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