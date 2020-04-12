package model;

import java.util.TreeSet;

/**
 * <p><strong>Game</strong> is the class which manage a game party.<p>
 * <p>A Game is characterized by : </p>
 * <ul>
 * 		<li><dt>Severals hunters</dt>
 * 			<dd>- The hunters are stored in a TreeSet</dd></li> 
 * 		<li><dt>A cell matrix</dt>
 * 			<dd>- The matrix generated for this game</dd></li>
 * </ul>
 * <p>The Game class is used to manage a game process without worrying about the map and players</p>
 * @see model.Hunter
 * @see model.Board
 * 
 * @author François Poguet
 */
public class Game {
	private TreeSet<Hunter> hunters;
	private Board board;
	
	/**
	 * Default Game constructor
	 * @param size		The map size for this game party
	 * @param nbPlayers	The number of players for this game party
	 */
	public Game(int size, int nbPlayers) {
		this.hunters = new TreeSet<Hunter>();
		this.board = new Board(size,hunters,nbPlayers);
	}
	
	/**
	 * Execute a move of each hunter
	 */
	public void execute() {
		for(Hunter h : hunters) {
			h.move(this.board);
		}
	}
	
	/**
	 * Execute the entire game party
	 */
	public void play() throws InterruptedException {
		while(this.board.getTreasure().getWinner() == null) {
			//while(true) {
				this.execute();
				Thread.sleep(150);
				System.out.println(board);
			//}
			
		}
	}
	
	/**
	 * Execute the game ending and display result
	 */
	public void result() {
		
	}

	
	
}
