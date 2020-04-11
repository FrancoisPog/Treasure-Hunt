package model;

import java.util.TreeSet;

/**
 * <p><strong>Board</strong> is the class which manage a game party.<p>
 * <p>A board is characterized by : </p>
 * <ul>
 * 		<li><dt>Severals hunters</dt>
 * 			<dd>- The hunters are stored in a TreeSet</dd></li> 
 * 		<li><dt>A cell matrix</dt>
 * 			<dd>- The matrix generated for this game</dd></li>
 * </ul>
 * <p>The board class is used to manage a game process without worrying about the map</p>
 * @see model.Hunter
 * @see model.CellMatrix
 * 
 * @author François Poguet
 */
public class Board {
	private TreeSet<Hunter> hunters;
	private CellMatrix cells;
	
	/**
	 * Default board constructor
	 * @param size		The map size for this game party
	 * @param nbPlayers	The number of players for this game party
	 */
	public Board(int size, int nbPlayers) {
		this.hunters = new TreeSet<Hunter>();
		this.cells = new CellMatrix(size,hunters,nbPlayers);
	}
	
	/**
	 * Execute a move of each hunter
	 */
	public void execute() {
		for(Hunter h : hunters) {
			h.move(this.cells);
		}
	}
	
	/*
	 * Execute the entire game party
	 */
	public void play() throws InterruptedException {
		while(this.cells.getTreasure().getWinner() == null) {
			this.execute();
			Thread.sleep(150);
			System.out.println(this);
		}
	}
	
	/**
	 * Execute the game ending and display result
	 */
	public void result() {
		
	}
	
	
//	public void hunters_dump() {
//		for(Hunter h : hunters) {
//			System.out.println(h+":"+h.getPosition());
//		}
//		System.out.println(cells.getTreasure().getPosition());
//	}
	
	/**
	 * Board appearance on display
	 * @return the board appearance
	 */
	public String toString() {
		return cells.toString();
	}
	
	
}
