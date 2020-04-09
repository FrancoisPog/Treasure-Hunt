package model;

public class Position implements Comparable<Position>{
	private int column;
	private int row;
	
	public Position(int column, int row) {
		this.column = column;
		this.row = row;
	}
	
	public int getDistanceTo(Position p) {
		return p.column*p.column+p.row*p.row;
	}

	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
	
	public boolean equals(Position that) {
		return this.column == that.column && this.row == that.row;
	}

	@Override
	public int compareTo(Position o) {
		return 0;
	}
	
	public String toString() {
		return "["+this.column+";"+this.row+"]";
	}
	
	public static Position randomPos(int max,int min) {
		return new Position(min+(int)(Math.random()*(max - min + 1)), min+(int)(Math.random()*(max - min + 1)));
	}
}
