package model;

public class Hunter implements Positionable,Comparable<Hunter>{
	private char symbol;
	private int direction;
	private Floor currentFloor;
	private int dirContourning;
	
	
	public Hunter(char symbol, Position pos) {
		this.symbol = symbol;
		this.direction = 0;
		//this.pos = pos;
		this.currentFloor = new Floor(pos, this,null);
		this.dirContourning = 0;
	}


	public int getDirection() {
		return direction;
	}


	public Floor getCurrentCell() {
		return currentFloor;
	}


	public void setCurrentCell(Floor currentCell) {
		this.currentFloor = currentCell;
	}


	public void setDirection(int direction) {
		this.direction = direction;
	}


	public char getSymbol() {
		return symbol;
	}


	@Override
	public Position getPosition() {
		return this.getCurrentCell().getPosition();
	}

	
	
	public String toString() {
		return this.symbol+"";
	}


	@Override
	public int compareTo(Hunter that) {
		if(that == null) {
			return 1;
		}
		int res = this.getPosition().getRow() - that.getPosition().getRow();
		if(res != 0) {
			return res;
		}
		
		return this.getPosition().getColumn() - that.getPosition().getColumn();
			
	}
	
	
	public void move(CellMatrix mat) {
		int row = 0, col = 0;
		
		switch(this.direction) {
			case 3:
				row = -1;
				break;
			case 4:
				row = -1;
				col = -1;
				break;
			case 2:
				row = -1;
				col = 1;
				break;
			case 1:
				col = 1;
				break;
			case 5:
				col = -1;
				break;
			case 7:
				row = 1;
				break;
			case 6:
				row = 1;
				col = -1;
				break;
			case 8:
				row = 1;
				col = 1;
				break;
			default : 
				System.err.println("Error");
				break;
		}
		
		mat.get(this.getPosition().getColumn()+col, this.getPosition().getRow()+row).process(this);
		
		
		
	}


	public int getDirContourning() {
		return dirContourning;
	}


	public void setDirContourning(int dirContourning) {
		this.dirContourning = dirContourning;
	}
	
	
}
	
	
	
	
	
	
	
	

