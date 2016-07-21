package entrants.pacman.karthik;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import pacman.controllers.PacmanController;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getMove() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., entrants.pacman.username).
 */
public class UninformedPacMan extends PacmanController {
    
    Set<Integer> visited = new HashSet<>();
    Queue<Integer> frontier=new LinkedList<>();
    public MOVE getMove(Game game, long timeDue) {    	

        // Should always be possible as we are PacMan
        int current = game.getPacmanCurrentNodeIndex();        
        // pills having the same x or y co-ords as the pacman and also having no obstacles between the pacman and pill
        // Partially observable environment. Returns pills in the line of sight of PacMan.
        int[] pills = game.getPillIndices();
        int[] powerPills = game.getPowerPillIndices();
        // Marking the current node index as visited
        visited.add(current);
        //checking if the neighbors of the current index are visited
        for (int i = 0; i < pills.length; i++) {  
        	if(!visited.contains(pills[i]) && !frontier.contains(pills[i]))
        		// adding the pill to the frontier, as its not visited
        		frontier.add(pills[i]);            
        }        
        for (int i = 0; i < powerPills.length; i++) {  
        	if(!visited.contains(powerPills[i]) && !frontier.contains(powerPills[i]))
        		frontier.add(powerPills[i]);            
        }  
        if(frontier.contains(current)){
        	frontier.remove(current);
        }        
        // Select the first element of the Frontier as the Target
        int nextPill= frontier.remove();
        return game.getNextMoveTowardsTarget(current, nextPill, DM.PATH);     
    }
}