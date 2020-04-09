package model;

public class Border extends Cell {

	public Border(Position pos) {
		super(pos);
	}
	
	
	@Override
	public boolean process(Hunter h) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		return " + ";
	}


	@Override
	public Position getPosition() {
		// TODO Auto-generated method stub
		return this.getPos();
	}

	
	
}
