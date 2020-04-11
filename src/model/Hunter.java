package model;

/**
 * <p><strong>Hunter</strong> is the class representing players.</p>.
 * <p>A hunter is characterized by : </p>
 * <ul>
 * 		<li><dt>A symbol</dt>
 * 			<dd>- The hunter appearance on display</dd></li> 
 * 		<li><dt>A direction</dt>
 * 			<dd>- The direction followed by the hunter</dd></li>
 * 		<li><dt>His current floor cell</dt>
 * 			<dd>- The currents cell where is the hunter</dd></li>
 * 		<li><dt>His bypass direction (only when he goes around a wall)</dt>
 * 			<dd>- The bypass direction to go around a wall</dd></li>
 * </ul>
 * <p>The <strong>hunter</strong> will be led by his successive floor cells to find the best way to go towards the treasure.</p>
 * <p>The <strong> bypass direction</strong> is used to hold direction when he goes around a wall.<p>
 * <p>The hunter class implements the <strong>Positionable</strong> interface.
 * @see model.Positionable
 * 
 * @author François Poguet
 */
public class Hunter implements Positionable,Comparable<Hunter>{
	private char symbol;
	private int direction;
	private Floor currentFloor;
	private int byPassDirection;
	
	
	/**
	 * Default constructor
	 * @param symbol 	The hunter's symbol on display
	 * @param pos 		The hunter's position
	 */
	public Hunter(char symbol, Position pos) {
		this.symbol = symbol;
		this.direction = 0;
		this.currentFloor = new Floor(pos, this,null);
		this.byPassDirection = 0;
	}

	/**
	 * Getter for the direction
	 * @return The current direction
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * Getter for the current cell
	 * @return	The current hunter's cell
	 */
	public Floor getCurrentCell() {
		return currentFloor;
	}

	/**
	 * Setter for the current cell
	 * @param currentCell	The new current cell
	 */
	public void setCurrentCell(Floor currentCell) {
		this.currentFloor = currentCell;
	}

	/**
	 * Setter for the direction
	 * @param direction	The new direction
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}

	
	
	/**
	 * Getter for the current hunter's position
	 * @return The hunter's position
	 */
	@Override
	public Position getPosition() {
		return this.getCurrentCell().getPosition();
	}

	
	/**
	 * Hunter's toString
	 */
	public String toString() {
		return this.symbol+"";
	}


	
	/**
	 * Compare the hunter with an other by their position
	 * @param that The other hunter 
	 */
	@Override
	public int compareTo(Hunter that) {
		if(that == null) {
			return 1;
		}
		return this.getPosition().compareTo(that.getPosition());
			
	}
	
	/**
	 * Execute a move depending the current cell and the current direction
	 * @param mat The cell matrix where the hunter is
	 */
	public void move(Board mat) {
		int row = 0, col = 0;
		
		switch(this.direction) {
			case 3:
				row = -1;
				break;
			case 4:
				row = -1;
				col = -1;
				break;
			case 2:
				row = -1;
				col = 1;
				break;
			case 1:
				col = 1;
				break;
			case 5:
				col = -1;
				break;
			case 7:
				row = 1;
				break;
			case 6:
				row = 1;
				col = -1;
				break;
			case 8:
				row = 1;
				col = 1;
				break;
			default : 
				System.err.println("Error");
				break;
		}
		
		mat.get(this.getPosition().getColumn()+col, this.getPosition().getRow()+row).process(this);
		
		
		
	}



	/**
	 * Getter for the bypass direction, when the hunter goes around a wall
	 * @return The bypass direction (1:TOP, 2:LEFT, 3:BOTTOM, 4:RIGHT or 0:No bypass)
	 */
	public int getByPassDirection() {
		return byPassDirection;
	}

	/**
	 * Setter for byPassDirection
	 * @param byPassDir The new bypass direction
	 */
	public void setByPassDirection(int byPassDir) {
		this.byPassDirection = byPassDir;
	}
	
	
}
	
	
	
	
	
	
	
	

