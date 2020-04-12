package model;

/**
 * <p><strong>Stone</strong> is the class representing a stone cell on the matrix .<p>
 * <p>When a stone is queried by a hunter, it give to the hunter a bypass direction to go around the wall and get closer to the treasure</p>
 * @see model.Cell
 * @see model.Board
 * 
 * @author François Poguet
 */
public class Stone extends Cell {

	/**
	 * Default stone constructor
	 * @param pos	The stone position
	 * @param cm	The matrix
	 */
	public Stone(Position pos,Board cm) {
		super(pos,cm);
	}
	
	
	
	/**
	 * Redirects the hunter who queried
	 */
	//@SuppressWarnings("unused")
	@Override
	public void process(Hunter h) {
		int hCol = h.getPosition().getColumn();
		int hRow = h.getPosition().getRow();
		int sCol = this.getPosition().getColumn();
		int sRow = this.getPosition().getRow();
		
		
		Position treasurePos = this.getMatrix().getTreasure().getPosition();
		Board mat = this.getMatrix();
		
		// if bypass the wall isn't the best direction to get closer to the treasure
		if(!mat.getCellInDir(h.getPosition(), h.getPosition().getBestDirTo(treasurePos, mat, false, 0)).isStone()) {
			h.setDirection(h.getPosition().getBestDirTo(treasurePos, mat, false, 0));
			return;
		}
		
		// if the hunter is already bypassing
		if(h.getByPassDirection() != 0) {
			h.setDirection(h.getPosition().getBestDirTo(treasurePos, this.getMatrix(),true,h.getByPassDirection()));
			return;
		}
		
		int size1 = this.wallSize(1);
		int size2 = this.wallSize(2);
		int size3 = this.wallSize(3);
		int size4 = this.wallSize(4);
		
		// Front
		if(hCol == sCol) {
			if(size2 > size4 ) {
				h.setByPassDirection(4);
			}else {
				h.setByPassDirection(2);
			}
		}
		if(hRow == sRow){
			if(size1 < size3) {
				h.setByPassDirection(1);
			}else {
				h.setByPassDirection(3);
			}
		}
		
		// Diagonals
		if(hCol != sCol && hRow != sRow) {
			if(hRow > sRow) { // TOP
				if(hCol < sCol) { // TOP RIGHT
					if(this.wallIsVertical()) { // TOP RIGHT H
						if(treasurePos.getColumn() == sCol) {
							h.setByPassDirection(1);
						}else {
							if(size1 + 1 > size3 - 1) {
								if(mat.get(hCol+1, hRow).isStone()) {
									h.setByPassDirection(3);
								}else{
									h.setByPassDirection(4);
								}
									
							} else {
								h.setByPassDirection(1);
							}
						}
					}else { // TOP RIGHT V
						if(treasurePos.getRow() == sRow) {
							h.setByPassDirection(4);
						}else {
							if(size2 - 1 < size4 + 1) {
								if(mat.get(hCol, hRow-1).isStone()) {
									h.setByPassDirection(2);
								}else{
									h.setByPassDirection(1);
								}
									
							}else {
								h.setByPassDirection(4);
							}
						}
					}
				}else { // TOP LEFT
					if(this.wallIsVertical()) { // TOP LEFT V
						if(treasurePos.getColumn() == sCol) {
							h.setByPassDirection(1);
						}else {
							if(size1 + 1 > size3 - 1) {
								if(mat.get(hCol-1, hRow).isStone()) {
									h.setByPassDirection(3);
								}else{
									h.setByPassDirection(2);
								}
									
							}else {
								h.setByPassDirection(1);
							}
						}
					} else { // TOP LEFT H
						if(treasurePos.getRow() == sRow) {
							h.setByPassDirection(2);
						}else {
							if(size2 + 1 > size4 - 1) {
								if(mat.get(hCol, hRow-1).isStone()) {
									h.setByPassDirection(4);
								}else{
									h.setByPassDirection(1);
								}
									
							}else {
								h.setByPassDirection(2);
							}
						}
					}
				}
				
			}else { //BOTTOM
				if(hCol < sCol) { // BOTTOM RIGHT
					if(this.wallIsVertical()) { // BOTTOM RIGHT V
						if(treasurePos.getColumn() == sCol) {
							h.setByPassDirection(3);
						}else {
							if(size3 + 1 > size1 - 1) {
								if(mat.get(hCol+1, hRow).isStone()) {
									h.setByPassDirection(1);
								}else{
									h.setByPassDirection(4);
								}
									
							} else {
								h.setByPassDirection(3);
							}
						}
					}else{ // BOTTOM RIGHT H
						if(treasurePos.getRow() == sRow) {
							h.setByPassDirection(4);
						}else {
							if(size2 - 1 < size4 + 1) {
								if(mat.get(hCol, hRow+1).isStone()) {
									h.setByPassDirection(2);
								}else{
									h.setByPassDirection(3);
								}
									
							}else {
								h.setByPassDirection(4);
							}
						}
					}
				}else { // BOTTOM LEFT
					if(this.wallIsVertical()) { // BOTTOM LEFT V
						if(treasurePos.getColumn() == sCol) {
							h.setByPassDirection(3);
						}else {
							if(size3 + 1 > size1 - 1) {
								if(mat.get(hCol-1, hRow).isStone()) {
									h.setByPassDirection(1);
								}else{
									h.setByPassDirection(2);
								}
									
							} else {
								h.setByPassDirection(3);
							}
						}
					}else { // BOTTOM LEFT H
						if(treasurePos.getRow() == sRow) {
							h.setByPassDirection(2);
						}else {
							if(size2 + 1 > size4 - 1) {
								if(mat.get(hCol, hRow+1).isStone()) {
									h.setByPassDirection(4);
								}else{
									h.setByPassDirection(3);
								}
									
							}else {
								h.setByPassDirection(2);
							}
						}
					}
				}
			}
		}

		h.setDirection(h.getPosition().getBestDirTo(treasurePos, this.getMatrix(),true,h.getByPassDirection()));
		
		//System.out.println("["+h+"] : Mur -> "+h.getDirection()+"bp:"+h.getByPassDirection());
		
	}

	@Override
	public String toString() {
		return " # ";
	}
	
	


	@Override
	public boolean isStone() {
		return true;
	}
	
	/**
	 * Compute the number of stone cell from this cell in a given direction
	 * @param dir	The direction
	 * @return		The wall size
	 */
	public int wallSize(int dir){
		int i,j;
		switch(dir) {
			case 1 :
				i = -1;
				j = 0;
				break;
			case 2 : 
				i = 0;
				j = -1;
				break;
			case 3 : 
				i = 1;
				j = 0;
				break;
			case 4 : 
				i = 0;
				j = 1;
				break;
			default : 
				i = 0;
				j = 0;
				break;
		}
		
		while(this.getMatrix().get(this.getPosition().getColumn()+j, this.getPosition().getRow() +i).isStone()) {
			switch(dir) {
				case 1 :
					i--;
					continue;
				case 2 : 
					j--;
					continue;
				case 3 : 
					i++;
					continue;
				case 4 : 
					j++;
					continue;
			}
			//System.out.println("dd");
		}
		//System.out.println("r");
		if(dir%2 == 0) {
			return Math.abs(j)-1;
		}
		
		return Math.abs(i)-1;
		
	}
	
	
	public boolean wallIsVertical() {
		return (this.wallSize(1) > 0 || this.wallSize(3) > 0);
	}

	

}
