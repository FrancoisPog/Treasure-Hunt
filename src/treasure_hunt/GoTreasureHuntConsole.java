package treasure_hunt;

public class GoTreasureHuntConsole {
	
	
	public static void main(String[] args) throws InterruptedException {
		
		Game game = Game.welcome();
		game.play();
		game.result();
	}
}
