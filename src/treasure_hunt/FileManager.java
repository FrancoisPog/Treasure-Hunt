package treasure_hunt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class FileManager {
	
	public FileManager() {
		
	}
	
	public File selectFile(JFrame parent,char type ) {
		JFileChooser select = new JFileChooser();
		select.setMultiSelectionEnabled(false);
		select.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int res = -1;
		if(type == 'o') {
			res = select.showOpenDialog(parent);
		}else {
			res = select.showSaveDialog(parent);
		}
		if(res == JFileChooser.APPROVE_OPTION) {
			File file = select.getSelectedFile();
			return file;
		}
		return null;
	}
	
	
	
	
	
	public static void saveMap(Board board, File file) {
		DataOutputStream out = null;
		
		try {
			out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			out.writeInt(board.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			int i = 0;
			for(Cell curr : board) {
				System.out.println(file.length());
				System.out.println(i);
				++i;
				String cellType = curr.getClass().getSimpleName();
				if(cellType.equals("Floor")) {
					out.writeInt(0);
					continue;
				}
				
				if(cellType.equals("Border_c")) {
					out.writeInt(1);	
					continue;
				}
				
				if(cellType.equals("Treasure")) {
					out.writeInt(2);
					continue;
				}
				
				if(cellType.equals("Stone")) {
					out.writeInt(3);
					continue;
				}
				System.out.println("err");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void openMap(Board board, File file) {
		DataInputStream in = null;
		
		try {
			in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
			
			int size = in.readInt();
			board.setMatrix(new Matrix<Cell>(size));
			System.out.println(size);
			int row = 0;
			int col = 0;
			for(int i = 0 ; i < size * size ; ++i) {
				
				
				if(i % size == 0 && i != 0) {
					col = 0;
					row++;
				}
				
				int read = in.readInt();
				System.out.println("read : "+read+"col : "+col+" row : "+row+" i : "+i);
				
				if(read == 0) {
					board.set(col,row,new Floor(new Position(col, row),null,board));
					col++;
					continue;
				}
				
				if(read == 1) {
					board.set(col,row,new Border_c(new Position(col, row),board));
					col++;
					continue;
				}
				
				if(read == 2) {
					Treasure t = new Treasure(new Position(col, row),board);
					board.set(col,row,t);
					board.setTreasure(t);
					col++;
					continue;
				}
				
				if(read == 3) {
					board.set(col,row,new Stone(new Position(col, row),board));
					col++;
					continue;
				}
				
				System.out.println("error "+read);
			}
			
		}catch (EOFException eof) {
			System.out.println("eof");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(board);
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
