package treasure_hunt;

import java.awt.Color;

/**
 * <p><strong>Floor_c</strong> is the class representing a floor cell on the matrix .<p>
 * <p>A Floor_c is characterized by : </p>
 *  <ul>
 * 		<li><dt>A state : free or full</dt></li> 
 * 		<li><dt>A hunter if it full</dt></li>
 * </ul>
 * <p>When a Floor_c is queried by a hunter, it bring the hunter on if the floor is free, and redirects him to get closer to the treasure</p>
 * @see treasure_hunt.Cell
 * @see treasure_hunt.Board
 * 
 * @author François Poguet
 * @author Enzo Costantini
 */
public class Floor_c extends Cell {
	private boolean isFull;
	private Hunter hunter;
	
	/**
	 * Default floor constructor
	 * @param pos	The stone position
	 * @param cm	The matrix
	 */
	public Floor_c(Position pos, Hunter hunter,Board cm) {
		super(pos,cm);
		this.isFull = false;
		
		
		if(hunter != null) {
			this.hunter = hunter;
			this.isFull = true;
		}else {
			this.hunter = null;
		}
	}
	
	
	/**
	 * Get the hunter closer to the treasure
	 */
	@Override
	public void process(Hunter h) {
		// If the floor is full, the hunter is redirected in a random direction
		if(isFull) {
			h.setDirection(1 + (int)(Math.random()*8));
			h.setBypassDirection(0);
			System.out.println("\tThe floor is full");
			return;
		}
		
		Position old = h.getPosition();
		
		h.getCurrentFloor().leave();
		this.come(h);
		
		// If the hunter was bypassing a wall
		if(h.getBypassDirection() > 0) {
			if(h.getBypassDirection() == 1 || h.getBypassDirection() == 3) {
				if(h.getPosition().getColumn() != old.getColumn()) {
					h.setBypassDirection(0);
				}
			}else {
				if(h.getPosition().getRow() != old.getRow()) {
					h.setBypassDirection(0);
				}
			}
		}
		
		// Redirect the hunter to the best direction to get closer to the treasure
		Position treasure_pos = this.getBoard().getTreasure().getPosition();
		h.setDirection(h.getPosition().getBestDirTo(treasure_pos,this.getBoard(),false,h.getBypassDirection()));
		System.out.println("\tBest direction : "+h.getDirection()+" "+Hunter.dirToArrow(h.getDirection()));
	}
	
	
	/**
	 * Check if the floor is full
	 * @return a boolean
	 */
	public boolean isFull() {
		return isFull;
	}
	
	/**
	 * Empty the floor
	 */
	public void leave() {
		this.isFull = false;
		this.hunter = null;
	}
	
	/**
	 * Fill the floor with a hunter
	 * @param h	The hunter 
	 */
	public void come(Hunter h) {
		this.hunter = h;
		h.setCurrentFloor(this);
		this.isFull = true;
	}
	

	@Override
	public String toString() {
		if(isFull) {
			return " "+hunter.toString()+" ";
		}
		return " . ";
	}
	@Override
	public byte encode() {
		return 0;
	}


	@Override
	public Color color() {
		if(isFull) {
			return Color.blue;
		}
		return Color.gray;
	}

	
	

}
