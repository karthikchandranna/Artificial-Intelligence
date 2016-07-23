package entrants.pacman.karthik;

import java.util.EnumMap;
import pacman.controllers.PacmanController;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MiniMaxPacMan extends PacmanController {

	private Integer MAX_DEPTH = 10;

	@Override
	public MOVE getMove(Game game, long timeDue) {
		MiniMaxNode currentNode = new MiniMaxNode(MAX_DEPTH , game.getScore(), null, game.copy());
		MiniMaxNode target = miniMax(currentNode, MAX_DEPTH, true);		
		return target.move;
	}

	private MiniMaxNode miniMax(MiniMaxNode node, int depth, boolean isPacMan) {
		if(depth == 0)
			return node;
		if(isPacMan) {
			MiniMaxNode maxNode = new MiniMaxNode(true);
			Game game = node.gameState;
			for(MOVE move : game.getPossibleMoves(game.getPacmanCurrentNodeIndex())) {
				Game gameCopy = game.copy();
				gameCopy.updatePacMan(move);
				gameCopy.updateGame();
				MOVE nextMove = node.move;
				if(depth == MAX_DEPTH)
					nextMove = move;	
				MiniMaxNode child = new MiniMaxNode(depth-1, gameCopy.getScore(), nextMove, gameCopy);
				MiniMaxNode result = miniMax(child, depth - 1, false);
				maxNode = findMaxNode(maxNode, result);
			}
			return maxNode;
		}
		else {
			MiniMaxNode minNode = new MiniMaxNode(false);
			Game game = node.gameState.copy();//copy not required actually
			int ghost1Index = game.getGhostCurrentNodeIndex(GHOST.values()[0]);
			for(MOVE moveGhost1 : game.getPossibleMoves(ghost1Index)) {
				int ghost2Index = game.getGhostCurrentNodeIndex(GHOST.values()[1]);
				for(MOVE moveGhost2 : game.getPossibleMoves(ghost2Index)) {
					int ghost3Index = game.getGhostCurrentNodeIndex(GHOST.values()[2]);
					for(MOVE moveGhost3 : game.getPossibleMoves(ghost3Index)) {
						int ghost4Index = game.getGhostCurrentNodeIndex(GHOST.values()[3]);
						for(MOVE moveGhost4 : game.getPossibleMoves(ghost4Index)) {
							Game gameCopy = game.copy();
							EnumMap<GHOST, MOVE> ghostMoves = new EnumMap<GHOST, MOVE>(GHOST.class);
							ghostMoves.put(GHOST.values()[0],moveGhost1);
							ghostMoves.put(GHOST.values()[1],moveGhost2);
							ghostMoves.put(GHOST.values()[2],moveGhost3);
							ghostMoves.put(GHOST.values()[3],moveGhost4);
							gameCopy.updateGhosts(ghostMoves);
							gameCopy.updateGame();		
							MiniMaxNode child = new MiniMaxNode(depth-1,gameCopy.getScore(),node.move, gameCopy);
							MiniMaxNode result = miniMax(child, depth - 1, true);
							minNode = findMinNode(minNode, result);							
						}
					}
				}
			}		
			return minNode;
		}		
	}

	private MiniMaxNode findMaxNode(MiniMaxNode node1, MiniMaxNode node2) {
		if(node1.score >= node2.score)
			return node1;
		return node2;
	}

	private MiniMaxNode findMinNode(MiniMaxNode node1, MiniMaxNode node2) {
		if(node1.score <= node2.score)
			return node1;
		return node2;
	}
}
