package entrants.pacman.karthik;
import java.util.LinkedList;
import java.util.List;

import pacman.game.Constants.MOVE;

/**
 * The Node class is used to store the node information. 
 * @author Karthik Chandranna
 */
public class Node {

	Integer index;
	Integer depth;
	Integer score;
	List<MOVE> moves;
	
	public Node() {
		this.index = 0;
		this.depth = 0;
		this.score = 0;
		this.moves = new LinkedList<>();
	}
	
	public Node(Integer index, Integer depth, Integer score, List<MOVE> moves) {		
		this.index = index;
		this.depth = depth;
		this.score = score;
		this.moves = moves;
	}

	@Override
	public String toString() {
		return "Node [index=" + index + ", depth=" + depth + ", score=" + score + ", moves=" + moves;
	}	
}
