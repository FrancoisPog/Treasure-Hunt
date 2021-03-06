package treasure_hunt;
import java.awt.Color;
//
import java.io.File;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.JLabel;


/**
 * <p><strong>Board</strong> is the class representing the game board.<p>
 * <p>A Board is characterized by : </p>
 * <ul>
 * 		<li><dt>A Cell Matrix</dt>
 * 			<dd>- All cells of the game is stored in the <strong>Matrix</strong></dd></li>
 * 		<li><dt>A treasure</dt>
 * 			<dd>- The treasure among the cells</dd></li>
 * </ul>
 * <p>The Board class is mainly used to generate the map and display it during the game.</p>
 * @see treasure_hunt.Cell
 * 
 * @author François Poguet
 * @author Enzo Costantini
 */
public class Board implements Iterable<Cell> {
	private Matrix<Cell> mat;
	private Treasure_c treasure;
	
	
	/**
	 * Constructor creating a random Board
	 * @param size			The board's size
	 * @param hunters		The hunters set (empty)
	 * @param nbPlayers		The number of players to create
	 * @param wallDensity	The walls density indicator
	 */
	public Board(int size, TreeSet<Hunter> hunters, int nbPlayers, int wallDensity) {
		// Matrix creation
		mat = new Matrix<Cell>(size);
		randomMap(wallDensity);
		setHunters(hunters, nbPlayers);
		
	}
	
	/**
	 * Create a board from file
	 * @param file		The board file
	 * @param hunters	The hunters set (empty)
	 * @param nbPlayers	The number of players to create
	 * @throws Exception If the file is wrong
	 */
	public Board(File file, TreeSet<Hunter> hunters, int nbPlayers) throws Exception {
		FileManager.openMap(this, file);
		setHunters(hunters, nbPlayers);
	}
	
	
	/**
	 * Constructor creating a board from a jlabel matrix (editor to game)
	 * @param labels		The JLabel matrix
	 * @param hunters		The hunter set
	 * @param nbPlayers		The number of player
	 */
	public Board(Matrix<JLabel> labels,TreeSet<Hunter> hunters, int nbPlayers) {
		mat = new Matrix<Cell>(labels.size());
		int size = labels.size();
		
		for(int row = 0 ; row < size ; ++row ) {
			for(int col = 0 ; col < size ; ++col) {
				
				Position curr = new Position(col, row);
				
				// If the current position is on the border of map
				if(col == 0 || col == size-1 || row == 0 || row == size-1) {
					mat.set(col, row, new Border_c(curr,this));
					continue;
				}
				
				Color color = labels.get(col, row).getBackground();
				
				if(color == Color.black) {
					mat.set(col, row, new Stone_c(curr, this));
					continue;
				}
				
				if(color == Color.yellow) {
					this.treasure = new Treasure_c(curr, this);
					mat.set(col, row, this.treasure);
					continue;
				}
				
				mat.set(col, row, new Floor_c(curr, null, this));
			
			}
		}
		
		setHunters(hunters, nbPlayers);
		
		
	}
	
	
	
	
	/**
	 * Replace hunters on the same board and reset the treasure
	 * @param hunters	The hunters set(empty)
	 * @param nbPlayers	The number of players
	 */
	public void resetMap(TreeSet<Hunter> hunters, int nbPlayers) {
		this.treasure.reset();
		setHunters(hunters, nbPlayers);
		
	}
	
	/**
	 * Place hunters on a board
	 * @param hunters
	 * @param nbPlayers
	 */
	public void setHunters(TreeSet<Hunter> hunters, int nbPlayers) {
		int c = 'A';
		while(hunters.size() != nbPlayers) {
			Hunter h = new Hunter((char)c, null); // Hunter creation
			Position pos = null;
			boolean isCorner = false; // We can't place a hunter on a corner because he could be blocked
			do {
				pos = Position.randomPos(this.size()-2, 1); // random position
				isCorner =		(pos.getColumn() == 1 && pos.getRow() == 1) 
								|| 	(pos.getColumn() == this.size()-2 && pos.getRow() == 1) 
								|| 	(pos.getColumn() == this.size()-2 && pos.getRow() == this.size()-2)
								|| 	(pos.getColumn() == 1 && pos.getRow() == this.size()-2);

			}while(isCorner || !this.get(pos).getClass().getSimpleName().equals("Floor_c") || ((Floor_c)get(pos)).isFull());
			
			Floor_c floor = (Floor_c)this.get(pos); 
			floor.come(h);
			hunters.add(h);
			c++;
			// Hunter added

			
		}
	}
	
