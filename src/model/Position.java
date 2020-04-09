package model;

import java.util.Arrays;

public class Position implements Comparable<Position>{
	private int column;
	private int row;
	
	public Position(int column, int row) {
		this.column = column;
		this.row = row;
	}
	
	public int getDistanceTo(Position p) {
		return (p.column-this.column)*(p.column-this.column)+(p.row-this.row)*(p.row-this.row);
	}
	
	public int getDirectionTo(Position p) {
		if(this.equals(p)) {
			return 0;
		}
		if(p.getRow() < this.row) {
			if(p.getColumn()< this.column) {
				return 4;
			}
			if(p.getColumn() > this.column) {
				return 2;
			}
			return 3;
		}
		
		if(p.getRow() > this.row) {
			if(p.getColumn()< this.column) {
				return 6;
			}
			if(p.getColumn() > this.column) {
				return 8;
			}
			return 7;
		}
		
		if(p.getColumn() > this.column) {
			return 1;
		}
		
		return 5;
	}
	
	
	
	

	
	
	public int getBestDirTo(Position p, CellMatrix mat, boolean careAboutStone, int direction) {
		int[][] dir1 = {{2,3,4},{4,5,6},{6,7,8},{1,2,8}};
		
		Position best_pos = null;
		int dist_min = 9999999;
		//System.out.println(direction);
		for(int i = -1 ; i <= 1 ; ++i) {
			for(int j = -1 ; j <= 1; ++j) {
				if(i == 0 && j == 0) {
					continue;
				}
				Position curr = new Position(this.getColumn()+i, this.getRow()+j);
				
				
				if(careAboutStone && mat.get(curr).isStone()) {
					continue;
				}
				//System.out.println("p to curr : "+this.getDirectionTo(curr));
				
				if(direction > 0) {
					//System.out.println(this.getDirectionTo(curr));
					if(Arrays.binarySearch(dir1[direction-1],this.getDirectionTo(curr))<0) {
						continue;
					}
					//System.out.println(this.getDirectionTo(curr));
					
				}
				
				
				
				//System.out.println(curr);
				int dist_curr = curr.getDistanceTo(p);
				//System.out.println(dist_curr);
				if(dist_curr < dist_min) {
					dist_min = dist_curr;
					best_pos = curr;
				}
				
			}
		}
		//System.out.println("best:"+this.getDirectionTo(best_pos));
		return this.getDirectionTo(best_pos);
	}
	
	
	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
	
	public boolean equals(Position that) {
		return this.column == that.column && this.row == that.row;
	}

	@Override
	public int compareTo(Position o) {
		return 0;
	}
	
	public String toString() {
		return "["+this.column+";"+this.row+"]";
	}
	
	public static Position randomPos(int max,int min) {
		return new Position(min+(int)(Math.random()*(max - min + 1)), min+(int)(Math.random()*(max - min + 1)));
	}
}
