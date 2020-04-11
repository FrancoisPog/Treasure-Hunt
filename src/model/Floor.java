package model;

/**
 * <p><strong>Floor</strong> is the class representing a floor cell on the matrix .<p>
 * <p>A Floor is characterized by : </p>
 *  <ul>
 * 		<li><dt>A state : free or full</dt></li> 
 * 		<li><dt>A hunter is it full</dt></li>
 * </ul>
 * <p>When a Floor is queried by a hunter, it bring the hunter on if the floor is free, and redirects him to get closer to the treasure</p>
 * @see model.Cell
 * @see model.CellMatrix
 * 
 * @author François Poguet
 */
public class Floor extends Cell {
	private boolean isFull;
	private Hunter hunter;
	
	
	public Floor(Position pos, Hunter hunter,CellMatrix cm) {
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
			System.out.println("["+h+"] : Case occupée -> "+h.getDirection());
			return;
		}
		
		Position old = h.getPosition();
		
		h.getCurrentCell().leave();
		this.come(h);
		
		if(h.getByPassDirection()>0) {
			if(h.getByPassDirection() == 1 || h.getByPassDirection() == 3) {
				if(h.getPosition().getColumn() != old.getColumn()) {
					h.setByPassDirection(0);
				}
			}else {
				if(h.getPosition().getRow() != old.getRow()) {
					h.setByPassDirection(0);
				}
			}
		}
		
		
		
		Position treasure_pos = this.getMatrix().getTreasure().getPosition();
		
		
		//System.out.println("f:"+h.getDirection());
		
		
		h.setDirection(h.getPosition().getBestDirTo(treasure_pos,this.getMatrix(),false,h.getByPassDirection()));
		
		//System.out.println("["+h+"] : Avance -> "+h.getDirection());
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
		h.setCurrentCell(this);
		this.isFull = true;
	}
	

	@Override
	public String toString() {
		if(isFull) {
			return " "+hunter.toString()+" ";
		}
		return " . ";
	}

	
	

}
