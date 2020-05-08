package treasure_hunt;


/**
 * <p><strong>Treasure_c</strong> is the class representing a treasure cell on the matrix .<p>
 * <p>A Floor_c is characterized by : </p>
 *  <ul>
 *  	<li><dt>A state : found or not</dt></li>
 * 		<li><dt>A winner : only if a hunter found it</dt></li> 
 * </ul>
 * <p>When a Treasure_c is queried by a hunter, it brings the hunter on, and he win.</p>
 * @see treasure_hunt.Cell
 * @see treasure_hunt.Board
 * 
 * @author François Poguet
 * @author Enzo Costantini
 */
public class Treasure_c extends Cell {
	private Hunter winner; 
	private boolean isFound;
	
	/**
	 * Default treasure constructor
	 * @param pos	The cell position
	 * @param cm	The matrix 
	 */
	public Treasure_c(Position pos,Board cm) {
		super(pos,cm);
		this.winner = null;
		this.isFound = false;
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
		h.getCurrentFloor().leave();
		this.winner = h;
		this.isFound = true;
	}

	@Override
	/**
	 * Getter for the treasure appearance on display
	 * @return The treasure appearance
	 */
	public String toString() {
		if(isFound) {
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
	
	/**
	 * Check if the treasure is found by a hunter
	 * @return	True if the treasure is found, otherwise false
	 */
	public boolean isFound() {
		return this.isFound;
	}
	
	/**
	 * Reset the treasure
	 */
	public void reset() {
		this.isFound = false;
		this.winner = null;
	}


	@Override
	public byte encode() {
		return 2;
	}
}
