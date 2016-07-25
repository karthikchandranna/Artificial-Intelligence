package entrants.pacman.karthik;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pacman.controllers.PacmanController;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
/**
 * MiniMaxPacMan class is used to traverse the possible game states and 
 * return the move to the closest node using MiniMax Search algorithm.  
 * @author Karthik Chandranna
 */
public class MiniMaxPacMan extends PacmanController {

	private Integer MAX_DEPTH = 4;

	@Override
	public MOVE getMove(Game game, long timeDue) {
		MiniMaxNode currentNode = new MiniMaxNode(MAX_DEPTH , computeScore(game.copy()), null, game.copy());
		MiniMaxNode target = miniMax(currentNode, MAX_DEPTH, true);	
		return target.move;
	}

	/**
	 * Finds the target node based on Minimax algorithm.
	 * Reference - https://en.wikipedia.org/wiki/Minimax
	 * @param node The current node whose best move is to be found out
	 * @param depth The depth of the tree to traverse
	 * @param isPacMan Whether the node is a Maximizing Player or not.
	 * @return The target node which has the best score based on Minimax algorithm
	 */
	private MiniMaxNode miniMax(MiniMaxNode node, int depth, boolean isPacMan) {
		// return the node if its a leaf node or if the game ended
		if(depth == 0 || node.gameState.gameOver())
			return node;
		//pacman's turn
		if(isPacMan) {
			MiniMaxNode maxNode = new MiniMaxNode(true);
			Game game1 = node.gameState.copy();
			// create a child node for every possible move of the pacman
			for(MOVE move : game1.getPossibleMoves(game1.getPacmanCurrentNodeIndex())) {
				Game gameCopy = game1.copy();
				gameCopy.updatePacMan(move);
				gameCopy.updateGame();
				MOVE nextMove = node.move;
				if(depth == MAX_DEPTH)
					nextMove = move;	
				MiniMaxNode child = new MiniMaxNode(depth-1, computeScore(gameCopy), nextMove, gameCopy);
				MiniMaxNode result = miniMax(child, depth-1, false);
				// find the node which has the maximum score
				maxNode = findMaxNode(maxNode, result);
			}
			return maxNode;
		}
		// ghosts' turn
		else {
			MiniMaxNode minNode = new MiniMaxNode(false);
			Game game2 = node.gameState.copy();
			Set<EnumMap<GHOST, MOVE>> setOfGhostMoves = getSetOfGhostsMoves(game2);
			// create a child node for every possible move of every ghost
			for(EnumMap<GHOST, MOVE> ghostMoves : setOfGhostMoves) {
				Game gameCopy = game2.copy();
				gameCopy.updateGhosts(ghostMoves);
				gameCopy.updateGame();		
				MiniMaxNode child = new MiniMaxNode(depth-1,computeScore(gameCopy),node.move, gameCopy);
				MiniMaxNode result = miniMax(child, depth-1, true);
				// find the node which has the minimum score
				minNode = findMinNode(minNode, result);	
			}			
			return minNode;
		}		
	}

	/**
	 * This method returns the node with the higher score
	 * @param node1 The first node
	 * @param node2 The second node
	 * @return The node which has the higher score
	 */
	private MiniMaxNode findMaxNode(MiniMaxNode node1, MiniMaxNode node2) {
		if(node1.score >= node2.score)
			return node1;
		return node2;
	}

	/**
	 * This method returns the node with the lower score
	 * @param node1 The first node
	 * @param node2 The second node
	 * @return The node which has the lower score
	 */
	private MiniMaxNode findMinNode(MiniMaxNode node1, MiniMaxNode node2) {		
		if(node1.score <= node2.score)
			return node1;
		return node2;
	}
	
	/**
	 * This methods an array of possible moves for a given ghost
	 * @param game The current game state
	 * @param ghost The ghost
	 * @return An array of possible moves
	 */
	private static MOVE[] getGhostMoves(Game game, GHOST ghost) {
        MOVE[] moves = game.getPossibleMoves(game.getGhostCurrentNodeIndex(ghost));
        if (moves.length <= 0 )        
            return new MOVE[] {MOVE.NEUTRAL};        
        return moves;
    }	
	
