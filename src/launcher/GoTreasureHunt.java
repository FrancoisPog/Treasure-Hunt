package launcher;

import java.util.Arrays;

import model.Board;

public class GoTreasureHunt {
	
	public static void main(String[] args) throws InterruptedException {
		Board board = new Board(60,1);
		
		
		
		
		
		while(true) {
			System.out.println(board);
			board.init(15);
			Thread.sleep(150);
		}
		
	
		
		//board.hunters_dump();
		
	}
}
