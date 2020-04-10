package launcher;

import model.Board;

public class GoTreasureHunt {
	
	public static void main(String[] args) throws InterruptedException {
		Board board = new Board(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
		board.play();
		
	
		
		//board.hunters_dump();
		
	}
}
