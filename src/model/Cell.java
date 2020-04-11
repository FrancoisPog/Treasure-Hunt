package model;

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
 * @see model.Positionable 
 * @see model.Questionable
 * 
 * @author François Poguet
 */
public abstract class Cell implements Questionable, Positionable{
	private Position pos;
	private CellMatrix matrix;
	
	/**
	 * Default constructor 
	 * @param pos	The position (it will keep the same all its life)
	 * @param cm	Its matrix
	 */
	public Cell(Position pos,CellMatrix cm) {
		this.pos = pos;
		this.matrix = cm;
	}
	
	/**
	 * Getter for its matrix
	 * @return Its matrix
	 */
	public CellMatrix getMatrix() {
		return matrix;
	}
	
	/**
	 * Setter for its matrix
	 * @param matrix The new matrix
	 */
	public void setMatrix(CellMatrix matrix) {
		this.matrix = matrix;
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
}
