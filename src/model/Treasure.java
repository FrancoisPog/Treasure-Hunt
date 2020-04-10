package model;

public class Treasure extends Cell {
	private Hunter winner; 
	public Treasure(Position pos,CellMatrix cm) {
		super(pos,cm);
		this.winner = null;
	}
	
	
	@Override
	public void process(Hunter h) {
		h.getCurrentCell().leave();
		//System.out.println(h+" won !");
		this.winner = h;

	}

	@Override
	public String toString() {
		if(this.winner != null) {
			return "|"+this.winner.toString()+"|";
		}
		return " T ";
	}

	public Hunter getWinner() {
//		if(this.winner == null) {
//			return null;
//		}
		return this.winner;
	}
	

}
