package model;

public abstract class Cell implements Questionable, Positionable{
	private Position pos;
	
	public Cell(Position pos) {
		this.pos = pos;
	}
	
	public Position getPos() {
		return pos;
	}

	public abstract String toString();
}
