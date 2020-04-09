package model;

public class Stone extends Cell {

	public Stone(Position pos,CellMatrix cm) {
		super(pos,cm);
	}
	
	
	@Override
	public void process(Hunter h) {
		int hCol = h.getPosition().getColumn();
		int hRow = h.getPosition().getRow();
		int sCol = h.getPosition().getColumn();
		int sRow = h.getPosition().getRow();
		
		
		Position treasurePos = this.getMatrix().getTreasure().getPosition();
		CellMatrix mat = this.getMatrix();
		//Position old = h.getPosition();
		
		if(h.getDirContourning() == 0) {
			if(h.getPosition().getColumn() == this.getPosition().getColumn()) {
				if(h.getPosition().getRow() < treasurePos.getRow()) {
					h.setDirContourning(4);
				}else {
					h.setDirContourning(2);
				}
			}
			
			if(h.getPosition().getRow() == this.getPosition().getRow()){
				if(h.getPosition().getColumn() < treasurePos.getColumn()) {
					h.setDirContourning(1);
				}else {
					h.setDirContourning(3);
				}
			}
			
			// Diagonales
			if(h.getPosition().getColumn() != this.getPosition().getColumn() && h.getPosition().getRow() != this.getPosition().getRow()) {
				if(h.getPosition().getRow() > this.getPosition().getRow()) { // Haut
					if(h.getPosition().getColumn() < this.getPosition().getColumn()) { // haut droite
						if(mat.get(hCol, hRow-1).isStone()) {
							h.setDirContourning(4);
						}else {
							h.setDirContourning(1);
						}
					}else { // haut gauche
						if(mat.get(hCol, hRow-1).isStone()) {
							h.setDirContourning(2);
						}else {
							h.setDirContourning(1);
						}
					}
					
				}else { // Bas
					if(h.getPosition().getColumn() < this.getPosition().getColumn()) { // bas droite
						if(mat.get(hCol, hRow+1).isStone()) {
							h.setDirContourning(4);
						}else {
							h.setDirContourning(3);
						}
					}else { // bas gauche
						if(mat.get(hCol, hRow+1).isStone()) {
							h.setDirContourning(2);
						}else {
							h.setDirContourning(3);
						}
					}
				}
			}
		}
		
		
		
		h.setDirection(h.getPosition().getBestDirTo(treasurePos, this.getMatrix(),true,h.getDirContourning()));
		
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


	

}
