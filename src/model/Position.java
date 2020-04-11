package model;

import java.util.Arrays;

/**
 * <p><strong>Position</strong> is the class representing a point on a matrix.<p>
 * <p>A position is characterized by : </p>
 * <ul>
 * 		<li><dt>A row index</dt></li> 
 * 		<li><dt>A column index</dt></li>
 * </ul>
 * <p>Positions are comparable by order of appearance during row browsing</p>
 * @see model.Positionable
 * @see java.lang.Comparable
 * 
 * @author François Poguet
 */
public class Position implements Comparable<Position>{
	private int column;
	private int row;
	
	/**
	 * Default position constructor
	 * @param column 	The column index 
	 * @param row		The row index
	 */
	public Position(int column, int row) {
		this.column = column;
		this.row = row;
	}
	
	/**
	 * Compute the distance to another position
	 * @param that	The other position
	 * @return 		The distance
	 */
	public int getDistanceTo(Position that) {
		return (that.column-this.column)*(that.column-this.column)+(that.row-this.row)*(that.row-this.row);
	}
	
	/**
	 * Compute the direction to have to go to another close position
	 * @param that	The other position
	 * @return 		The direction
	 */
	public int getDirectionTo(Position that) {
		if(this.equals(that)) {
			return 0;
		}
		if(that.getRow() < this.row) {
			if(that.getColumn()< this.column) {
				return 4;
			}
			if(that.getColumn() > this.column) {
				return 2;
			}
			return 3;
		}
		
		if(that.getRow() > this.row) {
			if(that.getColumn()< this.column) {
				return 6;
			}
			if(that.getColumn() > this.column) {
				return 8;
			}
			return 7;
		}
		
		if(that.getColumn() > this.column) {
			return 1;
		}
		
		return 5;
	}
	
	
	/**
	 * Compute the best direction to have to go to another position 
	 * @param that				The position to go
	 * @param mat				The cell matrix 
	 * @param careAboutStone	If true, this method return the best direction without go to a stone cell
	 * @param bypassDir			The current bypass direction of hunter
	 * @return					The best direction
	 */
	public int getBestDirTo(Position that, CellMatrix mat, boolean careAboutStone, int bypassDir) {
		int[][] allowedPos = {{2,3,4},{4,5,6},{6,7,8},{1,2,8}};
		
		Position best_pos = null;
		int dist_min = 9999999;

		for(int i = -1 ; i <= 1 ; ++i) {
			for(int j = -1 ; j <= 1; ++j) {
				if(i == 0 && j == 0) {
					continue;
				}
				Position curr = new Position(this.getColumn()+i, this.getRow()+j);
				
				// if careAboutStone=true and the current position is stone, skip it
				if(careAboutStone && mat.get(curr).isStone()) {
					continue;
				}
				
				// if the hunter is going around a wall, hold the bypass direction
				if(bypassDir > 0) {
					if(Arrays.binarySearch(allowedPos[bypassDir-1],this.getDirectionTo(curr))<0) {
						continue;
					}
				}
				
				// keep the best direction
				int dist_curr = curr.getDistanceTo(that);
				if(dist_curr < dist_min) {
					dist_min = dist_curr;
					best_pos = curr;
				}
				
			}
		}
		//System.out.println("best:"+this.getDirectionTo(best_pos));
		return this.getDirectionTo(best_pos);
	}
	
	/**
	 * Getter for the column index
	 * @return The column index
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Getter for the row index
	 * @return The row index
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Check if the position is equals to another
	 * @param that 	The other position
	 * @return		True if there are equals, else false
	 */
	public boolean equals(Position that) {
		return this.column == that.column && this.row == that.row;
	}
	
	/**
	 * Compare two position
	 */
	@Override
	public int compareTo(Position that) {
		if(that == null) {
			return 1;
		}
		int res = this.getRow() - that.getRow();
		if(res != 0) {
			return res;
		}
		
		return this.getColumn() - that.getColumn();
	}
	
	
	/**
	 * Get the position appearance on display
	 * @return The string position
	 */
	public String toString() {
		return "["+this.column+";"+this.row+"]";
	}
	
	/**
	 * Static method to get a random position
	 * @param max	The maximum value 
	 * @param min	The minimum value
	 * @return		A random position
	 */
	public static Position randomPos(int max,int min) {
		return new Position(min+(int)(Math.random()*(max - min + 1)), min+(int)(Math.random()*(max - min + 1)));
	}
}
