package model;

public class Treasure extends Cell {

	public Treasure(Position pos) {
		super(pos);
	}
	
	
	@Override
	public boolean process(Hunter h) {
		// TODO Auto-generated method stub
		return false;

	}

	@Override
	public String toString() {
		return " T ";
	}


	@Override
	public Position getPosition() {
		// TODO Auto-generated method stub
		return this.getPos();
	}

}
