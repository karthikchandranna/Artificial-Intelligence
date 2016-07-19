package entrants.pacman.karthik;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import pacman.controllers.PacmanController;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;

public class InformedPacMan extends PacmanController {
    
    Set<Integer> visited = new HashSet<>();
    public MOVE getMove(Game game, long timeDue) {    	

        // Should always be possible as we are PacMan
        int current = game.getPacmanCurrentNodeIndex();        
        // pills having the same x or y co-ords as the pacman and also having no obstacles between the pacman and pill
        int[] pills = game.getPillIndices();        
        TreeMap<Integer, Integer> map = new TreeMap<>();
        System.out.println("current-" + current);
        visited.add(current);
        System.out.println("pills");
        for (int i = 0; i < pills.length; i++) {  
        	System.out.println(pills[i]);       		          
        }
        System.out.println("pills not visited");
        for (int i = 0; i < pills.length; i++) {  
        	if(!visited.contains((pills[i])))
        		map.put(game.getManhattanDistance(current, pills[i]),pills[i]);
        }         
        if(map.isEmpty()) {
        	int[] allPills = game.getCurrentMaze().pillIndices;
        	for(int i = 0; i < allPills.length; i++) {
        		if(!visited.contains((allPills[i])))
        			map.put(game.getManhattanDistance(current, allPills[i]),allPills[i]);
        	}
        }               
        int nextPill = map.firstEntry().getValue();
        System.out.println("nextPill-" + nextPill);
        System.out.println(visited);
        return game.getNextMoveTowardsTarget(current, nextPill, DM.PATH);         
    }
}
