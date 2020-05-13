package treasure_hunt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class GameFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	
	private ButtonsPanel buttonsPane;
	private MenuBar menuBar;
	private GamePane gamePane;
	
	private Controller controller;
	
	
	/**
	 * Game frame constructor
	 * This constructor build the frame without the grid
	 */
	public GameFrame() {
		super("Treasure Hunt - Game");
		this.setSize(1400,1000);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(1600,700));
		this.setLocationRelativeTo(null);
		
		this.controller = new Controller(this);
		
		// Main container
		Container main = this.getContentPane();
		main.setLayout(new BorderLayout());
		
		// Buttons pane
		ButtonsPanel buttonsPane = new ButtonsPanel(controller);
		main.add(buttonsPane,"North");
		this.buttonsPane = buttonsPane;
		
//		// Game pane
		this.gamePane = new GamePane(this);
		main.add(gamePane);
		
		
		// Menu bar
		this.menuBar = new MenuBar(controller);
		this.setJMenuBar(this.menuBar);
		
		
		this.revalidate();
	
	}
	
	
	
	/**
	 * Getter for a button 
	 * @return	The button'sname
	 */
	public JButton getButton(String name) {
		return this.buttonsPane.getButton(name);
	}
	
	public Controller getController() {
		return this.controller;
	}
	
	
	/**
	 * Getter for a data text field
	 * @return The size field
	 */
	public int getSetting(String name) {
		return this.buttonsPane.getSettings(name);
	}
	
	/**
	 * Getter for the menu items  
	 * @param name	The item's name
	 * @return		The menu item
	 */
	public JMenuItem getMenuItem(String name) {
		return this.menuBar.getItem(name);
	}
	
	/**
	 * Setter for a setting
	 * @param name	The setting name
	 * @param value	The new setting value
	 */
	public void setSetting(String name, int value) {
		this.buttonsPane.setSettings(name,value);
	}
	
	/**
	 * Getter for the gamePane
	 * @return
	 */
	public GamePane getGamePane() {
		return this.gamePane;
	}
	
	
	/**
	 * Set if buttons and menu item is enable or not
	 * @param name
	 * @param value
	 */
	public void setEnable(String name,boolean value) {
		getButton(name).setEnabled(value);
		getMenuItem(name).setEnabled(value);
	}
	
	
	

	
	/**
	 * Class menubar
	 * @author francois
	 *
	 */
	public class MenuBar extends JMenuBar {

		private static final long serialVersionUID = 1L;
		private Map<String,JMenuItem> items;
		
		/**
		 * Constructor
		 * @param controller The controller
		 */
		public MenuBar(ActionListener controller) {
			this.items = new HashMap<String,JMenuItem>();
			
			
			// File menu
			JMenu file = new JMenu( "Files" );
	        file.setMnemonic( 'F' );
	        
	        ViewComponents.makeMenuItem("Save map", file, controller, false, items, "save",KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
	        
	        ViewComponents.makeMenuItem("Open map", file, controller, true, items, "open",KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
	        
	        // Game menu
	        JMenu game = new JMenu("Game");
	        game.setMnemonic('G');
	        
	        ViewComponents.makeMenuItem("New random map", game, controller, true, items, "new",KeyEvent.VK_M, KeyEvent.SHIFT_DOWN_MASK);

	        ViewComponents.makeMenuItem("Replay map", game, controller, false, items, "replay",KeyEvent.VK_R, KeyEvent.SHIFT_DOWN_MASK);
	        	        
	        game.addSeparator();
	        
	        ViewComponents.makeMenuItem("Play auto", game, controller, false, items, "play",KeyEvent.VK_P, KeyEvent.SHIFT_DOWN_MASK);
	        
	        ViewComponents.makeMenuItem("Stop", game, controller, false, items, "stop",KeyEvent.VK_S, KeyEvent.SHIFT_DOWN_MASK);
	        
	        ViewComponents.makeMenuItem("Play round", game, controller, false, items, "round",KeyEvent.VK_SPACE, KeyEvent.CTRL_DOWN_MASK);
	       
	   
	        // Settings menu
	        JMenu settings = new JMenu("Settings");
	        settings.setMnemonic('S');
	        
	        ViewComponents.makeMenuItem("Reset Settings", settings, controller, true, items, "reset",KeyEvent.VK_D, KeyEvent.SHIFT_DOWN_MASK);
	        
	        
	        // Help menu
	        
	        JMenu help = new JMenu("Help");
	        help.setMnemonic('H');
	        
	        ViewComponents.makeMenuItem("Manual", help, controller, true, items, "manual",KeyEvent.VK_M, KeyEvent.CTRL_DOWN_MASK);
	        
	        ViewComponents.makeMenuItem("Join us", help, controller, true, items, "join",KeyEvent.VK_J, KeyEvent.CTRL_DOWN_MASK);
	       
	        
	        
	        this.add(file);
	        this.add(game);
	        this.add(settings);
	        this.add(help);
		}
		
		/**
		 * Getter for menu item
		 * @param name
		 * @return
		 */
		public JMenuItem getItem(String name) {
			return this.items.get(name);
		}

	}
	
	/**
	 * Class ButtonsPanel
	 * @author francois
	 *
	 */
	public class ButtonsPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		private Map<String,JButton> buttons;
		private Map<String,JLabel> settings;
		
		private JComboBox<String> density;
		
		@SuppressWarnings("unused")
		public ButtonsPanel(ActionListener controller) {
			this.settings = new HashMap<String,JLabel>();
			this.buttons = new HashMap<String,JButton>();
			
			this.setLayout(new GridLayout(1,2));
			
			
			// New Game Panel
			JPanel newGamePanel = new JPanel();
			newGamePanel.setBorder(new TitledBorder("Random map"));
			
			JButton newGame = ViewComponents.makeButton("New map",newGamePanel, controller, true, this.buttons, "new");
			
			
			// Manage Game Panel
			JPanel manageGamePanel = new JPanel();
			manageGamePanel.setBorder(new TitledBorder("Manage game"));
			
			JButton play = ViewComponents.makeButton("Play",manageGamePanel, controller, false, this.buttons, "play");
			
			JButton round = ViewComponents.makeButton("Round",manageGamePanel, controller, false, this.buttons, "round");
			
			JButton stop = ViewComponents.makeButton("Stop",manageGamePanel, controller, false, this.buttons, "stop");
			
			JButton replay = ViewComponents.makeButton("Replay",manageGamePanel, controller, false, this.buttons, "replay");
			
			
			// File panel
			JPanel filePanel = new JPanel();
			filePanel.setBorder(new TitledBorder("Manage files"));
			
			JButton save = ViewComponents.makeButton("Save",filePanel, controller, false, this.buttons, "save");
			
			JButton open = ViewComponents.makeButton("Open",filePanel, controller, true, this.buttons, "open");
			
			
			// Mode panel
			JPanel modePane = new JPanel();
			modePane.setBorder(new TitledBorder("Mode"));
			
			JButton switchMode = ViewComponents.makeButton("Open edition mode",modePane, controller, true, buttons, "switch");
			
			// LEFT PANEL
			JPanel leftPanel = new JPanel();
			leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			leftPanel.add(filePanel);
			leftPanel.add(newGamePanel);
			leftPanel.add(manageGamePanel);
			leftPanel.add(modePane);
			
			
			
			// Density panel
			JPanel densityPanel = new JPanel();
			String[] densityModes = {"None","Low","Medium","High"};
			density = new JComboBox<String>(densityModes);
			density.setSelectedIndex(2);
			densityPanel.setBorder(new TitledBorder("Wall density"));
			densityPanel.add(density);
			
			// RIGHT PANEL
			JPanel rightPanel = new JPanel();
			rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			
			rightPanel.add(densityPanel);
			rightPanel.add(ViewComponents.makeButtonArea("Size setting", "Size :","", 10, 120, 10, 2, 50,this.settings,"size"));
			rightPanel.add(ViewComponents.makeButtonArea("Players setting", "","hunter(s)", 1, 10, 1, 2, 3,this.settings,"players"));
			rightPanel.add(ViewComponents.makeButtonArea("Timer setting", "","ms", 100, 2000, 100, 3, 100,this.settings,"timer"));
			
			
			
			this.add(leftPanel);
			this.add(rightPanel);
			
			this.revalidate();
		}

		
		/**
		 * Getter for buttons
		 * @param name	The button's name
		 * @return		The buttons
		 */
		public JButton getButton(String name) {
			return this.buttons.get(name);
		}
		
		/**
		 * Getter for settings
		 * @param name	The setting'a name
		 * @return		The setting value
		 */
		public int getSettings(String name) {
			if(name.equals("density")) {
				return this.density.getSelectedIndex();
			}
			return Integer.parseInt(this.settings.get(name).getText());
		}
		
		/**
		 * Setter for settings
		 * @param name	The setting's name
		 * @param value	The new setting value
		 */
		public void setSettings(String name, int value) {
			if(name.equals("density")) {
				this.density.setSelectedIndex(value);
				return ;
			}
			this.settings.get(name).setText(""+value);
		}

	}
	
	
	/**
	 * Class GamePane
	 * @author François Poguet
	 *
	 */
	public class GamePane extends JSplitPane {
		
		private GameFrame frame;
		
		private static final long serialVersionUID = 1L;
		private Matrix<JLabel> cellLabels;
		private List<JLabel> playersData;
		private boolean gridIsInit;
		
		private JPanel leftPane;
		private JPanel rightPane;
		
		/**
		 * Constructor
		 * @param frame
		 */
		public GamePane(GameFrame frame) {
			super(JSplitPane.HORIZONTAL_SPLIT,new JPanel(),new JPanel());
			this.setResizeWeight(0.85);
			
			
			this.playersData = new ArrayList<JLabel>();
			this.frame = frame;
			this.leftPane = (JPanel) this.getComponent(0);
			this.rightPane = (JPanel) this.getComponent(1);
			
			rightPane.setSize(300, 0);
			rightPane.setMinimumSize(new Dimension(300, 0));
			
			
		}
		

		/**
		 * Initialize the grid game on the frame
		 * @param game The current game
		 */
		public void initGrid(Game game) {
			System.out.println("[Frame]\tgenerating");
			int size = game.getBoard().size();
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
					Cell curr = game.getBoard().get(j, i);
					String cellType = curr.getClass().getSimpleName();
					if(cellType.equals("Floor_c")) {
						cellLabels.get(j, i).setBackground(Color.gray);
						cellLabels.get(j, i).setOpaque(true);
						Floor_c curr_f = (Floor_c)curr;
						if(curr_f.isFull()) {
							cellLabels.get(j, i).setBackground(Color.blue);
							cellLabels.get(j, i).setText(curr_f.toString());
						}
					}
					
					if(cellType.equals("Border_c")) {
						cellLabels.get(j, i).setBackground(Color.red);
						cellLabels.get(j, i).setOpaque(true);
						
					}
					
					if(cellType.equals("Treasure_c")) {
						cellLabels.get(j, i).setBackground(Color.yellow);
						cellLabels.get(j, i).setOpaque(true);
					}
					
					if(cellType.equals("Stone_c")) {
						cellLabels.get(j, i).setBackground(Color.black);
						cellLabels.get(j, i).setOpaque(true);
					}
				}
			}

			
			leftPane.repaint();
			leftPane.revalidate();
			this.gridIsInit = true;
			
			frame.setEnable("play",true);
			frame.setEnable("save",true);
			frame.setEnable("round",true);
			frame.setEnable("replay",true);

			initDataPane(game);
			System.out.println("[Frame]\tready");
		}
		
		/**
		 * Initialize the data pane at the game start
		 * @param game	The current game
		 */
		public void initDataPane(Game game) {
			JPanel pane =  this.rightPane;
			pane.setLayout(new GridLayout(game.getHunters().size(),1));
			this.playersData.clear();
			pane.removeAll();
			
			for(Hunter h : game.getHunters()) {
				System.out.println(h.toString());
				JLabel data = new JLabel();
				data.setFont(new java.awt.Font(Font.DIALOG,Font.BOLD,30));
				data.setText(" "+h.toString()+" : "+h.getPosition()+" - "+Hunter.dirToArrow(h.getDirection()));
				pane.add(data);
				this.playersData.add(data);
			}
			pane.revalidate();
			pane.repaint();
		}
		
		/**
		 * Update the data pane during the game
		 * @param game	The current game
		 */
		public void updateDataPane(Game game) {
			int i = 0;
			for(Hunter h : game.getHunters()) {
				JLabel data = this.playersData.get(i);
				data.setText(" "+h.toString()+" : "+h.getPosition()+" - "+Hunter.dirToArrow(h.getDirection()));
				++i;
			}
		}
		
		/**
		 * Clear a floor label on the grid
		 * @param pos	The floor's position
		 */
		public void clearFloor(Position pos) {
			cellLabels.get(pos.getColumn(), pos.getRow()).setText("");
			cellLabels.get(pos.getColumn(), pos.getRow()).setBackground(Color.gray);
//			this.revalidate();
		}
		
		/**
		 * Update a floor on the grid
		 * @param pos	The floor position
		 * @param board	The current board
		 */
		public void updateFloor(Position pos, Board board) {
			Floor_c curr = (Floor_c)board.get(pos);
			
			if(curr.isFull()) {
				cellLabels.get(pos.getColumn(), pos.getRow()).setText(board.get(pos).toString());
				cellLabels.get(pos.getColumn(), pos.getRow()).setBackground(Color.blue);
			}
			
			
		}
		
		/**
		 * Update the treasure on the grid
		 * @param treasure	The treasure
		 */
		public void updateTreasure(Treasure_c treasure) {
			Position treasurePos = treasure.getPosition();
			if(treasure.isFound()) {
				cellLabels.get(treasurePos.getColumn(), treasurePos.getRow()).setText(treasure.toString());
			}else {
				cellLabels.get(treasurePos.getColumn(), treasurePos.getRow()).setText("");
			}
		}
		
		
		
		/**
		 * Check if a grid is initialized 
		 * @return	True if the grid panel is filled
		 */
		public boolean isInit() {
			return this.gridIsInit;
		}
		
		/**
		 * Setter for the init boolean
		 * @param init The new value
		 */
		public void setInit(boolean init) {
			this.gridIsInit = init;
		}
		
		
		

		
		

	}

	

}
