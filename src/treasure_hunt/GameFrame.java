package treasure_hunt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class GameFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Matrix<JLabel> cellLabels;
	
	private Map<String,JLabel> gameSettings;
	private Map<String,JButton> buttons;
	private List<JLabel> playersData;
	private boolean isInit;

	private JPanel gridPanel;
	private JPanel rightPane;
	
	
	/**
	 * Game frame constructor
	 * This constructor build the frame without the grid
	 */
	public GameFrame() {
		super("Treasure Hunt");
		this.setSize(1400,1000);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(1200,700));
		
		// Attributes setting 
		this.isInit = false;
		cellLabels = null;
		Controller controller = new Controller(this);
		this.gameSettings = new HashMap<String,JLabel>();
		this.buttons = new HashMap<String,JButton>();
		this.playersData = new ArrayList<JLabel>();
		
		// Main container
		Container main = this.getContentPane();
		main.setLayout(new BorderLayout());
		
		// Buttons pane
		main.add(makeButtonsPane(controller),"North");
		
		// Grid pane
		this.gridPanel = new JPanel();
		
		// Right pane
		this.rightPane = new JPanel();
		
		rightPane.setSize(300, 0);
		rightPane.setMinimumSize(new Dimension(300, 0));
		
		// Center pane
		JSplitPane center = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,gridPanel,rightPane);
		center.setResizeWeight(0.85);
		
		main.add(center);
		
		
		this.revalidate();
	
	}
	
	
	
	/**
	 * Initialize the grid game on the frame
	 * @param game The current game
	 */
	public void initGrid(Game game) {
		System.out.println("[Frame]\tgenerating");
		int size = game.getBoard().size();
		cellLabels = new Matrix<JLabel>(size);
		
		
		gridPanel.removeAll();
		gridPanel.setLayout(new GridLayout(size,size));
		
		Border border = BorderFactory.createLineBorder(Color.black,1);
		for(int i = 0 ; i < cellLabels.size(); ++i) {
			for(int j = 0 ; j < cellLabels.size() ; ++j) {
				JLabel curr_l = new JLabel("");
				
				curr_l.setBorder(border);
				curr_l.setHorizontalAlignment(JLabel.CENTER);
				cellLabels.set(j, i, curr_l);
				gridPanel.add(cellLabels.get(j, i));
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

		
		gridPanel.repaint();
		gridPanel.revalidate();
		this.isInit = true;
		getButton("play_game").setEnabled(true);
		getButton("play_round").setEnabled(true);
		getButton("save").setEnabled(true);
		getButton("replay").setEnabled(true);
		
		
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
			data.setText(" "+h.toString()+" : "+h.getPosition()+" - "+dirToArrow(h.getDirection()));
			pane.add(data);
			this.playersData.add(data);
		}
		pane.revalidate();
		pane.repaint();
	}
	
	
	/**
	 * Check if a grid is initialized 
	 * @return	True if the grid panel is filled
	 */
	public boolean isInit() {
		return this.isInit;
	}
	
	/**
	 * Setter for the init boolean
	 * @param init The new value
	 */
	public void setInit(boolean init) {
		this.isInit = init;
	}
	
	
	/**
	 * Getter for a button 
	 * @return	The button'sname
	 */
	public JButton getButton(String name) {
		return this.buttons.get(name);
	}
	
	
	/**
	 * Getter for a data text field
	 * @return The size field
	 */
	public JLabel getData(String name) {
		return this.gameSettings.get(name);
	}
	
	/**
	 * Setter for a setting
	 * @param name	The setting name
	 * @param value	The new setting value
	 */
	public void setSetting(String name, String value) {
		this.gameSettings.get(name).setText(value);
	}
	
	
	/**
	 * Clear a floor label on the grid
	 * @param pos	The floor's position
	 */
	public void clearFloor(Position pos) {
		cellLabels.get(pos.getColumn(), pos.getRow()).setText("");
		cellLabels.get(pos.getColumn(), pos.getRow()).setBackground(Color.gray);
//		this.revalidate();
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
	 * Update the data pane during the game
	 * @param game	The current game
	 */
	public void updateDataPane(Game game) {
		int i = 0;
		for(Hunter h : game.getHunters()) {
			JLabel data = this.playersData.get(i);
			data.setText(" "+h.toString()+" : "+h.getPosition()+" - "+dirToArrow(h.getDirection()));
			++i;
		}
	}
	
	/**
	 * Get the arrow from a direction
	 * @param dir	The direction
	 * @return		The arrow
	 */
	public char dirToArrow(int dir) {
		char arrows[] = {'\u2192','\u2197','\u2191','\u2196','\u2190','\u2199','\u2193','\u2198'};
		return arrows[dir-1];
	}
	
	/**
	 * Make the main panel of buttons at the frame top
	 * @param controller The buttons listener
	 * @return	The buttons panel
	 */
	public JPanel makeButtonsPane(ActionListener controller) {
		JPanel buttonsPane = new JPanel();
		buttonsPane.setLayout(new GridLayout(1,2));
		
		JPanel gamePane = new JPanel();
		gamePane.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JPanel newGamePane = new JPanel();
		newGamePane.setBorder(new TitledBorder("Random map"));
		JButton newGame = new JButton("New map");
		newGame.addActionListener(controller);
		this.buttons.put("new",newGame);
		newGamePane.add(newGame);
		
		JPanel manageGamePane = new JPanel();
		manageGamePane.setBorder(new TitledBorder("Manage game"));
		JButton	playGame = new JButton("Play");
		playGame.setEnabled(false);
		playGame.addActionListener(controller);
		this.buttons.put("play_game",playGame);
		manageGamePane.add(playGame);
		
		JButton playRound = new JButton("Round");
		playRound.setEnabled(false);
		playRound.addActionListener(controller);
		this.buttons.put("play_round",playRound);
		manageGamePane.add(playRound);
		
		JButton stop = new JButton("Stop");
		stop.setEnabled(false);
		stop.addActionListener(controller);
		this.buttons.put("stop",stop);
		manageGamePane.add(stop);
		
		JButton replay = new JButton("Replay");
		replay.setEnabled(false);
		replay.addActionListener(controller);
		this.buttons.put("replay",replay);
		manageGamePane.add(replay);
		
		JPanel filePane = new JPanel();
		filePane.setBorder(new TitledBorder("Manage files"));
		JButton save = new JButton("Save");
		save.setEnabled(false);
		save.addActionListener(controller);
		this.buttons.put("save",save);
		filePane.add(save);
		
		JButton open = new JButton("Open");
		open.addActionListener(controller);
		this.buttons.put("open",open);
		filePane.add(open);
		
		gamePane.add(filePane);
		gamePane.add(newGamePane);
		gamePane.add(manageGamePane);
		
		JPanel settingsPane = new JPanel();
		settingsPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		settingsPane.add(makeButtonArea("Size settings", "Size :","", 10, 120, 10, 2, 50,"size"));
		settingsPane.add(makeButtonArea("Players settings", "","hunter(s)", 1, 10, 1, 2, 3,"players"));
		settingsPane.add(makeButtonArea("Timer settings", "","ms", 100, 2000, 100, 3, 100,"timer"));
		
		buttonsPane.add(gamePane);
		buttonsPane.add(settingsPane);
		
		buttonsPane.revalidate();
		
		return buttonsPane;
	}
	
	
	/**
	 * Make a button area
	 * @param title		The area title
	 * @param prefix	The textField label
	 * @param min		The minimum value
	 * @param max		The maximum value
	 * @param step		The value's step
	 * @param size		The textField size
	 * @param defaultValue	The default value
	 * @return	A button area panel 
	 */
	public JPanel makeButtonArea(String title, String prefix,String suffix, int min, int max, int step, int size, int defaultValue, String name ) {
		JPanel btn = new JPanel();
		btn.setBorder(new TitledBorder(title));
		btn.add(new JLabel(prefix));
		
		
		
		JLabel valuelabel = new JLabel(Integer.toString(defaultValue));
		this.gameSettings.put(name,valuelabel);
		valuelabel.setBorder(null);
		btn.add(valuelabel);
		btn.add(new JLabel(suffix));
		
		JButton up = new JButton("\u2191");
		up.setMargin(new Insets(0, 0, 0, 0));
		up.setSize(10, 5);
		up.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int currentSize = Integer.parseInt(valuelabel.getText());
				if(currentSize < max) {
					valuelabel.setText((currentSize+step)+"");
				}
				
			}
		});
		
		btn.add(up);

		JButton down = new JButton("\u2193");
		down.setMargin(new Insets(0, 0, 0, 0));
		down.setSize(10, 5);
		down.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int currentSize = Integer.parseInt(valuelabel.getText());
				if(currentSize > min) {
					valuelabel.setText((currentSize-step)+"");
				}
				
			}
		});
		btn.add(down);
		return btn;
	}

}
