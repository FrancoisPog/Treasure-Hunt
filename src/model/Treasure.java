package model;

public class Treasure extends Cell {

	public Treasure(Position pos,CellMatrix cm) {
		super(pos,cm);
	}
	
	
	@Override
	public void process(Hunter h) {
		System.out.println(h+" won !");
		System.exit(0);

	}

	@Override
	public String toString() {
		return " T ";
	}


	

}