	/**
	 * Generate a random map
	 * @param wallDensity The wall density
	 */
	public void randomMap(int wallDensity) {
		int size = size();
		
		
		// Treasure creation and position assignment
		treasure = new Treasure_c(Position.randomPos(size-2, 1),this);
		
		
		// Map generation
		for(int row = 0 ; row < size ; row++) {
			for(int col = 0 ; col < size ; col++) {
				
				Position curr = new Position(col,row);
				
				// If the current position is on the border of map
				if(col == 0 || col == size-1 || row == 0 || row == size-1) {
					// Assignment of border cell in this position
					mat.set(col, row, new Border_c(curr,this));
					continue;
				}
				
				// If the current position is the same than the treasure
				if(curr.equals(treasure.getPosition())) {
					// Assignment of treasure in this position
					mat.set(col, row, treasure);
					continue;
				}
				
				// If the current position can be a stone cell, let the probability do
				double p = canBeStone(col, row,wallDensity);
				if( p < 1 ) {
					if(Math.random()>p) {
						
						mat.set(col, row, new Stone_c(curr,this));
						
						continue;
					}
				}
				
				// Else, assignment of a floor cell at this position 
				mat.set(col, row, new Floor_c(curr,null,this));
			}
		}
		
		
		
		
	}
	

	/**
	 * Method for calculating the probability that the cell can be of the stone type
	 * @param col 			The cell position's column
	 * @param row 			The cell position's row
	 * @param wallDensity	The wall's density indicator
	 * @return The inverse probability (1 for impossible, 0 for sure)
	 */
	public double canBeStone(int col, int row, int wallDensity) {
		double proba[][] = {{1,1,1},{0.90,0.55,0.75},{0.75,0.35,0.5},{0.4,0.4,0.2}};
		
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
					return proba[wallDensity][0]; // wall begin
				}
				// else
				return 1;
			}
			// if the left left cell is stone
			if(get(col-2, row).isStone()) {
				return proba[wallDensity][1]; // wall continue
			}
			return proba[wallDensity][2]; // Wall continue (second stone in wall)
		}
		
		// if the top cell is stone
		
		if(get(col, row-1).isStone()) {
			// if the left cell is stone
			if(get(col-1, row).isStone()) {
				return 1;
			}
			
			// if the top top cell is stone
			if(get(col, row-2).isStone()) {
				return proba[wallDensity][1]; // wall continue
			}
			return 0; // Wall continue (mandatory)
		}
		
		return 1;
	}
	
	/**
	 * Get the adjacent cell from pos in direction of dir
	 * @param pos	The position
	 * @param dir	The direction
	 * @return		The Cell searched
	 */
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
	 * Setter for the cell of the matrix
	 * @param row	The row index
	 * @param col 	The column index
	 * @param value	The new cell
	 */
	public void set(int col, int row, Cell value) {
		this.mat.set(col, row, value);
	}
	

	/**
	 * Getter for the treasure 
	 * @return The treasure
	 */
	public Treasure_c getTreasure() {
		return treasure;
	}
	
	/**
	 * Setter for the treasure
	 * @param treasure	The treasure
	 */
	public void setTreasure(Treasure_c treasure) {
		this.treasure = treasure;
	}
	
	/**
	 * Setter for the matrix
	 * @param mat	The matrix
	 */
	public void setMatrix(Matrix<Cell> mat) {
		this.mat = mat;
	}
	
	/**
	 * Getter for the matrix
	 * @return The matrix
	 */
	public Matrix<Cell> getMatrix() {
		return this.mat;
	}
	
	/**
	 * Getter for the board size
	 * @return	The board size
	 */	
	public int size() {
		return mat.size();
	}

	@Override
	public Iterator<Cell> iterator() {
		// TODO Auto-generated method stub
		return this.mat.iterator();
	}



	
}
