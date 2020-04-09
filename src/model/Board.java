package model;

import java.util.TreeSet;

public class Board {
	private TreeSet<Hunter> hunters;
	private CellMatrix cells;
	//private int size;
	
	
	
	public Board(int size, int nbPlayers) {
		this.hunters = new TreeSet<Hunter>();
		
		// Création des joueurs et affectation des positions
		int c = 'A';
		while(hunters.size() != nbPlayers) {
			if(hunters.add(new Hunter((char)(c), Position.randomPos(size-2, 1)))) {
				c++;
			}
		}
		
		// Création du tresor et affectation de sa position
		boolean ok = true;
		Treasure t = null;
		do {
			ok = true;
			t = new Treasure(Position.randomPos(size-2, 1));
			
			for(Hunter h : hunters) {
				if(h.getPosition().equals(t.getPos())) {
					ok = false;
				}
			}
		}while(!ok);
		
		
		// Création de la matrice de cellule 
		this.cells = new CellMatrix(size,hunters,t);
		//this.size = size;
	}
	
	public void init(int size) {
		for(Hunter h : hunters) {
			h.move(this.cells);
		}
	}
	
	public String toString() {
		return cells.toString();
	}
	
	
}
