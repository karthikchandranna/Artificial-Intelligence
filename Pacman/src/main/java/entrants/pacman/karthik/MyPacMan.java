package entrants.pacman.karthik;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

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
    //private MOVE myMove = MOVE.NEUTRAL;
    //private Random random = new Random();
    List<Integer> visited = new ArrayList<>();
    LinkedHashSet<Integer> frontier=new LinkedHashSet<>();
    public MOVE getMove(Game game, long timeDue) {    	

        // Should always be possible as we are PacMan
        int current = game.getPacmanCurrentNodeIndex();
        
        // pills having the same x or y co-ords as the pacman and also having no obstacles between the pacman and pill
        int[] pills = game.getPillIndices();
        int[] powerPills = game.getPowerPillIndices();
        
        for (int i = 0; i < pills.length; i++) {  
        	if(!visited.contains(i))
        		frontier.add(pills[i]);            
        }
        
        for (int i = 0; i < powerPills.length; i++) {  
        	if(!visited.contains(i))
        		frontier.add(powerPills[i]);            
        }
        
        visited.add(current);
        
        if(frontier.contains(current)){
        	frontier.remove(current);
        }
        
        //System.out.println(frontier);
        int nextPill=-1;
        for(int pill: frontier) {
        	nextPill = pill;
        	break;
        }
        
        return game.getNextMoveTowardsTarget(current, nextPill, DM.PATH);       
     
    }
}