package entrants.pacman.karthik;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import pacman.controllers.PacmanController;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getMove() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., entrants.pacman.username).
 */
public class MyPacMan extends PacmanController {
    private MOVE myMove = MOVE.NEUTRAL;
    private Random random = new Random();
    List<Integer> visited = new ArrayList<>();
    LinkedHashSet<Integer> frontier=new LinkedHashSet<>();
    public MOVE getMove(Game game, long timeDue) {    	

        // Should always be possible as we are PacMan
        int current = game.getPacmanCurrentNodeIndex();
        
        // pills having the same x or y co-ords as the pacman and also having no obstacles between the pacman and pill
        int[] pills = game.getPillIndices();
        int[] powerPills = game.getPowerPillIndices();
        
        //ArrayList<Integer> targets = new ArrayList<Integer>();

        for (int i = 0; i < pills.length; i++) {  
        	if(!visited.contains(i))
        		frontier.add(pills[i]);            
        }
        
        for (int i = 0; i < powerPills.length; i++) {  
        	if(!visited.contains(i))
        		frontier.add(powerPills[i]);            
        }
        
        visited.add(current);
        System.out.println(frontier);
        int nextPill=-1;
        for(int pill: frontier) {
        	nextPill = pill;
        	break;
        }
        frontier.remove(nextPill);
        return game.getNextMoveTowardsTarget(current, nextPill, DM.PATH);
        
        
        /*public void bfs(Node root) {
		Queue<Integer> frontier = new LinkedList<Node>();
		//root.visited = true;
		//visit(root);
		frontier.add(current); // Add to end of queue

		while(!frontier.isEmpty()) {
			Node r = frontier.remove(); // Remove from front of queue
			for(Node n : r.adjacent) {
				if (n.visited == false) {
					//visit(n);
					n.visited = true;
					frontier.add(n);
				}
			}
		}	
	}*/

        /*ArrayList<Integer> targets = new ArrayList<Integer>();

        for (int i = 0; i < pills.length; i++) {
            //check which pills are available
            Boolean pillStillAvailable = game.isPillStillAvailable(i);
            if (pillStillAvailable == null) continue;
            if (game.isPillStillAvailable(i)) {
                targets.add(pills[i]);
            }
        }

        for (int i = 0; i < powerPills.length; i++) {            //check with power pills are available
            Boolean pillStillAvailable = game.isPillStillAvailable(i);
            if (pillStillAvailable == null) continue;
            if (game.isPowerPillStillAvailable(i)) {
                targets.add(powerPills[i]);
            }
        }*/

       /* if (!targets.isEmpty()) {
            int[] targetsArray = new int[targets.size()];        //convert from ArrayList to array

            for (int i = 0; i < targetsArray.length; i++) {
                targetsArray[i] = targets.get(i);
            }
            //return the next direction once the closest target has been identified
            return game.getNextMoveTowardsTarget(current, game.getClosestNodeIndexFromNodeIndex(current, targetsArray, DM.PATH), DM.PATH);
        }
        // Strategy 4: New PO strategy as now S3 can fail if nothing you can see
        // Going to pick a random action here
        MOVE[] moves = game.getPossibleMoves(current, game.getPacmanLastMoveMade());
        if (moves.length > 0) {
            return moves[random.nextInt(moves.length)];
        }*/
        //return myMove;
    }
}