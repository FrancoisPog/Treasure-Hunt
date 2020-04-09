package model;

public class Stone extends Cell {

	public Stone(Position pos,CellMatrix cm) {
		super(pos,cm);
	}
	
	
	@Override
	public void process(Hunter h) {
		int sRow = this.getPosition().getRow();
		int sCol = this.getPosition().getColumn();
		int hRow = h.getPosition().getRow();
		int hCol = h.getPosition().getColumn();
		Position treasurePos = this.getMatrix().getTreasure().getPosition();
		
		
		h.setDirection(h.getPosition().getBestDirTo(treasurePos, this.getMatrix(),true));
		System.out.println("["+h+"] : Mur -> "+h.getDirection());
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
