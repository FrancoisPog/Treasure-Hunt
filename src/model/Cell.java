package model;

public abstract class Cell implements Questionable, Positionable{
	private Position pos;
	private CellMatrix matrix;
	
	public Cell(Position pos,CellMatrix cm) {
		this.pos = pos;
		this.matrix = cm;
	}
	
	public CellMatrix getMatrix() {
		return matrix;
	}
	
	public void setMatrix(CellMatrix matrix) {
		this.matrix = matrix;
	}

	public boolean isEquals(Cell that) {
		return this.pos.equals(that.getPosition());
	}
	
	public Position getPosition() {
		return pos;
	}

	public abstract String toString();
	
	public boolean isStone() {
		return false;
	}
}
