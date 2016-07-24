package entrants.pacman.karthik;

import pacman.game.Constants.MOVE;
import pacman.game.Game;

/**
 * The MiniMaxNode class is used to store the node information of the nodes in a MiniMax tree. 
 * @author Karthik Chandranna
 */
public class MiniMaxNode {
	Integer depth;
	Integer score;
	MOVE move;
	Game gameState;
	
	public MiniMaxNode() {
		this.depth = 0;
		this.score = 0;
		this.move = null;
		this.gameState = null;
	}	
	
	public MiniMaxNode(boolean isMaxNode) {
		this.depth = 0;
		if(isMaxNode)
			this.score = Integer.MIN_VALUE;
		else
			this.score = Integer.MAX_VALUE;
		this.move = null;
		this.gameState = null;
	}

	public MiniMaxNode(Integer depth, Integer score, MOVE move, Game gameState) {
		this.depth = depth;
		this.score = score;
		this.move = move;
		this.gameState = gameState;
	}

	@Override
	public String toString() {
		return "MiniMaxNode [depth=" + depth + ", score="
				+ score + ", move=" + move + ", gameState=" + gameState + "]";
	}
}
