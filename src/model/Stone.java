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
	@SuppressWarnings("unused")
	@Override
	public void process(Hunter h) {
		int hCol = h.getPosition().getColumn();
		int hRow = h.getPosition().getRow();
		int sCol = h.getPosition().getColumn();
		int sRow = h.getPosition().getRow();
		
		
		Position treasurePos = this.getMatrix().getTreasure().getPosition();
		Board mat = this.getMatrix();
		
		
		if(h.getByPassDirection() == 0) {
			if(h.getPosition().getColumn() == this.getPosition().getColumn()) {
				int size2 = this.wallSize(2);
				int size4 = this.wallSize(4);
				if(size2 > size4 ) {
					h.setByPassDirection(4);
				}else {
					h.setByPassDirection(2);
				}
			}
			
			if(h.getPosition().getRow() == this.getPosition().getRow()){
				int size1 = this.wallSize(1);
				int size3 = this.wallSize(3);
				if(size1 < size3) {
					h.setByPassDirection(1);
				}else {
					h.setByPassDirection(3);
				}
			}
			
			// Diagonales
			if(h.getPosition().getColumn() != this.getPosition().getColumn() && h.getPosition().getRow() != this.getPosition().getRow()) {
				if(h.getPosition().getRow() > this.getPosition().getRow()) { // Haut
					if(h.getPosition().getColumn() < this.getPosition().getColumn()) { // haut droite
						if(mat.get(hCol, hRow-1).isStone()) {
							h.setByPassDirection(4);
						}else {
							h.setByPassDirection(1);
						}
					}else { // haut gauche
						if(mat.get(hCol, hRow-1).isStone()) {
							h.setByPassDirection(2);
						}else {
							h.setByPassDirection(1);
						}
					}
					
				}else { // Bas
					if(h.getPosition().getColumn() < this.getPosition().getColumn()) { // bas droite
						if(mat.get(hCol, hRow+1).isStone()) {
							h.setByPassDirection(4);
						}else {
							h.setByPassDirection(3);
						}
					}else { // bas gauche
						if(mat.get(hCol, hRow+1).isStone()) {
							h.setByPassDirection(2);
						}else {
							h.setByPassDirection(3);
						}
					}
				}
			}
		}
		
		
		
		h.setDirection(h.getPosition().getBestDirTo(treasurePos, this.getMatrix(),true,h.getByPassDirection()));
		
		//System.out.println("["+h+"] : Mur -> "+h.getDirection());
		
		
		
		
		return;
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
		}
		if(dir%2 == 0) {
			return Math.abs(j)-1;
		}
		
		return Math.abs(i)-1;
		
	}

	

}
