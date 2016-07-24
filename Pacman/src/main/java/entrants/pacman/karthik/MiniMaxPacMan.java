package entrants.pacman.karthik;

import java.util.EnumMap;
import pacman.controllers.PacmanController;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MiniMaxPacMan extends PacmanController {

	private Integer MAX_DEPTH = 4;

	@Override
	public MOVE getMove(Game game, long timeDue) {
		MiniMaxNode currentNode = new MiniMaxNode(MAX_DEPTH , game.getScore(), null, game.copy());
		MiniMaxNode target = miniMax(currentNode, MAX_DEPTH, true);	
		System.out.println("Target: " + target);
		//System.exit(-1);
		return target.move;
	}

	private MiniMaxNode miniMax(MiniMaxNode node, int depth, boolean isPacMan) {
		if(depth == 0 || isGameOver(node.gameState))
			return node;
		if(isPacMan) {
			//System.out.println("Pacman");
			MiniMaxNode maxNode = new MiniMaxNode(true);
			Game game1 = node.gameState.copy();
			for(MOVE move : game1.getPossibleMoves(game1.getPacmanCurrentNodeIndex())) {
				Game gameCopy = game1.copy();
				gameCopy.updatePacMan(move);
				gameCopy.updateGame();
				MOVE nextMove = node.move;
				if(depth == MAX_DEPTH)
					nextMove = move;	
				MiniMaxNode child = new MiniMaxNode(depth-1, gameCopy.getScore(), nextMove, gameCopy);
				//System.out.println("PM Child: " + child);
				MiniMaxNode result = miniMax(child, depth-1, false);
				//System.out.println("PM Result: " + result);
				maxNode = findMaxNode(maxNode, result);
			}
			return maxNode;
		}
		else {
			//System.out.println("Ghosts");
			MiniMaxNode minNode = new MiniMaxNode(false);
			Game game2 = node.gameState.copy();//copy not required actually
			//int ghost1Index = game2.getGhostCurrentNodeIndex(GHOST.values()[0]);
			for(MOVE moveGhost1 : getGhostMoves(game2, GHOST.values()[0])) {
				//System.out.println("Ghost1: " + moveGhost1);
				//int ghost2Index = game2.getGhostCurrentNodeIndex(GHOST.values()[1]);
				for(MOVE moveGhost2 : getGhostMoves(game2, GHOST.values()[1])) {
					//System.out.println("inside Ghost2: " + moveGhost2);
					//int ghost3Index = game2.getGhostCurrentNodeIndex(GHOST.values()[2]);
					for(MOVE moveGhost3 : getGhostMoves(game2, GHOST.values()[2])) {
						//System.out.println("inside Ghost3: " + moveGhost3);
						//int ghost4Index = game2.getGhostCurrentNodeIndex(GHOST.values()[3]);
						for(MOVE moveGhost4 : getGhostMoves(game2, GHOST.values()[3])) {
							//System.out.println("inside Ghost4: " + moveGhost4);
							Game gameCopy = game2.copy();
							EnumMap<GHOST, MOVE> ghostMoves = new EnumMap<GHOST, MOVE>(GHOST.class);
							ghostMoves.put(GHOST.values()[0],moveGhost1);
							ghostMoves.put(GHOST.values()[1],moveGhost2);
							ghostMoves.put(GHOST.values()[2],moveGhost3);
							ghostMoves.put(GHOST.values()[3],moveGhost4);
							//System.out.println("hello ghost");
							gameCopy.updateGhosts(ghostMoves);
							gameCopy.updateGame();		
							MiniMaxNode child = new MiniMaxNode(depth-1,gameCopy.getScore(),node.move, gameCopy);
							//System.out.println("GH Child: " + child);
							MiniMaxNode result = miniMax(child, depth-1, true);
							//System.out.println("GH result: " + result);
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
	
	private MOVE[] getGhostMoves(Game game, GHOST ghost) {
        MOVE[] moves = game.getPossibleMoves(game.getGhostCurrentNodeIndex(ghost), game.getGhostLastMoveMade(ghost));
        if (moves.length <= 0 ) {            
            return new MOVE[] {MOVE.NEUTRAL};
        }
        return moves;
    }
	
	private boolean isGameOver(Game game) {
        return (game.gameOver() || game.wasPacManEaten() || 
        		(game.getNumberOfActivePills() == 0 && game.getNumberOfActivePowerPills() == 0));
    }

}
