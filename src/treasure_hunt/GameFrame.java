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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;



/**
 * <p><strong>GameFrame</strong> is the class representing the game frame.<p>
 * <p>A game frame is characterized by : </p>
 * <ul>
 * 		<li>A button panel</li>
 * 		<li>A menu bar</li>
 * 		<li>A game panel</li>
 * </ul>
 * @see treasure_hunt.GameFrame.GameButtonsPanel
 * @see treasure_hunt.GameFrame.GameMenuBar
 * @see treasure_hunt.GameFrame.GamePanel
 * 
 * @author François Poguet
 * @author Enzo Costantini
 */
public class GameFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	
	private GameButtonsPanel buttonsPanel;
	private GameMenuBar menuBar;
	private GamePanel gamePanel;
	
	private Controller controller;
	
	
	/**
	 * Game frame constructor
	 * This constructor build the frame without the grid
	 */
	public GameFrame() {
		super("Treasure Hunt - Game");
		this.setSize(1800,1000);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(1775,850));
		this.setLocationRelativeTo(null);
		
		this.controller = new Controller(this);
		
		// Main container
		Container main = this.getContentPane();
		main.setLayout(new BorderLayout());
		
		// Buttons pane
		GameButtonsPanel buttonsPane = new GameButtonsPanel(controller);
		main.add(buttonsPane,"North");
		this.buttonsPanel = buttonsPane;
		
		// Game pane
		this.gamePanel = new GamePanel(this);
		main.add(gamePanel);
		
		
		// Menu bar
		this.menuBar = new GameMenuBar(controller);
		this.setJMenuBar(this.menuBar);
		
		
		this.revalidate();
	
	}
	
	/**
	 * Getter for the buttons panel
	 * @return	The buttons panel
	 */
	public GameButtonsPanel getButtonPanel() {
		return buttonsPanel;
	}

	/**
	 * Getter for the game panel
	 * @return	The center panel
	 */
	public GamePanel getGamePanel() {
		return gamePanel;
	}
	
	
	
	/**
	 * Getter for the controller
	 * @return	The controller
	 */
	public Controller getController() {
		return this.controller;
	}
	
	
	/**
	 * Getter for the game menu bar
	 * @return	The menu bar
	 */
	public GameMenuBar getGameMenuBar() {
		return this.menuBar;
	}

	
	
	/**
	 * Set if buttons and menu item is enable or not
	 * @param name	The component name
	 * @param value	The new value
	 */
	public void setEnable(String name,boolean value) {
		getButtonPanel().getButton(name).setEnabled(value);
		getGameMenuBar().getItem(name).setEnabled(value);
	}
	
	
	

	
	/**
	 * <p><strong>Menubar</strong> is a inner class to manage the menu bar and keyboard shortcuts .<p>
	 * <p>A Menu bar is characterized by : </p>
	 * <ul>
	 * 		<li>A item list</li>
	 * </ul>
	
	 * 
	 * @author François Poguet
	 * @author Enzo Costantini
	 */
	public class GameMenuBar extends MenuBar {
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor
		 * @param controller The controller
		 */
		public GameMenuBar(ActionListener controller) {
			super();
			
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
	        
	        
	        // Editor menu
	        JMenu editor = new JMenu("Editor");
	        editor.setMnemonic('E');
	        
	        ViewComponents.makeMenuItem("Open editor", editor, controller, true, items, "editor", KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK);
	        ViewComponents.makeMenuItem("Send map to editor", editor, controller, false, items, "send", KeyEvent.VK_E, KeyEvent.SHIFT_DOWN_MASK);
	       
	   
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
	        this.add(editor);
	        this.add(settings);
	        this.add(help);
		}
		
		
	}
	
	/**
	 * <p><strong>ButtonsPanel</strong> is a inner class to manage the buttons panel of the frame.<p>
	 * <p>A buttons panel is characterized by : </p>
	 * <ul>
	 * 		<li>A buttons list</li>
	 * 		<li>A label list : the settings</li>
	 * 		<li>Other components like checkbox or combobox</li>
	 * </ul>
	 * @author François Poguet
	 * @author Enzo Costantini
	 */
	public class GameButtonsPanel extends ButtonsPanel {

		private static final long serialVersionUID = 1L;
		
		private JCheckBox huntersRandom;
		private JComboBox<String> density;
		
		public GameButtonsPanel(ActionListener controller) {
			super();
			
			this.setLayout(new GridLayout(1,2));
			
			// New Game Panel
			JPanel newGamePanel = new JPanel();
			newGamePanel.setBorder(new TitledBorder("Random map"));
			
			newGamePanel.add(ViewComponents.makeButton("New map",controller, true, this.buttons, "new"));
			
			
			// Manage Game Panel
			JPanel manageGamePanel = new JPanel();
			manageGamePanel.setBorder(new TitledBorder("Manage game"));
			
			manageGamePanel.add(ViewComponents.makeButton("Play", controller, false, this.buttons, "play"));
			
			manageGamePanel.add(ViewComponents.makeButton("Round", controller, false, this.buttons, "round"));
			
			manageGamePanel.add(ViewComponents.makeButton("Stop", controller, false, this.buttons, "stop"));
			
			manageGamePanel.add(ViewComponents.makeButton("Replay", controller, false, this.buttons, "replay"));
			
			
			// File panel
			JPanel filePanel = new JPanel();
			filePanel.setBorder(new TitledBorder("Manage files"));
			
			filePanel.add(ViewComponents.makeButton("Save", controller, false, this.buttons, "save"));
			
			filePanel.add(ViewComponents.makeButton("Open", controller, true, this.buttons, "open"));
			
			
			// Mode panel
			JPanel modePane = new JPanel();
			modePane.setBorder(new TitledBorder("Editor"));
			
			modePane.add(ViewComponents.makeButton("Open editor", controller, true, buttons, "editor"));
			modePane.add(ViewComponents.makeButton("Send map to editor", controller, false, buttons, "send"));
			
			// LEFT PANEL
			JPanel leftPanel = new JPanel();
			leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			leftPanel.add(filePanel);
			leftPanel.add(newGamePanel);
			leftPanel.add(manageGamePanel);
			leftPanel.add(modePane);
			
			
			JPanel huntersRandomPanel = new JPanel();
			huntersRandomPanel.setBorder(new TitledBorder("Players position"));
			
			this.huntersRandom = new JCheckBox("Random       ");
			huntersRandom.setSelected(true);
			huntersRandomPanel.add(huntersRandom);
			
			
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
			
			JLabel titleLabel = new JLabel("Game mode");
			titleLabel.setBorder(new CompoundBorder(titleLabel.getBorder(),new EmptyBorder(0,0,0,100)));
			titleLabel.setFont(new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, 30));
			
			rightPanel.add(titleLabel);
			rightPanel.add(huntersRandomPanel);
			rightPanel.add(densityPanel);
			rightPanel.add(ViewComponents.makeButtonArea("Size setting", "Size :","", 10, 120, 10, 2, 50,this.settings,"size"));
			rightPanel.add(ViewComponents.makeButtonArea("Players setting", "","hunter(s)", 1, 10, 1, 2, 3,this.settings,"players"));
			rightPanel.add(ViewComponents.makeButtonArea("Timer setting", "","ms", 100, 2000, 100, 3, 100,this.settings,"timer"));
			
			
			
			this.add(leftPanel);
			this.add(rightPanel);
			
			this.revalidate();
		}

		
		/**
		 * Getter for settings
		 * @param name	The setting'a name
		 * @return		The setting value
		 */
		@Override
		public int getSettings(String name) {
			if(name.equals("density")) {
				return this.density.getSelectedIndex();
			}
			if(name.equals("randomHunters")) {
				return this.huntersRandom.isSelected()?1:0;
			}
			return super.getSettings(name);
		}
		
		/**
		 * Setter for settings
		 * @param name	The setting's name
		 * @param value	The new setting value
		 */
		@Override
		public void setSettings(String name, int value) {
			if(name.equals("density")) {
				this.density.setSelectedIndex(value);
				return ;
			}
			if(name.equals("randomHunters")) {
				this.huntersRandom.setSelected((value == 0)?false:true);
				return;
			}
			super.setSettings(name, value);
		}

	}
	
	
	/**<p><strong>GamePanel</strong> is a inner class to manage the main panel of the frame.<p>
	 * <p>A game panel is characterized by : </p>
	 * <ul>
	 * 		<li>A matrix of label</li>
	 * 		<li>Some players data (position, direction)</li>
	 * </ul>
	 * @author François Poguet
	 * @author Enzo Costantini
	 */ 
	public class GamePanel extends JSplitPane {
		
		private GameFrame frame;
		
		private static final long serialVersionUID = 1L;
		private Matrix<JLabel> cellLabels;
		private List<JLabel> playersData;
		private boolean gridIsInit;
		
		private JPanel leftPanel;
		private JPanel rightPanel;
		
		/**
		 * Constructor
		 * @param frame
		 */
		public GamePanel(GameFrame frame) {
			super(JSplitPane.HORIZONTAL_SPLIT,new JPanel(),new JPanel());
			this.setResizeWeight(0.85);
			
			
			
			this.playersData = new ArrayList<JLabel>();
			this.frame = frame;
			this.leftPanel = (JPanel) this.getComponent(0);
			this.rightPanel = (JPanel) this.getComponent(1);
			

			JLabel logo = new JLabel(new ImageIcon("logo1.png"));
			logo.setBorder(new CompoundBorder(logo.getBorder(),new EmptyBorder(200,0,0,0)));
			leftPanel.add(logo);
			
			JLabel logo2 = new JLabel(new ImageIcon("logo3.png"));
			logo2.setBorder(new CompoundBorder(logo2.getBorder(),new EmptyBorder(00,0,00,0)));
			logo2.setPreferredSize(new Dimension(1000, 100));
			leftPanel.add(logo2);
			

			
			
			rightPanel.setSize(300, 0);
			rightPanel.setMinimumSize(new Dimension(300, 0));
			
			this.revalidate();
			this.repaint();
		}
		

		/**
		 * Initialize the grid game on the frame
		 * @param game The current game
		 */
		public void initGrid(Game game) {
			
			int size = game.getBoard().size();
			cellLabels = new Matrix<JLabel>(size);
			
			
			leftPanel.removeAll();
			leftPanel.setLayout(new GridLayout(size,size));
			
			Border border = BorderFactory.createLineBorder(Color.black,1);
			for(int i = 0 ; i < cellLabels.size(); ++i) {
				for(int j = 0 ; j < cellLabels.size() ; ++j) {
					JLabel curr_l = new JLabel("");
					
					curr_l.setBorder(border);
					curr_l.setHorizontalAlignment(JLabel.CENTER);
					cellLabels.set(j, i, curr_l);
					
					JLabel label = cellLabels.get(j, i);
					label.setOpaque(true);
					
					leftPanel.add(label);
					Cell curr = game.getBoard().get(j, i);
					
					label.setBackground(curr.color());
					
					if(curr.color() == Color.blue) {
						label.setText(curr.toString());
					}
					
					// We can't place a hunter on a corner because he could be blocked
					if((j == 1 && i == 1) || (j == cellLabels.size()-2 && i == 1) || (j == cellLabels.size()-2 && i == cellLabels.size()-2) ||(j == 1 && i == cellLabels.size()-2)) {
						continue;
					}
					
					final int i_inner = i;
					final int j_inner = j;
					
					// Set the listener to place hunters by clicking
					if(curr.color() == Color.blue || curr.color() == Color.gray) {
						label.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {
								controller.addHunter(j_inner, i_inner);
							}
						});
					}
				}
			}

			
			leftPanel.repaint();
			leftPanel.revalidate();
			this.gridIsInit = true;
			
			frame.setEnable("play",true);
			frame.setEnable("save",true);
			frame.setEnable("round",true);
			frame.setEnable("replay",true);

			initDataPanel(game);
			
		}
		
		/**
		 * Initialize the data panel at the game start
		 * @param game	The current game
		 */
		public void initDataPanel(Game game) {
			JPanel pane =  this.rightPanel;
			pane.setLayout(new GridLayout(game.getHunters().size(),1));
			this.playersData.clear();
			pane.removeAll();
			
			for(Hunter h : game.getHunters()) {
				
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
			}else {
				cellLabels.get(pos.getColumn(), pos.getRow()).setText("");
				cellLabels.get(pos.getColumn(), pos.getRow()).setBackground(Color.gray);
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
		
		public void addMessage(String msg_str) {
			JLabel msg = new JLabel(msg_str);
			msg.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 20));
			this.rightPanel.removeAll();
			this.rightPanel.setLayout(new GridLayout());
			this.rightPanel.add(msg);
			this.rightPanel.revalidate();
			this.rightPanel.repaint();
		}
		
		

		
		

	}

	

}
