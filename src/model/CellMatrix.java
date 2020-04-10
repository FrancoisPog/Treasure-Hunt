package model;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * @author francois_pog14
 */
public class CellMatrix implements Iterable<Cell> {
	private Cell [][] matrix;
	private int size;
	private Treasure treasure;
	
	/**
	 * Matrix constructor
	 * @param size 		The matrix size
	 */
	public CellMatrix(int size, TreeSet<Hunter> hunters, int nbPlayers) {
		if(size < 0) {
			this.matrix = null;
			return;
		}
		
		// Création des joueurs et affectation des positions
		int c = 'A';
		while(hunters.size() != nbPlayers) {
			if(hunters.add(new Hunter((char)(c), Position.randomPos(size-2, 1)))) {
				c++;
			}
		}
		
		// Création du tresor et affectation de sa position
		boolean ok = true;
		treasure = null;
		do {
			ok = true;
			treasure = new Treasure(Position.randomPos(size-2, 1),this);
			
			for(Hunter h : hunters) {
				if(h.getPosition().equals(treasure.getPosition())) {
					ok = false;
				}
			}
		}while(!ok);
		
		@SuppressWarnings("unchecked")
		TreeSet<Hunter> hunters_tmp = (TreeSet<Hunter>) hunters.clone();
		
		
		this.size = size;
		matrix = new Cell [size][size];
		
		
		for(int row = 0 ; row < size ; row++) {
			for(int col = 0 ; col < size ; col++) {
				Position curr = new Position(col,row);
				
				if(!hunters_tmp.isEmpty() &&  hunters_tmp.first().getPosition().equals(curr)) {
					Hunter h = hunters_tmp.pollFirst();
					matrix[col][row] = h.getCurrentCell();
					h.getCurrentCell().setMatrix(this);
					h.setDirection(h.getPosition().getBestDirTo(getTreasure().getPosition(),this,false,0));
					continue;
				}
				
				if(col == 0 || col == size-1 || row == 0 || row == size-1) {
					matrix[col][row] = new Border(curr,this);
					
					continue;
				}
				
				if(curr.equals(treasure.getPosition())) {
					matrix[col][row] = treasure;
					
					continue;
				}
				
				double p = canBeStone(col, row);
				//System.out.println(p);
				if( p < 1 ) {
					if(Math.random()>p) {
						matrix[col][row] = new Stone(curr,this);
						
						continue;
					}
				}
				
				
				matrix[col][row] = new Floor(curr,null,this);
				
			}
		}
		
		
	}
	

	
	public double canBeStone(int col, int row) {
		// Si bord -> impossible
		if(col == 1 || col == size-2 || row == 1 || row == size-2 ) {
			return 1;
		}
		
		// Si topleft ou topright = stone -> exit
		if(get(col-1, row-1).isStone() || get(col+1, row-1).isStone()) {
			return 2;
		}
		
		
		// Si cases dessus libre
		if(!get(col, row-1).isStone()) {
			// Si la case à gauche est libre 
			if(!get(col-1, row).isStone()) {
				// Si la case au dessus à droite droite est libre
				if(!get(col+2, row-1).isStone()) {
					return 0.9; // mur commence
				}
				// Sinon
				return 3;
			}
			// Si la case à gauche est un rocher
			if(get(col-2, row).isStone()) {
				return 0.4; // mur continue
			}
			return 0.5;
		}
		
		// Si case du dessus rocher 
		
		if(get(col, row-1).isStone()) {
			if(get(col-1, row).isStone()) {
				return 4;
			}
			
			if(get(col, row-2).isStone()) {
				return 0.3; // mur continue
			}
			return 0;
		}
		
		System.out.println("error");
		return 5;
	}
	
	
	
	
	
	/**
	 * toString method
	 * @return The string representation of matrix
	 */
	public String toString() {
		if(this.matrix == null) {
			return "null";
		}
		String res = "";
		int i = 0;
		for(Cell element : this) {
			++i;
			res += element;
			if(i%this.size == 0) {
				res+="\n";
			}
			
		}
		
		
		return res;
	}
	
	/**
	 * Getter for an element of the matrix
	 * @param row The row index
	 * @param col The column index
	 * @return The value of matrix[row][col]
	 */
	public Cell get(int col, int row) {
		if(matrix == null || row >= size || col >= size) {
			return null;
		}
		return matrix[col][row];
	}
	
	public Cell get(Position pos) {
		return matrix[pos.getColumn()][pos.getRow()];
	}
	

	
	public int size() {
		return this.size;
	}
	
	
	
	

	public Treasure getTreasure() {
		return treasure;
	}



	@Override
	public Iterator<Cell> iterator() {
		return new IteratorRow(this) ;
	}
	
	
	public class IteratorRow implements Iterator<Cell>{
		private CellMatrix matrix ;
		private int rowIndex;
		private int colIndex;
		
		/**
		 * Iterator constructor
		 * @param mat The matrix used
		 */
		public IteratorRow(CellMatrix mat) {
			this.matrix = mat;
			rowIndex = 0;
			colIndex = 0;
		}
		
		/**
		 * Test if there is an element after the current element
		 * @return false if is the end of collection, else true
		 */
		public boolean hasNext() {
			if(this.matrix == null) {
				return false;
			}
			return rowIndex < matrix.size;
		}

		/**
		 * Return the current value and continue to next element
		 * @return The current element value
		 */
		public Cell next() {
			if(this.matrix == null || !hasNext()) {
				return null;
			}
			Cell valReturn = this.matrix.get(colIndex, rowIndex);
			colIndex++;
			if(colIndex >= matrix.size) {
				colIndex = 0;
				rowIndex++;
			}
			return valReturn;
		}

	}

	
}
