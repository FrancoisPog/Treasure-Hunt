package model;


/**
 * <p><strong>Treasure</strong> is the class representing a treasure cell on the matrix .<p>
 * <p>A Floor is characterized by : </p>
 *  <ul>
 * 		<li><dt>A winner : only if a hunter found it</dt></li> 
 * </ul>
 * <p>When a Treasure is queried by a hunter, it brings the hunter on, and he win.</p>
 * @see model.Cell
 * @see model.Board
 * 
 * @author François Poguet
 */
public class Treasure extends Cell {
	private Hunter winner; 
	
	/**
	 * Default treasure constructor
	 * @param pos	The cell position
	 * @param cm	The matrix 
	 */
	public Treasure(Position pos,Board cm) {
		super(pos,cm);
		this.winner = null;
	}
	
	
	@Override
	/**
	 * Bring the hunter who queried
	 * @param h	The hunter
	 */
	public void process(Hunter h) {
		if(this.winner != null) {
			return;
		}
		h.getCurrentCell().leave();
		this.winner = h;

	}

	@Override
	/**
	 * Getter for the treasure appearance on display
	 * @return The treasure appearance
	 */
	public String toString() {
		if(this.winner != null) {
			return "|"+this.winner.toString()+"|";
		}
		return " T ";
	}
	
	/**
	 * Getter for the hunter which is on the treasure cell
	 * @return	The winner
	 */
	public Hunter getWinner() {
		return this.winner;
	}
	

}
