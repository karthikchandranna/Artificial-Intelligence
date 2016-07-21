package entrants.pacman.karthik;
import java.util.LinkedList;
import java.util.List;

import pacman.game.Constants.MOVE;

public class Node {

	Integer index;
	Integer depth;
	Integer score;
	//Node parent;
	List<MOVE> moves;
	//Game gameState;
	
	public Node() {
		this.index = 0;
		this.depth = 0;
		this.score = 0;
		//this.parent = null;
		this.moves = new LinkedList<>();
		//this.gameState = null;
	}
	
	public Node(int index) {
		this.index = index;
		this.depth = 0;
		this.score = 0;
		//this.parent = null;
		this.moves = new LinkedList<>();
		//this.gameState = null;
	}
	
	public Node(Integer index, Integer depth, Integer score, List<MOVE> moves) {		
		this.index = index;
		this.depth = depth;
		this.score = score;
		//this.parent = parent;
		this.moves = moves;
		//this.gameState = gameState;
	}

	@Override
	public String toString() {
		return "Node [index=" + index + ", depth=" + depth + ", score=" + score
				+ ", moves=" + moves;
	}	
}
