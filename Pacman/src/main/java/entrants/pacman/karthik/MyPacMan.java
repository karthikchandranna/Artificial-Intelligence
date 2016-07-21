package entrants.pacman.karthik;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;

import pacman.controllers.PacmanController;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MyPacMan extends PacmanController {	
	public static ArrayList<Integer> eatenPills = new ArrayList<>();	
	
	@Override
	public MOVE getMove(Game game, long timeDue) {		
		int pacMan = game.getPacmanCurrentNodeIndex();	
		eatenPills.add(pacMan);
		System.out.println("CURRENT: " + pacMan);
        Node target = findTarget(pacMan, game);      
        System.out.println("TARGET");
        System.out.println(target.toString());
        return target.moves.get(0);
        //return game.getNextMoveTowardsTarget(pacMan, target.index, DM.PATH);   
	}

	private static Node findTarget(int pacMan, Game game) {		
		Game curGame = game.copy();
		List<Integer> allPills = getAllPills(game);
		int MAX_DEPTH = 20;
		List<Integer> visited = new ArrayList<>();
		Queue<Node> frontier = new LinkedList<>();
		MOVE[] possibleMoves = {MOVE.LEFT, MOVE.DOWN, MOVE.RIGHT, MOVE.UP};
		int depth = 0;
		frontier.add(new Node(pacMan,0,0,new LinkedList<MOVE>()));
		
		while(!frontier.isEmpty() && depth<MAX_DEPTH) {			
			Queue<Node> parents = frontier;
			frontier = new LinkedList<Node>();
			depth++;
			// for each node in the this depth
			for(Node parent : parents) {
				visited.add(parent.index);
				//for all possible moves from the current node
				for(MOVE move : possibleMoves) {
					int childIndex = curGame.getNeighbour(parent.index, move);	
					//check if the index is not an obstacle and its not visited
					if (childIndex>0 && !visited.contains(childIndex)) {
						int score = parent.score;
						// check if its a pill which is not eaten
						if (allPills.contains(childIndex) && !eatenPills.contains(childIndex))
							score += 1;
						List<MOVE> movesToReachNode = new LinkedList<>(parent.moves);						
						movesToReachNode.add(move);						
						Node childNode = new Node(childIndex, depth, score,	movesToReachNode);
						frontier.add(childNode);
					}
				}
			}			
		}
		
		// select the node with the highest score in the frontier at depth = MAX_DEPTH
		int maxScore = -1;
		Node targetNode = null;
		System.out.println(frontier);
		for(Node node : frontier) {			
			if(node.score > maxScore) {
				maxScore = node.score;
				targetNode = node;
			}
		}		
		return targetNode;
	}
	
	public static List<Integer> getAllPills(Game game) {
		int[] pills = game.getCurrentMaze().pillIndices;
		int[] powerPills = game.getCurrentMaze().powerPillIndices;
		List<Integer> allPills = new ArrayList<>();
		for (int i : pills) allPills.add(i);
		for (int j : powerPills) allPills.add(j);
		return allPills;
	}	
}
