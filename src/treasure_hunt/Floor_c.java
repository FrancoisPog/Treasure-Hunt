package treasure_hunt;

/**
 * <p><strong>Floor_c</strong> is the class representing a floor cell on the matrix .<p>
 * <p>A Floor_c is characterized by : </p>
 *  <ul>
 * 		<li><dt>A state : free or full</dt></li> 
 * 		<li><dt>A hunter is it full</dt></li>
 * </ul>
 * <p>When a Floor_c is queried by a hunter, it bring the hunter on if the floor is free, and redirects him to get closer to the treasure</p>
 * @see treasure_hunt.Cell
 * @see treasure_hunt.Board
 * 
 * @author François Poguet
 */
public class Floor_c extends Cell {
	private boolean isFull;
	private Hunter hunter;
	
	
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
		if(isFull) {
			h.setDirection(1 + (int)(Math.random()*8));
			h.setBypassDirection(0);
			//System.out.println("["+h+"] : Case occupée -> "+h.getDirection());
			return;
		}
		
		Position old = h.getPosition();
		
		h.getCurrentFloor().leave();
		this.come(h);
		
		if(h.getBypassDirection() > 0) {
			if(h.getBypassDirection() == 1 || h.getBypassDirection() == 3) {
				if(h.getPosition().getColumn() != old.getColumn()) {
					h.setBypassDirection(0);
					//System.out.println("reset");
				}
			}else {
				if(h.getPosition().getRow() != old.getRow()) {
					h.setBypassDirection(0);
					//System.out.println("reset");
				}
			}
		}
		
		
		
		Position treasure_pos = this.getBoard().getTreasure().getPosition();
		
		h.setDirection(h.getPosition().getBestDirTo(treasure_pos,this.getBoard(),false,h.getBypassDirection()));
		
	}
	
	
	
	public boolean isFull() {
		return isFull;
	}
	
	public void leave() {
		this.isFull = false;
		this.hunter = null;
	}
	
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

	
	

}