	/**
	 * This method returns all the combinations of every single move of every ghost
	 * @param game The current game state
	 * @return A set of combinations of every ghost and every move
	 */
	private static Set<EnumMap<GHOST, MOVE>> getSetOfGhostsMoves(Game game) {
		Set<EnumMap<GHOST, MOVE>> setOfGhostsMoves = new HashSet<>();
		for(MOVE moveBlinky : getGhostMoves(game, GHOST.BLINKY)) {
			for(MOVE movePinky : getGhostMoves(game, GHOST.PINKY)) {
				for(MOVE moveInky : getGhostMoves(game, GHOST.INKY)) {
					for(MOVE moveSue : getGhostMoves(game, GHOST.SUE)) {
						EnumMap<GHOST, MOVE> ghostMoves = new EnumMap<GHOST, MOVE>(GHOST.class);
						ghostMoves.put(GHOST.BLINKY,moveBlinky);
						ghostMoves.put(GHOST.PINKY,movePinky);
						ghostMoves.put(GHOST.INKY,moveInky);
						ghostMoves.put(GHOST.SUE,moveSue);	
						setOfGhostsMoves.add(ghostMoves);
					}
				}
			}
		}
		return setOfGhostsMoves;		
	}
	
	/**
	 * This method computes the game score based on certain heuristics
	 * @param game The current game state
	 * @return The computed score in int
	 */
	private static int computeScore(Game game) {
		int pacManIndex = game.getPacmanCurrentNodeIndex();
		// least score if pacMan is eaten
        if (game.wasPacManEaten())            
            return Integer.MIN_VALUE; 
        
        // find closest active pill
        int closestPillDist = Integer.MAX_VALUE;
        int[] activePills = game.getActivePillsIndices();
        int[] activePowerPills = game.getActivePowerPillsIndices();
        for (int i = 0; i < activePills.length; i++) {
        	if(game.getShortestPathDistance(pacManIndex, activePills[i]) < closestPillDist)
        		closestPillDist = game.getShortestPathDistance(pacManIndex, activePills[i]);
        }
        for (int i = 0; i < activePowerPills.length; i++) {
        	if(game.getShortestPathDistance(pacManIndex, activePowerPills[i]) < closestPillDist)
        		closestPillDist = game.getShortestPathDistance(pacManIndex, activePowerPills[i]);
        }
        
        // find the closest ghost and the distance to it
        HashMap<GHOST, Integer> ghostsDistMap = new HashMap<>();       
        ghostsDistMap.put(GHOST.BLINKY, game.getShortestPathDistance(pacManIndex, game.getGhostCurrentNodeIndex(GHOST.BLINKY)));
        ghostsDistMap.put(GHOST.INKY, game.getShortestPathDistance(pacManIndex, game.getGhostCurrentNodeIndex(GHOST.INKY)));
        ghostsDistMap.put(GHOST.PINKY, game.getShortestPathDistance(pacManIndex, game.getGhostCurrentNodeIndex(GHOST.PINKY)));
        ghostsDistMap.put(GHOST.SUE, game.getShortestPathDistance(pacManIndex, game.getGhostCurrentNodeIndex(GHOST.SUE)));
        
        int minDistGhost = Integer.MAX_VALUE;
        GHOST closestGhost = null;        
        for (Map.Entry<GHOST, Integer> ghostDist : ghostsDistMap.entrySet()) {
            if (ghostDist.getValue() <= minDistGhost)
            	closestGhost = ghostDist.getKey();            
        }
        // reduce score if closest ghost is very close
        int dangerousGhostScore = 600 * (25 - minDistGhost);
        if (minDistGhost > 25 || game.isGhostEdible(closestGhost))
        	dangerousGhostScore = 0;
        
        // increase score if closest blue ghost is very close
        int edibleGhostScore = 0;
        if (minDistGhost < 50 && game.isGhostEdible(closestGhost))
        	edibleGhostScore = 100 * (50 - minDistGhost); 
        
        // reduce score if many active pills are in the maze
        int activePillsCount = game.getNumberOfActivePills() + game.getNumberOfActivePowerPills();
        // add weight to original game score (As my heuristics could be wrong !!)
        int originalGameScore = game.getScore()*100;
        // add up all the scores
        int finalScore = originalGameScore + edibleGhostScore - dangerousGhostScore - activePillsCount - closestPillDist;
        return finalScore;
    }	
}
