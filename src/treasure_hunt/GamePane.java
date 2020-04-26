package treasure_hunt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.Border;

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
			data.setText(" "+h.toString()+" : "+h.getPosition()+" - "+dirToArrow(h.getDirection()));
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
			data.setText(" "+h.toString()+" : "+h.getPosition()+" - "+dirToArrow(h.getDirection()));
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
	
	/**
	 * Get the arrow from a direction
	 * @param dir	The direction
	 * @return		The arrow
	 */
	public static char dirToArrow(int dir) {
		char arrows[] = {'\u2192','\u2197','\u2191','\u2196','\u2190','\u2199','\u2193','\u2198'};
		return arrows[dir-1];
	}
	

}
