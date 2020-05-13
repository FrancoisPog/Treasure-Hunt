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
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class EditionFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private ButtonPanel buttonPane;
	private CenterPane centerPanel;
		
	public EditionFrame(Controller controller) {
		super("Treasure Hunt - Edition");
		this.setSize(1400,1000);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setMinimumSize(new Dimension(1600,700));
		this.setLocationRelativeTo(null);
		
		
		
		
		Container main = this.getContentPane();
		main.setLayout(new BorderLayout());
		
		this.buttonPane = new ButtonPanel(controller);
		main.add(buttonPane,"North");
		
		this.centerPanel = new CenterPane(this);
		main.add(centerPanel,"Center");
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				controller.closingEditor();
			}
		});
		
		this.revalidate();
	}
	
	
	
	
	
	public ButtonPanel getButtonPane() {
		return buttonPane;
	}

	public CenterPane getCenterPanel() {
		return centerPanel;
	}





	public class ButtonPanel extends JPanel{
		
		private static final long serialVersionUID = 1L;
		private Map<String,JButton> buttons;
		private Map<String,JLabel> settings;
		
		
		public ButtonPanel(ActionListener controller) {
			this.buttons = new HashMap<String, JButton>();
			this.settings = new HashMap<String, JLabel>();
			
			this.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			// Game panel
			JPanel gamePanel = new JPanel();
			gamePanel.setBorder(new TitledBorder("Play this board"));
			
			ViewComponents.makeButton("Send map to game", gamePanel, controller, true, buttons, "play");
			
			JPanel emptyPanel = new JPanel();
			emptyPanel.setBorder(new TitledBorder("New empty map"));
			ViewComponents.makeButton("   Generate   ", emptyPanel, controller, true, buttons, "empty");
			
			this.add(gamePanel);
			this.add(emptyPanel);
			this.add(ViewComponents.makeButtonArea("  Configure size  ", " size :", "", 10, 120, 10, 3, 50, settings, "size"));
			
			this.revalidate();
			
		}
		
		public JButton getButton(String name) {
			return buttons.get(name);
		}
		
		/**
		 * Getter for settings
		 * @param name	The setting'a name
		 * @return		The setting value
		 */
		public int getSettings(String name) {
			return Integer.parseInt(this.settings.get(name).getText());
		}
		
		/**
		 * Setter for settings
		 * @param name	The setting's name
		 * @param value	The new setting value
		 */
		public void setSettings(String name, int value) {
			this.settings.get(name).setText(""+value);
		}
		
	}
	
	public class CenterPane extends JSplitPane {
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
		public CenterPane(JFrame frame) {
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
		
		public void initGrid(Game game) {
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
		
		public boolean isColor(int col, int row, Color color) {
			return this.getMatrix().get(col, row).getBackground() == Color.black;
		}
		
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
		
		
		
		public Matrix<JLabel> getMatrix(){
			return this.cellLabels;
		}
		
				
	}

}
