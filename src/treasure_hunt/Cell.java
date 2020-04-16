package treasure_hunt;

/**
 * <p><strong>Cell</strong> is an abstract class for different cell type.<p>
 * <p>A cell is characterized by : </p>
 * <ul>
 * 		<li><dt>A cell matrix</dt>
 * 			<dd>- The 2D array to which the cell belongs </dd></li> 
 * 		<li><dt>A Position</dt>
 * 			<dd>- Its position in the matrix</dd></li>
 * </ul>
 * <p>The cell class implements the <strong>Questionable</strong> interface, so it can be queried by hunters.</p>
 * <p>The cell class implements also the <strong>Positionable</strong> interface.<p>
 * @see treasure_hunt.Positionable 
 * @see treasure_hunt.Questionable
 * 
 * @author François Poguet
 */
public abstract class Cell implements Questionable, Positionable{
	private Position pos;
	private Board board;
	
	/**
	 * Default constructor 
	 * @param pos	The position (it will keep the same all its life)
	 * @param board	Its board
	 */
	public Cell(Position pos,Board board) {
		this.pos = pos;
		this.board = board;
	}
	
	/**
	 * Getter for its board
	 * @return Its board
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Setter for its board
	 * @param board The new board
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

	/**
	 * Getter for its position
	 * @return its position
	 */
	public Position getPosition() {
		return pos;
	}
	
	/**
	 * Get the cell appearance on display
	 * @return Its appearance
	 */
	public abstract String toString();
	
	/**
	 * Check if the cell is stone type
	 * @return True if is a stone cell, false else
	 */
	public boolean isStone() {
		return false;
	}
	
	/**
	 * Get the number encoding number
	 * @return The encoding number
	 */
	public abstract byte encode();
}
