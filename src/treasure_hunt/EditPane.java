package treasure_hunt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.Border;

public class EditPane extends JSplitPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel leftPane;
	private JPanel rightPane;
	
	
	private Color currentColor;

	private Matrix<JLabel> cellLabels;
	private JLabel treasure;
	
	
	/**
	 * Constructor
	 * @param frame
	 */
	public EditPane(GameFrame frame) {
		super(JSplitPane.HORIZONTAL_SPLIT,new JPanel(),new JPanel());
		this.setResizeWeight(0.8);
		
	
		this.leftPane = (JPanel) this.getComponent(0);
		this.rightPane = (JPanel) this.getComponent(1);
		
		this.rightPane = makeRightPane(rightPane);
		
		rightPane.setSize(300, 0);
		rightPane.setMinimumSize(new Dimension(300, 0));
		
		this.currentColor = Color.yellow;
		this.treasure = null;
		
		
		
	}
	
	
	public JPanel makeRightPane(JPanel rightPane) {
		rightPane.setLayout(new GridLayout());
		Color colors[] = {Color.black,Color.yellow};
		
		for(Color color : colors) {
			JLabel label = new JLabel("");
			label.setBorder(BorderFactory.createLineBorder(Color.black,1));
			label.setOpaque(true);
			label.setBackground(color);
			label.setPreferredSize(new Dimension(30,30));
			label.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent event){
					currentColor = color;
				}
			});
			
			
			rightPane.add(label);
		}
		
		rightPane.revalidate();
		
		return rightPane;
	}
	
	public void initGrid(int size) {
		System.out.println("[Frame]\tgenerating");
		cellLabels = new Matrix<JLabel>(size);
		
		
		leftPane.removeAll();
		leftPane.setLayout(new GridLayout(size,size));
		
		Border border = BorderFactory.createLineBorder(Color.black,1);
		int i,j;
		for(i = 0 ; i < cellLabels.size(); ++i) {
			for(j = 0 ; j < cellLabels.size() ; ++j) {
				JLabel curr_l = new JLabel("");
				
				curr_l.setBorder(border);
				curr_l.setHorizontalAlignment(JLabel.CENTER);
				cellLabels.set(j, i, curr_l);
				leftPane.add(cellLabels.get(j, i));
				cellLabels.get(j, i).setOpaque(true);
				
				if(i == size-1 || i == 0 || j == 0 || j == size-1) {
					cellLabels.get(j, i).setBackground(Color.red);
					continue;
				}
				
				cellLabels.get(j, i).setBackground(Color.gray);
				
				final int iInner = i;
				final int jInner = j;
				
				cellLabels.get(j, i).addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent event) {
						changeColor(jInner,iInner);
					}
				});
				
				
			}
		}

		
		leftPane.repaint();
		leftPane.revalidate();
		

	
		System.out.println("[Frame]\tready");
	}
	
	public void changeColor(int col, int row) {
		JLabel cell = cellLabels.get(col, row);
		Color cellBg = cell.getBackground();
		Color color = this.currentColor;
		if(color == Color.black) {
			if(cellBg == Color.black) {
				cell.setBackground(Color.gray);
			}else {
				if(cellBg == Color.yellow) {
					this.treasure = null;
				}
				cell.setBackground(Color.black);
			}
		}
		
		if(color == Color.yellow) {
			if(cellBg == Color.yellow) {
				cell.setBackground(Color.gray);
				this.treasure = null;
			}else {
				cell.setBackground(Color.yellow);
				if(this.treasure != null) {
					this.treasure.setBackground(Color.gray);
				}
				this.treasure = cell;
			}
		}
		
	}
	
	
	
	
	
	
	public Matrix<JLabel> getMatrix(){
		return this.cellLabels;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
