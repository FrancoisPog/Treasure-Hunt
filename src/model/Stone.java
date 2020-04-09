package model;

public class Stone extends Cell {

	public Stone(Position pos) {
		super(pos);
	}
	
	
	@Override
	public boolean process(Hunter h) {
		h.setDirection(DIRECTION.BOTTOM);
		return false;
	}

	@Override
	public String toString() {
		return " # ";
	}


	@Override
	public Position getPosition() {
		// TODO Auto-generated method stub
		return this.getPos();
	}

}
