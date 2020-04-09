package launcher;

import model.Board;

public class GoTreasureHunt {
	
	public static void main(String[] args) {
		Board board = new Board(17,3);
		
		while(true) {
			System.out.println(board);
			board.init(15);
		}
		
	
		
		//board.hunters_dump();
		
	}
}
