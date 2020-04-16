package treasure_hunt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
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
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class GameFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Matrix<JLabel> cellLabels;
	
	private Map<String,JTextField> gameData;
	private Map<String,JButton> buttons;
	private List<JLabel> playersData;
	private boolean isInit;

	private JPanel gridPanel;
	
	
	
	/**
	 * Game frame constructor
	 * This constructor build the frame without the grid
	 */
	public GameFrame() {
		super("Treasure Hunt");
		this.setSize(1400,1000);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setMinimumSize(new Dimension(800,200));
		
		// Attributes setting 
		this.isInit = false;
		cellLabels = null;
		Controller controller = new Controller(this);
		this.gameData = new HashMap<String,JTextField>();
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
		
		JPanel rightPane = new JPanel();
		rightPane.setSize(200, 0);
		rightPane.setMinimumSize(new Dimension(400, 0));
		
		// Center pane
		JSplitPane center = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,gridPanel,rightPane);
		center.setResizeWeight(0.66);
		
		main.add(center);
		
		
		this.revalidate();
	
	}
	
	/**
	 * Make the main panel of buttons of the frame
	 * @param controller The buttons listener
	 * @return	The buttons panel
	 */
	public JPanel makeButtonsPane(ActionListener controller) {
		JPanel buttonsPane = new JPanel();
		
		JButton newGame = new JButton("New map");
		newGame.addActionListener(controller);
		this.buttons.put("new",newGame);
		buttonsPane.add(newGame);
		
		JButton	playGame = new JButton("Play");
		playGame.setEnabled(false);
		playGame.addActionListener(controller);
		this.buttons.put("play_game",playGame);
		buttonsPane.add(playGame);
		
		JButton save = new JButton("Save");
		save.setEnabled(false);
		save.addActionListener(controller);
		this.buttons.put("save",save);
		buttonsPane.add(save);
		
		JButton open = new JButton("Open");
		open.addActionListener(controller);
		this.buttons.put("open",open);
		buttonsPane.add(open);
		
		JButton replay = new JButton("Replay");
		replay.setEnabled(false);
		replay.addActionListener(controller);
		this.buttons.put("replay",replay);
		buttonsPane.add(replay);
		
		buttonsPane.add(makeButtonArea("Size settings", "Size :", 10, 120, 10, 3, 50,"size"));
		buttonsPane.add(makeButtonArea("Players settings", "Players : ", 1, 20, 1, 2, 3,"players"));
		buttonsPane.add(makeButtonArea("Timer settings", "Timer : ", 100, 2000, 100, 4, 100,"timer"));
		return buttonsPane;
	}
	
	/**
	 * Initialize the grid game on the frame
	 * @param game The current game
	 */
	public void initGrid(Game game) {
		System.out.println("Frame grid initialization");
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
		getButton("save").setEnabled(true);
		getButton("replay").setEnabled(true);
	}
	
	
	public boolean isInit() {
		return this.isInit;
	}
	
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
	public JTextField getData(String name) {
		return this.gameData.get(name);
	}
	
	public void setData(String name, String value) {
		this.gameData.get(name).setText(value);
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
	 * Update a cell on the grid
	 * @param pos	The cell position
	 * @param board	The current board
	 */
	public void updateFloor(Position pos, Board board) {
		Floor_c curr = (Floor_c)board.get(pos);
		
		if(curr.isFull()) {
			cellLabels.get(pos.getColumn(), pos.getRow()).setText(board.get(pos).toString());
			cellLabels.get(pos.getColumn(), pos.getRow()).setBackground(Color.blue);
		}
		
		
	}
	
	public void updateTreasure(Treasure_c treasure) {
		
		Position treasurePos = treasure.getPosition();
		if(treasure.isFound()) {
			cellLabels.get(treasurePos.getColumn(), treasurePos.getRow()).setText(treasure.toString());
		}else {
			cellLabels.get(treasurePos.getColumn(), treasurePos.getRow()).setText("");
		}
		
		
	}
	
	
	/**
	 * Make a button area
	 * @param title		The area title
	 * @param subTitle	The textField label
	 * @param min		The minimum value
	 * @param max		The maximum value
	 * @param step		The value's step
	 * @param size		The textField size
	 * @param defaultValue	The default value
	 * @return	A button area panel 
	 */
	public JPanel makeButtonArea(String title, String subTitle, int min, int max, int step, int size, int defaultValue, String name ) {
		JPanel btn = new JPanel();
		btn.setBorder(new TitledBorder(title));
		btn.add(new JLabel(subTitle));
		
		
		
		JTextField sizeField = new JTextField(Integer.toString(defaultValue));
		this.gameData.put(name,sizeField);
		sizeField.setEditable(false);
		sizeField.setColumns(size);
		btn.add(sizeField);
		
		JButton up = new JButton("\u2191");
		up.setMargin(new Insets(0, 0, 0, 0));
		up.setSize(10, 5);
		up.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int currentSize = Integer.parseInt(sizeField.getText());
				if(currentSize < max) {
					sizeField.setText((currentSize+step)+"");
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
				int currentSize = Integer.parseInt(sizeField.getText());
				if(currentSize > min) {
					sizeField.setText((currentSize-step)+"");
				}
				
			}
		});
		btn.add(down);
		return btn;
	}

}
