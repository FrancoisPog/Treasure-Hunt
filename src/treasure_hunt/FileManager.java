package treasure_hunt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * <p>The class <strong>FileManager</strong> manage the files interactions to save or open board in file.<p>
 * <p>This class contains only static method</p>
 * 
 * @author François Poguet
 * @author Enzo Costantini
 *
 */
public class FileManager {
	
	
	/**
	 * Open a dialog window to choose a file
	 * @param parent	The parent frame
	 * @param type		The type of window (save/open)
	 * @return The selected file
	 */
	public static File selectFile(JFrame parent,char type ) {
		JFileChooser select = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Map files", "pog");
		select.addChoosableFileFilter(filter);
		select.setAcceptAllFileFilterUsed(false);
		
		select.setMultiSelectionEnabled(false);
		select.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int res = -1;
		if(type == 'o') {
			select.setDialogTitle("Open a map");
			res = select.showOpenDialog(parent);
		}else {
			if(type != 's') {
				System.err.println("[selectFile] : The type must be 'o' or 's'");
				return null;
			}
			select.setDialogTitle("Save a map");
			res = select.showSaveDialog(parent);
		}
		if(res == JFileChooser.APPROVE_OPTION) {
			File file = select.getSelectedFile();
			return file;
		}
		return null;
	}
	
	
	
	
	/**
	 * Save a board to file
	 * @param board	The board to save
	 * @param file	The files used
	 */
	public static void saveMap(Board board, File file) {
		DataOutputStream out = null;
		
		try {
			
			out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			
			// Write size
			out.writeInt(board.size());
			Cell prev = null;
			int i = -1;
			
			for(Cell curr : board) {
				if(prev == null || prev.encode() == curr.encode()) {
					i++;
					prev = curr;
					continue;
				}
				
				if(i >= 1) {
					out.writeByte(-128+i+1);
				}
				
				out.writeByte(prev.encode());
				prev = curr;
				i = 0;
			}
			
			if(i > 0) {
				if(i >= 1) {
						out.writeByte(-128+i+1);
				}
				out.writeByte(prev.encode());
			}
			out.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

		
	}
	
	
	/**
	 * Open a map
	 * @param board The current board (it will be modified in this method)
	 * @param file	The files used
	 * @throws Exception If the file is wrong
	 */
	public static void openMap(Board board, File file) throws Exception {
		DataInputStream in = null;
		
		try {
			
			in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
			
			int size = in.readInt();
			
			if(size < 10 || size > 120) {
				in.close();
				throw new Exception();
			}
			board.setMatrix(new Matrix<Cell>(size));
			
			int row = 0;
			int col = 0;
			
			
			for(int i = 0 ; i < size * size ; ++i) {
				if(col >= size -1 && row>=size -1) {
					break;
				}
				
				if(col  % size == 0 && col != 0) {
					col = 0;
					row++;
				}
				
				byte read = in.readByte();
				
				int rep = 1;
				
				if(read < 0) {
					rep = ((int)read + 128);
					if(rep > 121) {
						in.close();
						throw new Exception();
					}
				}else {
					if(read > 3) {
						in.close();
						throw new Exception();
					}
				}
			
				
				if(rep != 1) {
					read = in.readByte();
					if(read < 0 || read > 3) {
						in.close();
						throw new Exception();
					}
						
				}
				
				for(int k = 0 ; k < rep ; ++k) {
					if(col % size == 0 && col != 0) {
						col = 0;
						row++;
					}
					if(read == 0) {
						board.set(col,row,new Floor_c(new Position(col, row),null,board));
						col++;
						continue;
					}
					
					if(read == 1) {
						board.set(col,row,new Border_c(new Position(col, row),board));
						col++;
						continue;
					}
					
					if(read == 2) {
						Treasure_c t = new Treasure_c(new Position(col, row),board);
						board.set(col,row,t);
						board.setTreasure(t);
						col++;
						continue;
					}
					
					if(read == 3) {
						board.set(col,row,new Stone_c(new Position(col, row),board));
						col++;
						continue;
					}
					
				}
			
			}
			
			in.close();
		
		}catch (EOFException eof) {
			System.err.println("[Open]\tend of file");
		} catch (Exception e) {
			System.err.println("[Open]\tBad file");
			throw new Exception("Bad file");

		}
		
		
		return;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
