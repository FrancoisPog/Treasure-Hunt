package model;

public class Hunter implements Positionable,Comparable<Hunter>{
	private char symbol;
	private DIRECTION direction;
	private Floor currentFloor;
	
	
	public Hunter(char symbol, Position pos) {
		this.symbol = symbol;
		this.direction = DIRECTION.TOP;
		//this.pos = pos;
		this.currentFloor = new Floor(pos, this);
	}


	public DIRECTION getDirection() {
		return direction;
	}


	public Floor getCurrentCell() {
		return currentFloor;
	}


	public void setCurrentCell(Floor currentCell) {
		this.currentFloor = currentCell;
	}


	public void setDirection(DIRECTION direction) {
		this.direction = direction;
	}


	public char getSymbol() {
		return symbol;
	}


	@Override
	public Position getPosition() {
		return this.getCurrentCell().getPos();
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
			case TOP:
				row = -1;
				break;
			case TOP_LEFT:
				row = -1;
				col = -1;
				break;
			case TOP_RIGHT:
				row = -1;
				col = 1;
				break;
			case RIGHT:
				col = 1;
				break;
			case LEFT:
				col = -1;
				break;
			case BOTTOM:
				row = 1;
				break;
			case BOTTOM_LEFT:
				row = 1;
				col = -1;
				break;
			case BOTTOM_RIGH:
				row = 1;
				col = 1;
				break;
			default : 
				System.err.println("Error");
				break;
		}
		
		mat.get(this.getPosition().getColumn()+col, this.getPosition().getRow()+row).process(this);
		
		
		
	}
}
	
	
	
	
	
	
	
	

