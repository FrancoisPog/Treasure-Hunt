package model;

public class Floor extends Cell {
	private boolean isFull;
	private Hunter hunter;
	
	public Floor(Position pos, Hunter hunter) {
		super(pos);
		this.isFull = false;
		
		
		if(hunter != null) {
			this.hunter = hunter;
			this.isFull = true;
		}else {
			this.hunter = null;
		}
	}
	
	@Override
	public boolean process(Hunter h) {
		if(isFull) {
			h.setDirection(DIRECTION.BOTTOM);
			return false;
		}
		Floor oldCell = (Floor)h.getCurrentCell();
		oldCell.leave();
		
		this.hunter = h;
		h.setPosition(this.getPos());
		h.setCurrentCell(this);
		this.isFull = true;
		return true;

	}
	
	
	
	public boolean isFull() {
		return isFull;
	}
	
	public void leave() {
		this.isFull = false;
		this.hunter = null;
	}

	@Override
	public String toString() {
		if(isFull) {
			return " "+hunter.toString()+" ";
		}
		return " . ";
	}

	@Override
	public Position getPosition() {
		return this.getPos();
	}

	

}
