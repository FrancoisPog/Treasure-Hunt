package model;

import java.util.TreeSet;

/**
 * <p><strong>Board</strong> is the class representing the game board.<p>
 * <p>A Board is characterized by : </p>
 * <ul>
 * 		<li><dt>A Cell Matrix</dt>
 * 			<dd>- All cells of the game is stored in the <strong>collections</strong></dd></li>
 * 		<li><dt>A treasure</dt>
 * 			<dd>- The treasure among the cells</dd></li>
 * </ul>
 * <p>The Board class is mainly used to generate the map and display it during the game.</p>
 * @see model.Cell
 * 
 * @author François Poguet
 */
public class Board {
	private Matrix<Cell> mat;
	private Treasure treasure;
	
	/**
	 * Default constructor
	 * @param size 		The matrix size
	 * @param hunters 	The empty TreeSet of hunters, it will be filled in this method
	 * @param nbPlayers	The number of players in the game
	 */
	public Board(int size, TreeSet<Hunter> hunters, int nbPlayers) {
		if(size < 0) {
			this.mat = null; // Faire les vérifs
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
		
		
		
		// Matrix creation
		mat = new Matrix<Cell>(size);
		
		// Map generation
		for(int row = 0 ; row < size ; row++) {
			for(int col = 0 ; col < size ; col++) {
				Position curr = new Position(col,row);
				
				// If the current position is the same than the first players in the TreeSet
				if(!hunters_tmp.isEmpty() &&  hunters_tmp.first().getPosition().equals(curr)) {
					// Assignment of player at this position
					Hunter h = hunters_tmp.pollFirst();
					mat.set(col, row, h.getCurrentCell());
					h.getCurrentCell().setMatrix(this);
					h.setDirection(h.getPosition().getBestDirTo(getTreasure().getPosition(),this,false,0));
					continue;
				}
				
				// If the current position is on the border of map
				if(col == 0 || col == size-1 || row == 0 || row == size-1) {
					// Assignment of border cell in this position
					
					mat.set(col, row, new Border(curr,this));
					
					continue;
				}
				
				// If the current position is the same than the treasure
				if(curr.equals(treasure.getPosition())) {
					// Assignment of treasure in this position
					
					mat.set(col, row, treasure);
					continue;
				}
				
				// If the current position can be a stone cell, let the probability do
				double p = canBeStone(col, row);
				if( p < 1 ) {
					if(Math.random()>p) {
						
						mat.set(col, row, new Stone(curr,this));
						
						continue;
					}
				}
				
				// Else, assignment of a floor cell at this position 
				
				mat.set(col, row, new Floor(curr,null,this));
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
		if(col == 1 || col == mat.size()-2 || row == 1 || row == mat.size()-2 ) {
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
					return 0.8; // wall begin
				}
				// else
				return 1;
			}
			// if the left left cell is stone
			if(get(col-2, row).isStone()) {
				return 0.35; // wall continue
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
				return 0.35; // wall continue
			}
			return 0; // Wall continue (mandatory)
		}
		
		//System.out.println("DEBUG: error");
		return 1;
	}
	
	public Cell getCellInDir(Position pos,int dir) {
		switch(dir) {
			case 1 : 
				return this.get(pos.getColumn()+1, pos.getRow());
			case 2 : 
				return this.get(pos.getColumn()+1, pos.getRow()-1);
			case 3 : 
				return this.get(pos.getColumn(), pos.getRow()-1);
			case 4 : 
				return this.get(pos.getColumn()-1, pos.getRow()-1);
			case 5 : 
				return this.get(pos.getColumn()-1, pos.getRow());
			case 6 : 
				return this.get(pos.getColumn()-1, pos.getRow()+1);
			case 7 : 
				return this.get(pos.getColumn(), pos.getRow()+1);
			case 8 : 
				return this.get(pos.getColumn()+1, pos.getRow()+1);
			default : 
				return null;
		}
	}
	
	
	
	/**
	 * Get the map appearance on display
	 * @return The map appearance
	 */
	public String toString() {
		return mat.toString();
	}
	
	/**
	 * Getter for a cell of the matrix
	 * @param row The row index
	 * @param col The column index
	 * @return The value of requested cell
	 */
	public Cell get(int col, int row) {
		return mat.get(col, row);
	}
	
	/**
	 * Getter for a cell of the matrix
	 * @param pos The position of requested cell
	 * @return The value of requested cell
	 */
	public Cell get(Position pos) {
		return mat.get(pos.getColumn(), pos.getRow());
	}
	

	/**
	 * Getter for the treasure 
	 * @return The treasure
	 */
	public Treasure getTreasure() {
		return treasure;
	}



	
}
