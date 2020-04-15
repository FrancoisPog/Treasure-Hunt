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
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class GameFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Matrix<JLabel> cellLabels;
	private List<JTextField> gameData;
	private List<JButton> buttons;
	
	private boolean isInit;

	private JPanel gridPanel;
	
	
	
	/**
	 * Game frame constructor
	 * This constructor build the frame without the grid
	 */
	public GameFrame() {
		super("Treasure Hunt");
//		try {
//			UIManager.setLookAndFeel(new NimbusLookAndFeel());
//		} catch (UnsupportedLookAndFeelException e) {}
		this.setSize(1100,1000);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setMinimumSize(new Dimension(800,200));
		
		this.isInit = false;
		
		cellLabels = null;
		Controller controller = new Controller(this);
		this.gameData = new ArrayList<JTextField>();
		this.buttons = new ArrayList<JButton>();
		
		
		Container main = this.getContentPane();
		main.setLayout(new BorderLayout());
		
		
		
		
		main.add(makeButtonsPane(controller),"North");
		
		this.gridPanel = new JPanel();
		
		
		main.add(gridPanel);
		
		this.revalidate();
	
	}
	
	/**
	 * Make the main panel of buttons of the frame
	 * @param controller The buttons listener
	 * @return	The buttons panel
	 */
	public JPanel makeButtonsPane(ActionListener controller) {
		JPanel buttonsPane = new JPanel();
		
		JButton launch = new JButton("New game");
		launch.addActionListener(controller);
		this.buttons.add(launch);
		buttonsPane.add(launch);
		
		JButton	nextRoundButton = new JButton("Launch game");
		nextRoundButton.setEnabled(false);
		nextRoundButton.addActionListener(controller);
		this.buttons.add(nextRoundButton);
		buttonsPane.add(nextRoundButton);
		
		buttonsPane.add(makeButtonArea("Size settings", "Size :", 10, 200, 10, 3, 50));
		buttonsPane.add(makeButtonArea("Players settings", "Players : ", 1, 20, 1, 2, 3));
		return buttonsPane;
	}
	
	/**
	 * Initialize the grid game on the frame
	 * @param game The current game
	 */
	public void initGrid(Game game) {
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
				if(cellType.equals("Floor")) {
					cellLabels.get(j, i).setBackground(Color.gray);
					cellLabels.get(j, i).setOpaque(true);
					Floor curr_f = (Floor)curr;
					if(curr_f.isFull()) {
						cellLabels.get(j, i).setBackground(Color.blue);
						cellLabels.get(j, i).setText(curr_f.toString());
					}
				}
				
				if(cellType.equals("Border_c")) {
					cellLabels.get(j, i).setBackground(Color.red);
					cellLabels.get(j, i).setOpaque(true);
					
				}
				
				if(cellType.equals("Treasure")) {
					cellLabels.get(j, i).setBackground(Color.yellow);
					cellLabels.get(j, i).setOpaque(true);
				}
				
				if(cellType.equals("Stone")) {
					cellLabels.get(j, i).setBackground(Color.black);
					cellLabels.get(j, i).setOpaque(true);
				}
			}
		}
		this.repaint();
		this.isInit = true;
		getNextButton().setEnabled(true);
	}
	
	
	public boolean isInit() {
		return this.isInit;
	}
	
	
	/**
	 * Getter for the button "next round"
	 * @return	The "next round" button
	 */
	public JButton getNextButton() {
		return this.buttons.get(1);
	}
	
	/**
	 * Getter for the button "Launch button"
	 * @return	The "launch game" button
	 */
	public JButton getNewGameBtn() {
		return this.buttons.get(0);
	}
	
	/**
	 * Getter for the size field
	 * @return The size field
	 */
	public JTextField getSizeField() {
		return this.gameData.get(0);
	}
	
	/**
	 * Getter for the players field
	 * @return	The layer's field
	 */
	public JTextField getPlayersField() {
		return this.gameData.get(1);
	}
	
	/**
	 * Clear a cell label on the grid
	 * @param pos	The cell's position
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
		Floor curr = (Floor)board.get(pos);
		
		if(curr.isFull()) {
			cellLabels.get(pos.getColumn(), pos.getRow()).setText(board.get(pos).toString());
			cellLabels.get(pos.getColumn(), pos.getRow()).setBackground(Color.blue);
		}
		
		if(board.getTreasure().isFound()) {
			Position treasurePos = board.getTreasure().getPosition();
			cellLabels.get(treasurePos.getColumn(), treasurePos.getRow()).setText(board.getTreasure().toString());
			cellLabels.get(treasurePos.getColumn(), treasurePos.getRow()).setBackground(Color.yellow);
		}
//		this.revalidate();
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
	public JPanel makeButtonArea(String title, String subTitle, int min, int max, int step, int size, int defaultValue ) {
		JPanel btn = new JPanel();
		btn.setBorder(new TitledBorder(title));
		btn.add(new JLabel(subTitle));
		
		
		
		JTextField sizeField = new JTextField(Integer.toString(defaultValue));
		this.gameData.add(sizeField);
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
