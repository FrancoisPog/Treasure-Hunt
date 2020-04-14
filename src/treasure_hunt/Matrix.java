package treasure_hunt;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * <p>The <strong>Matrix</strong> is a ourself made collection to represent a square two dimensions array</p>
 * <p>A Matrix is characterized by : </p>
 * <ul>
 * 		<li>An ArrayList of ArrayList of type <em>T</em></li>
 * 		<li>A size</li>
 * </ul>
 * 
 * @param <T> The generic type used
 * @see java.lang.Iterable
 * @author François Poguet
 */
public class Matrix<T> implements Iterable<T>{
	private ArrayList<ArrayList<T>> matrix;
	private int size;
	
	/**
	 * Default matrix constructor
	 * @param size	The matrix size
	 */
	public Matrix(int size){
		this.size = size;
		matrix = new ArrayList<ArrayList<T>>();
		for(int i = 0 ; i < size ; ++i) {
			ArrayList<T> tmp = new ArrayList<T>();
			for(int j = 0 ; j < size ; ++j) {
				tmp.add(null);
			}
			matrix.add(tmp);
		}
	}
	
	/**
	 * Getter for a matrix element
	 * @param col	The column index
	 * @param row	The row index
	 * @return		The wanted element 
	 */
	public T get(int col, int row){
		return matrix.get(row).get(col);
	}
	
	/**
	 * Setter for a matrix element
	 * @param col	The column index
	 * @param row	The row index
	 * @param value	The new element
	 */
	public void set(int col, int row, T value) {
		matrix.get(row).set(col, value);
	}
	
	/**
	 * Getter for the matrix size
	 * @return	The matrix size
	 */
	public int size() {
		return this.size;
	}
	
	@Override
	public String toString() {
		String res = "";
		
		for(ArrayList<T> row : matrix) {
			for(T element : row) {
				res += element.toString();
			}
			res += "\n";
		}
		
		return res;
	}
	
	/**
	 * Get the matrix iterator (row browsing)
	 * @return The matrix iterator
	 */
	@Override
	public Iterator<T> iterator() {
		return new MatrixIterator() ;
	}
	
	
	/**
	 * <p><strong>MatrixIterator</strong> is an inner class of Matrix<p>
	 * <p>The matrix iterator is characterized by : </p>
	 * <ul>
	 * 		<li><dt>A row index</dt></li> 
	 * 		<li><dt>A column index</dt></li> 
	 * </ul>
	 * 
	 * @see treasure_hunt.Matrix
	 * @see java.util.Iterator
	 * 
	 * @author François Poguet
	 */
	public class MatrixIterator implements Iterator<T>{
		private int rowIndex;
		private int colIndex;
		
		/**
		 * Default iterator constructor
		 */
		public MatrixIterator() {
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
		public T next() {
			if(matrix == null || !hasNext()) {
				return null;
			}
			T valReturn = get(colIndex, rowIndex);
			colIndex++;
			if(colIndex >= size) {
				colIndex = 0;
				rowIndex++;
			}
			return valReturn;
		}

	}
	
}
