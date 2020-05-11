package treasure_hunt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

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
	private JFrame frame;

	private Matrix<JLabel> cellLabels;
	
	/**
	 * Constructor
	 * @param frame
	 */
	public EditPane(GameFrame frame) {
		super(JSplitPane.HORIZONTAL_SPLIT,new JPanel(),new JPanel());
		this.setResizeWeight(0.8);
		
		
		this.frame = frame;
		this.leftPane = (JPanel) this.getComponent(0);
		this.rightPane = (JPanel) this.getComponent(1);
		
		rightPane.setSize(300, 0);
		rightPane.setMinimumSize(new Dimension(300, 0));
		
		
		
		
		
		
	}
	
	public void initGrid(int size) {
		System.out.println("[Frame]\tgenerating");
		cellLabels = new Matrix<JLabel>(size);
		
		
		leftPane.removeAll();
		leftPane.setLayout(new GridLayout(size,size));
		
		Border border = BorderFactory.createLineBorder(Color.black,1);
		for(int i = 0 ; i < cellLabels.size(); ++i) {
			for(int j = 0 ; j < cellLabels.size() ; ++j) {
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
				
					
				
				
				
			}
		}

		
		leftPane.repaint();
		leftPane.revalidate();
		

	
		System.out.println("[Frame]\tready");
	}
}
