package treasure_hunt;

import java.io.File;
import java.util.TreeSet;

import javax.swing.JLabel;

/**
 * <p><strong>Game</strong> is the class which manage a game party.<p>
 * <p>A Game is characterized by : </p>
 * <ul>
 * 		<li><dt>Severals hunters</dt>
 * 			<dd>- The hunters are stored in a TreeSet</dd></li> 
 * 		<li><dt>A cell matrix</dt>
 * 			<dd>- The matrix generated for this game</dd></li>
 * 		<li><dt>A playing mode</dt>
 * 			<dd>- Automatically of manually</dd></li>
 * </ul>
 * <p>The Game class is used to manage a game process without worrying about the map and players</p>
 * @see treasure_hunt.Hunter
 * @see treasure_hunt.Board
 * 
 * @author François Poguet
 * @author Enzo Costantini
 */
public class Game {
	private TreeSet<Hunter> hunters;
	private Board board;
	
	
	/**
	 * Game constructor from file
	 * @param file
	 * @param nbPlayers
	 * @throws Exception If the file is wrong
	 */
	public Game(File file, int nbPlayers) throws Exception {
		this.hunters = new TreeSet<Hunter>();
		this.board = new Board(file,this.hunters, nbPlayers);
	}
	
	/**
	 * Default Game constructor
	 * @param size			The map size for this game party
	 * @param nbPlayers		The number of players for this game party
	 * @param wallDensity	The walls density indicator
	 */
	public Game(int size, int nbPlayers,int wallDensity) {
		this.hunters = new TreeSet<Hunter>();
		this.board = new Board(size,hunters,nbPlayers,wallDensity);
	}
	
	
	public Game(Matrix<JLabel> labels, int nbPlayer) {
		this.hunters = new TreeSet<Hunter>();
		this.board = new Board(labels,hunters,nbPlayer);
	}	
	
	

	
	
	/**
	 * Create a new random board (same size)
	 * @param nbPlayers
	 */
	public void randomBoard(int nbPlayers,int wallDensity) {
		removeHunter();
		board.randomMap( wallDensity);
		board.setHunters(hunters, nbPlayers);
	}
	
	
	/**
	 * Replay the same map (just replace randomly hunters and reset treasure)
	 * @param nbPlayers
	 */
	public void replayGame(int nbPlayers) {
		removeHunter();
		this.board.resetMap(hunters, nbPlayers);
		
	}
	
	/**
	 * Remove all hunters
	 */
	public void removeHunter() {
		for(Hunter h : hunters) {
			h.getCurrentFloor().leave();
		}
		hunters.clear();
	}
	
	
	/**
	 * Getter for the board
	 * @return The game board
	 */
	public Board getBoard() {
		return this.board;
	}
	
	/**
	 * Getter for the hunters set
	 * @return	The hunters TreeSet
	 */
	public TreeSet<Hunter> getHunters(){
		return this.hunters;
	}
	
}
