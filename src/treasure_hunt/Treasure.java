package treasure_hunt;


/**
 * <p><strong>Treasure</strong> is the class representing a treasure cell on the matrix .<p>
 * <p>A Floor is characterized by : </p>
 *  <ul>
 * 		<li><dt>A winner : only if a hunter found it</dt></li> 
 * </ul>
 * <p>When a Treasure is queried by a hunter, it brings the hunter on, and he win.</p>
 * @see treasure_hunt.Cell
 * @see treasure_hunt.Board
 * 
 * @author François Poguet
 */
public class Treasure extends Cell {
	private Hunter winner; 
	private boolean isFound;
	
	/**
	 * Default treasure constructor
	 * @param pos	The cell position
	 * @param cm	The matrix 
	 */
	public Treasure(Position pos,Board cm) {
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
			return " "+this.winner.toString()+" ";
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
	
	public boolean isFound() {
		return this.isFound;
	}
	

}
