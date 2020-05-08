package treasure_hunt;

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
 * @see treasure_hunt.Positionable
 * 
 * @author François Poguet
 * @author Enzo Costantini
 */
public class Hunter implements Positionable,Comparable<Hunter>{
	private char symbol;
	private int direction;
	private Floor_c currentFloor;
	private int bypassDirection;
	
	
	/**
	 * Default constructor
	 * @param symbol 			The hunter's symbol on display
	 * @param currentFloor 		The hunter's current floor
	 */
	public Hunter(char symbol, Floor_c currentFloor) {
		this.symbol = symbol;
		this.direction = 0;
		this.currentFloor = currentFloor;
		this.bypassDirection = 0;
	}

	/**
	 * Getter for the direction
	 * @return The current direction
	 */
	public int getDirection() {
		return direction;
	}
	
	/**
	 * Getter for the symbol
	 * @return The symbol
	 */
	public int getSymbol() {
		return symbol;
	}

	/**
	 * Getter for the current floor
	 * @return	The current hunter's floor
	 */
	public Floor_c getCurrentFloor() {
		return currentFloor;
	}

	/**
	 * Setter for the current floor
	 * @param currentFloor	The new current floor
	 */
	public void setCurrentFloor(Floor_c currentFloor) {
		this.currentFloor = currentFloor;
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
		return this.getCurrentFloor().getPosition();
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
		return (this.symbol+"").compareTo(that.getSymbol()+"");
			
	}
	
	/**
	 * Execute a move : query the cell in his direction
	 * @return the new player position
	 */
	public Position move() {
		Board board = this.currentFloor.getBoard();
		System.out.println("\tTarget cell : "+board.getCellInDir(getPosition(), direction).getPosition());
		board.getCellInDir(getPosition(), direction).process(this);
		System.out.println("\t-> Position : "+getPosition()+" - "+"Direction : "+getDirection()+" "+Hunter.dirToArrow(getDirection())+"\n");
		return this.getPosition();
		
	}


	/**
	 * Getter for the bypass direction, when the hunter goes around a wall
	 * @return The bypass direction (1:TOP, 2:LEFT, 3:BOTTOM, 4:RIGHT or 0:No bypass)
	 */
	public int getBypassDirection() {
		return bypassDirection;
	}

	/**
	 * Setter for byPassDirection
	 * @param byPassDir The new bypass direction
	 */
	public void setBypassDirection(int byPassDir) {
		this.bypassDirection = byPassDir;
	}
	
	/**
	 * Get the arrow from a direction
	 * @param dir	The direction
	 * @return		The arrow
	 */
	public static char dirToArrow(int dir) {
		char arrows[] = {'\u2192','\u2197','\u2191','\u2196','\u2190','\u2199','\u2193','\u2198'};
		return arrows[dir-1];
	}
	
}
	
	
	
	
	
	
	
	

