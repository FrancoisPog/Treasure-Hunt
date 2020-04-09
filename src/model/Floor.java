package model;

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
	
	@Override
	public void process(Hunter h) {
		if(isFull) {
			h.setDirection(1 + (int)(Math.random()*8));
			System.out.println("["+h+"] : Case occupée -> "+h.getDirection());
			return;
		}
		
		
		h.getCurrentCell().leave();
		this.come(h);
		
		Position treasure_pos = this.getMatrix().getTreasure().getPosition();
		
		
		
		//System.out.println(best_pos);
		
		h.setDirection(h.getPosition().getBestDirTo(treasure_pos,this.getMatrix(),true));
		System.out.println("["+h+"] : Avance -> "+h.getDirection());
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
