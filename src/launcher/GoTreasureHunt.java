package launcher;

import model.Game;

public class GoTreasureHunt {
	
	public static void main(String[] args) throws InterruptedException {
		Game game = null;
		if(args.length != 0) {
			game = new Game(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
		}else {
			game = new Game(15,2);
		}
		game.play();
		
	
		
		
		
	}
}
