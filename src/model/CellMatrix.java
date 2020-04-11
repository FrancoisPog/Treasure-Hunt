package model;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * <p><strong>CellMatrix</strong> is the ourself collection for manage a 2D array of cells .<p>
 * <p>A cell matrix is characterized by : </p>
 * <ul>
 * 		<li><dt>A 2D primitive array of Cell</dt>
 * 			<dd>- All of cells stored</dd></li> 
 * 		<li><dt>A size</dt>
 * 			<dd>- The matrix is of size*size dimension</dd></li>
 * 		<li><dt>A treasure</dt>
 * 			<dd>- The treasure among the cells</dd></li>
 * </ul>
 * <p>The CellMatrix class is mainly used to generate the map and display it during the game.</p>
 * @see model.Cell
 * @see java.util.Iterator
 * 
 * @author François Poguet
 */
public class CellMatrix implements Iterable<Cell> {
	private Cell [][] matrix;
	private int size;
	private Treasure treasure;
	
	/**
	 * Default constructor
	 * @param size 		The matrix size
	 * @param hunters 	The empty TreeSet of hunters, it will be filled in this method
	 * @param nbPlayers	The number of players in the game
	 */
	public CellMatrix(int size, TreeSet<Hunter> hunters, int nbPlayers) {
		if(size < 0) {
			this.matrix = null; // Faire les vérifs
			return;
		}
		
		// Players creation and position assignment (all different), the hunters set is sorted by their positions
		int c = 'A';
		while(hunters.size() != nbPlayers) {
			if(hunters.add(new Hunter((char)(c), Position.randomPos(size-2, 1)))) {
				c++;
			}
		}
		
		// Treasure creation and position assignment ( with different position than hunters)
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
		
		// Temporary copy of hunters TreeSet
		@SuppressWarnings("unchecked")
		TreeSet<Hunter> hunters_tmp = (TreeSet<Hunter>) hunters.clone();
		
		// 2D array creation
		this.size = size;
		matrix = new Cell [size][size];
		
		// Map generation
		for(int row = 0 ; row < size ; row++) {
			for(int col = 0 ; col < size ; col++) {
				Position curr = new Position(col,row);
				
				// If the current position is the same than the first players in the TreeSet
				if(!hunters_tmp.isEmpty() &&  hunters_tmp.first().getPosition().equals(curr)) {
					// Assignment of player at this position
					Hunter h = hunters_tmp.pollFirst();
					matrix[col][row] = h.getCurrentCell();
					h.getCurrentCell().setMatrix(this);
					h.setDirection(h.getPosition().getBestDirTo(getTreasure().getPosition(),this,false,0));
					continue;
				}
				
				// If the current position is on the border of map
				if(col == 0 || col == size-1 || row == 0 || row == size-1) {
					// Assignment of border cell in this position
					matrix[col][row] = new Border(curr,this);
					
					continue;
				}
				
				// If the current position is the same than the treasure
				if(curr.equals(treasure.getPosition())) {
					// Assignment of treasure in this position
					matrix[col][row] = treasure;
					continue;
				}
				
				// If the current position can be a stone cell, let the probability do
				double p = canBeStone(col, row);
				if( p < 1 ) {
					if(Math.random()>p) {
						matrix[col][row] = new Stone(curr,this);
						
						continue;
					}
				}
				
				// Else, assignment of a floor cell at this position 
				matrix[col][row] = new Floor(curr,null,this);
			}
		}
	}
	

	/**
	 * Method for calculating the probability that the cell can be of the stone type
	 * @param col The cell position's column
	 * @param row The cell position's row
	 * @return The probability (1 for impossible, 0 for sure)
	 */
	public double canBeStone(int col, int row) {
		// if border
		if(col == 1 || col == size-2 || row == 1 || row == size-2 ) {
			return 1;
		}
		
		// if the top-left or top-right cell is stone
		if(get(col-1, row-1).isStone() || get(col+1, row-1).isStone()) {
			return 1;
		}
		
		
		// if the top cell is free
		if(!get(col, row-1).isStone()) {
			// if the left cell is free
			if(!get(col-1, row).isStone()) {
				// if the top-right cell is free
				if(!get(col+2, row-1).isStone()) {
					return 0.9; // wall begin
				}
				// else
				return 1;
			}
			// if the left left cell is stone
			if(get(col-2, row).isStone()) {
				return 0.4; // wall continue
			}
			return 0.5; // Wall continue (second stone in wall)
		}
		
		// if the top cell is stone
		
		if(get(col, row-1).isStone()) {
			// if the left cell is stone
			if(get(col-1, row).isStone()) {
				return 1;
			}
			
			// if the top top cell is stone
			if(get(col, row-2).isStone()) {
				return 0.3; // wall continue
			}
			return 0; // Wall continue (mandatory)
		}
		
		//System.out.println("DEBUG: error");
		return 1;
	}
	
	
	
	
	
	/**
	 * Get the map appearance on display
	 * @return The map appearance
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
	 * Getter for a cell of the matrix
	 * @param row The row index
	 * @param col The column index
	 * @return The value of requested cell
	 */
	public Cell get(int col, int row) {
		if(matrix == null || row >= size || col >= size) {
			return null;
		}
		return matrix[col][row];
	}
	
	/**
	 * Getter for a cell of the matrix
	 * @param pos The position of requested cell
	 * @return The value of requested cell
	 */
	public Cell get(Position pos) {
		return matrix[pos.getColumn()][pos.getRow()];
	}
	

	/**
	 * Getter for the treasure 
	 * @return The treasure
	 */
	public Treasure getTreasure() {
		return treasure;
	}



	
	/**
	 * Get the cell matrix iterator (row browsing)
	 * @return The row iterator
	 */
	@Override
	public Iterator<Cell> iterator() {
		return new IteratorRow() ;
	}
	
	
	/**
	 * <p><strong>IteratorRow</strong> is an inner class of model.CellMatrix.<p>
	 * <p>The row iterator is characterized by : </p>
	 * <ul>
	 * 		<li><dt>A row index</dt></li> 
	 * 		<li><dt>A column index</dt></li> 
	 * </ul>
	 * 
	 * @see model.CellMatrix
	 * @see java.util.Iterator
	 * 
	 * @author François Poguet
	 */
	public class IteratorRow implements Iterator<Cell>{
		private int rowIndex;
		private int colIndex;
		
		/**
		 * Default iterator constructor
		 */
		public IteratorRow() {
			rowIndex = 0;
			colIndex = 0;
		}
		
		/**
		 * Test if there is an element after the current element
		 * @return false if is the end of collection, else true
		 */
		public boolean hasNext() {
			if(matrix == null) {
				return false;
			}
			return rowIndex < size;
		}

		/**
		 * Return the current value and continue to next element
		 * @return The current element value
		 */
		public Cell next() {
			if(matrix == null || !hasNext()) {
				return null;
			}
			Cell valReturn = get(colIndex, rowIndex);
			colIndex++;
			if(colIndex >= size) {
				colIndex = 0;
				rowIndex++;
			}
			return valReturn;
		}

	}

	
}
