package entrants.pacman.karthik;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;

import pacman.controllers.PacmanController;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.internal.Maze;
/**
 * MyGBFSPacMan class is used to traverse the possible game states and 
 * return the move to the closest node in a GBFS fashion. (Informed Search)
 * @author Karthik Chandranna
 */
public class MyGBFSPacMan extends PacmanController {	
	public static HashMap<Maze,ArrayList<Integer>> eatenPillsPerMaze = new HashMap<>();	

	@Override
	public MOVE getMove(Game game, long timeDue) {		
		int pacMan = game.getPacmanCurrentNodeIndex();
		ArrayList<Integer> eatenPills = new ArrayList<>();
		// Maintain a list of eaten pills for each maze. 
		if(eatenPillsPerMaze.containsKey(game.getCurrentMaze()))
			eatenPills = eatenPillsPerMaze.get(game.getCurrentMaze());		
		eatenPills.add(pacMan);
		eatenPillsPerMaze.put(game.getCurrentMaze(), eatenPills);
		Node target = findTargetGBFS(pacMan, game);
		if(target != null)			
			return target.moves.get(0);		
		// rarely when the pacMan cannot see any pills, guess the available pills.
		else {		
			List<Integer> allPills = getAllPills(game);
			int[] availablePills = getAvailablePills(allPills, game.getCurrentMaze());		
			int newTargetIndex = game.getClosestNodeIndexFromNodeIndex(pacMan, availablePills, DM.PATH);
			return game.getNextMoveTowardsTarget(pacMan, newTargetIndex, DM.PATH);
		}
	}

	/**
	 * This method traverses through all the possible nodes from the current index in a GBFS fashion
	 * and returns the target node which is at a certain depth having the highest score.
	 * At each level, the algorithm picks the node closest to the Goal.
	 * @param pacMan The current index of the pacMan
	 * @param game The current game state
	 * @return The target node which has the highest score at a certain depth.
	 */
	private static Node findTargetGBFS(int pacMan, Game game) {		
		Game curGame = game.copy();
		List<Integer> allPills = getAllPills(game);
		int MAX_DEPTH = 20;
		int GOAL = MAX_DEPTH/4;
		List<Integer> visited = new ArrayList<>();
		Queue<Node> frontier = new LinkedList<>();
		MOVE[] possibleMoves = {MOVE.LEFT, MOVE.DOWN, MOVE.RIGHT, MOVE.UP};
		int depth = 0;
		frontier.add(new Node(pacMan,0,0,new LinkedList<MOVE>()));
		// loop until the frontier is empty or until certain depth is reached
		while(!frontier.isEmpty() && depth<MAX_DEPTH) {			
			Queue<Node> parents = frontier;
			frontier = new LinkedList<Node>();
			depth++;
			int distanceFromGoal = Integer.MAX_VALUE;
			int curMaxScore = -1;
			// being greedy here. Selecting the nodes which are closet to the goal
			for(Node parent : parents) {	
				if((GOAL - parent.score) < distanceFromGoal) {
					distanceFromGoal = GOAL - parent.score;
					curMaxScore = parent.score;
				}				
			}	
			for(Node parent : parents) {
				// return the node if it satisfies the GOAL.
				if(parent.score >= GOAL)
					return parent;
				visited.add(parent.index);
				// rejecting the nodes who are far from the goal.
				if(parent.score < curMaxScore)
					continue;
				//for all possible moves from the current node
				for(MOVE move : possibleMoves) {
					int childIndex = curGame.getNeighbour(parent.index, move);	
					//check if the index is not an obstacle and its not visited
					if (childIndex>0 && !visited.contains(childIndex)) {
						int score = parent.score;
						ArrayList<Integer> eatenPills = eatenPillsPerMaze.get(curGame.getCurrentMaze());
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
		for(Node node : frontier) {			
			if(node.score > maxScore) {
				maxScore = node.score;
				targetNode = node;
			}
		}	
		if(targetNode == null || targetNode.score <= 0)
			return null;
		return targetNode;
	}

	/**
	 * This method returns a list of all the pills in the current maze,
	 * irrespective of whether they are eaten or not.
	 * @param game The current game state
	 * @return List of all the pills in the current maze
	 */
	private static List<Integer> getAllPills(Game game) {
		int[] pills = game.getCurrentMaze().pillIndices;
		int[] powerPills = game.getCurrentMaze().powerPillIndices;
		List<Integer> allPills = new ArrayList<>();
		for (int i : pills) allPills.add(i);
		for (int j : powerPills) allPills.add(j);
		return allPills;
	}	

	/**
	 * This method returns a list of the available pills in the current maze. 
	 * @param allPills List of all the pills in the current maze
	 * @param maze 
	 * @return List of available pills in the current maze
	 */
	private static int[] getAvailablePills(List<Integer> allPills, Maze maze) {
		List<Integer> availablePillsList = new ArrayList<>();
		ArrayList<Integer> eatenPills = eatenPillsPerMaze.get(maze);
		// Check for each pill in the available pills if it's already eaten
		for(Integer pill : allPills) {
			if(!eatenPills.contains(pill)) {
				availablePillsList.add(pill);
			}
		}
		// converting ArrayList to int array
		int[] availablePills = new int[availablePillsList.size()];
		int i = 0;
		for(Integer pill : availablePillsList) {
			availablePills[i] = pill;
			i++;
		}
		return availablePills;
	}
}
