package model;

import java.util.TreeSet;

public class Board {
	private TreeSet<Hunter> hunters;
	private CellMatrix cells;
	
	
	
	
	public Board(int size, int nbPlayers) {
		this.hunters = new TreeSet<Hunter>();
		
		// Création de la matrice de cellule 
		this.cells = new CellMatrix(size,hunters,nbPlayers);
		
	}
	
	public void init(int size) {
		for(Hunter h : hunters) {
			h.move(this.cells);
		}
	}
	
	
	public void hunters_dump() {
		for(Hunter h : hunters) {
			System.out.println(h+":"+h.getPosition());
		}
		System.out.println(cells.getTreasure().getPosition());
	}
	
	public String toString() {
		return cells.toString();
	}
	
	
}
