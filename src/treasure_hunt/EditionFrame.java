package treasure_hunt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * <p><strong>EditionFrame</strong> is the class representing the edition frame.<p>
 * <p>An edition frame is characterized by : </p>
 * <ul>
 * 		<li>A button panel</li>
 * 		<li>A center panel</li>
 * </ul>
 * @see treasure_hunt.EditionFrame.EditionButtonsPanel
 * @see treasure_hunt.EditionFrame.CenterPanel
 * 
 * @author François Poguet
 * @author Enzo Costantini
 */
public class EditionFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private EditionButtonsPanel buttonPane;
	private CenterPanel centerPanel;
		
	/**
	 * Default edition frame constructor
	 * @param controller	The controller used in the main frame
	 */
	public EditionFrame(Controller controller) {
		super("Treasure Hunt - Edition");
		this.setSize(1000,700);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setMinimumSize(new Dimension(1000,700));
		this.setLocationRelativeTo(null);
		
		
		
		
		Container main = this.getContentPane();
		main.setLayout(new BorderLayout());
		
		this.buttonPane = new EditionButtonsPanel(controller);
		main.add(buttonPane,"North");
		
		this.centerPanel = new CenterPanel(this);
		main.add(centerPanel,"Center");
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				controller.closingEditor();
			}
		});
		
		this.revalidate();
	}
	
	/**
	 * Getter for the buttons panel
	 * @return	The buttons panel
	 */
	public EditionButtonsPanel getButtonPanel() {
		return buttonPane;
	}

	/**
	 * Getter for the center panel
	 * @return	The center panel
	 */
	public CenterPanel getCenterPanel() {
		return centerPanel;
	}




	/**
	 * <p><strong>ButtonsPanel</strong> is a inner class to manage the buttons panel of the frame.<p>
	 * <p>A buttons panel is characterized by : </p>
	 * <ul>
	 * 		<li>A buttons list</li>
	 * 		<li>A label list : the settings</li>
	 * </ul>
	 * @author François Poguet
	 * @author Enzo Costantini
	 */
	public class EditionButtonsPanel extends ButtonsPanel{
		
		private static final long serialVersionUID = 1L;
		
		
		/**
		 * Buttons panel default constructor
		 * @param controller	The frame's controller 
		 */
		public EditionButtonsPanel(ActionListener controller) {
			super();
			
			this.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			// Game panel
			JPanel gamePanel = new JPanel();
			gamePanel.setBorder(new TitledBorder("Play this board"));
			gamePanel.add(ViewComponents.makeButton("Send map to game", controller, true, this.buttons, "play"));
			
			JPanel emptyPanel = new JPanel();
			emptyPanel.setBorder(new TitledBorder("New empty map"));
			emptyPanel.add(ViewComponents.makeButton("   Generate   ", controller, true, this.buttons, "empty"));
			
			this.add(gamePanel);
			this.add(emptyPanel);
			this.add(ViewComponents.makeButtonArea("  Configure size  ", " size :", "", 10, 120, 10, 3, 30, this.settings, "size"));
			
			this.revalidate();
		}
		
		
		
	}
	
	
	/**<p><strong>CenterPanel</strong> is a inner class to manage the main panel of the frame.<p>
	 * <p>A center panel is characterized by : </p>
	 * <ul>
	 * 		<li>A matrix of label</li>
	 * </ul>
	 * @author François Poguet
	 * @author Enzo Costantini
	 */ 
	public class CenterPanel extends JSplitPane {
		
		private static final long serialVersionUID = 1L;
		private JPanel leftPane;
		private JPanel rightPane;
		
		private ArrayList<JLabel> colorLabels;
		private Color currentColor;

		private Matrix<JLabel> cellLabels;
		private JLabel treasure;
		
		
		/**
		 * Default center panel constructor
		 * @param frame	The main frame
		 */
		public CenterPanel(JFrame frame) {
			super(JSplitPane.HORIZONTAL_SPLIT,new JPanel(),new JPanel());
			this.setResizeWeight(0.8);
			
		
			this.leftPane = (JPanel) this.getComponent(0);
			this.rightPane = (JPanel) this.getComponent(1);
			
			this.colorLabels = new ArrayList<JLabel>();
			this.rightPane = makeRightPane(rightPane);
			
			rightPane.setSize(300, 0);
			rightPane.setMinimumSize(new Dimension(300, 0));
			
			
			this.currentColor = Color.yellow;
			this.treasure = null;
						
		}
		
		/**
		 * Check if a treasure in set
		 * @return	a boolean
		 */
		public boolean treasureIsInit() {
			return this.treasure != null;
		}
		
		/**
		 * Make the right panel
		 * @param rightPane	The panel to use
		 * @return	The panel modified
		 */
		public JPanel makeRightPane(JPanel rightPane) {
			rightPane.setLayout(new GridLayout(3,1,0,0));
			Color colors[] = {Color.black,Color.yellow};
			String names[] = {"Wall","Treasure"};
			
			
			
			int i = 0;
			for(Color color : colors) {
				JPanel colorPanel = new JPanel(new GridLayout(1,2));
				
				
				JLabel label = new JLabel("");
				colorLabels.add(label);
				if(color == Color.yellow) {
					label.setBorder(BorderFactory.createLineBorder(Color.gray,3));
				}else {
					label.setBorder(BorderFactory.createLineBorder(Color.black,1));
				}
				label.setOpaque(true);
				label.setBackground(color);
				label.setPreferredSize(new Dimension(30,30));
				label.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent event){
						currentColor = color;
						for(JLabel colorLabel : colorLabels) {
							colorLabel.setBorder(BorderFactory.createLineBorder(Color.black,1));
						}
						label.setBorder(BorderFactory.createLineBorder(Color.gray,3));
					}
				});
				
				colorPanel.setBorder(new CompoundBorder(colorPanel.getBorder(), new EmptyBorder(50,10,50,50)));
				
				colorPanel.add(label);
				
				JLabel name = new JLabel(names[i]);
				name.setHorizontalAlignment(JLabel.CENTER);
				colorPanel.add(name);
				
				rightPane.add(colorPanel);
				i++;
			}
			
			rightPane.revalidate();
			
			return rightPane;
		}
		
		/**
		 * initialize an empty label matrix
		 * @param size	The board size
		 */
		public void initGrid(int size) {
			this.treasure = null;
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
		
		/**
		 * initialize a label matrix from a game board
		 * @param game	The game used
		 */
		public void initGrid(Game game) {
			this.treasure = null;
			System.out.println("[Frame]\tgenerating");
			int size = game.getBoard().size();
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
					
					Color color = game.getBoard().get(new Position(j, i)).color();
					cellLabels.get(j, i).setBackground(color);
					if(color == Color.blue) {
						cellLabels.get(j, i).setBackground(Color.gray);
					}
					if(color == Color.yellow) {
						this.treasure = cellLabels.get(j, i);
					}
					
					
					
					
					
					final int iInner = i;
					final int jInner = j;
					
					cellLabels.get(j, i).addMouseListener(new MouseAdapter() {
						public void mousePressed(MouseEvent event) {
							changeColor(jInner,iInner);
						}
					});
					
					
				}
			}

			
			leftPane.repaint();
			leftPane.revalidate();
			

		
			System.out.println("[Frame]\tready");
		}
		
		/**
		 * Check is a label is of a given color
		 * @param col	The label column
		 * @param row	The label row
		 * @param color	The given color
		 * @return		A boolean
		 */
		public boolean isColor(int col, int row, Color color) {
			return this.getMatrix().get(col, row).getBackground() == Color.black;
		}
		
		/**
		 * Change the color of a label
		 * @param col	The label column
		 * @param row	The label row
		 */
		public void changeColor(int col, int row) {
			JLabel cell = cellLabels.get(col, row);
			Color cellBg = cell.getBackground();
			Color color = this.currentColor;
			if(color == Color.black) {
				if(cellBg == Color.black) {
					cell.setBackground(Color.gray);
				}else {
					if(isColor(col-1, row-1,Color.black) || isColor(col-1, row+1,Color.black) || isColor(col+1, row-1,Color.black) || isColor(col+1, row+1,Color.black)) {
						return;
					}
					
					if( col == 1 || col == getMatrix().size()-2 || row == 1 || row == getMatrix().size()-2) {
						return;
					}
					
					
					
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
		
		
		/**
		 * Getter for the labe matrix
		 * @return
		 */
		public Matrix<JLabel> getMatrix(){
			return this.cellLabels;
		}
		
				
	}

}
