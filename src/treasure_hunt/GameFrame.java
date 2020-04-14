package treasure_hunt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class GameFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Matrix<JLabel> cellLabels;
	private JButton next;
	private JButton launch;
	private boolean isInit;
	
	private JPanel gridPanel;
	
	public GameFrame() {
		super("Treasure Hunt");
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {}
		this.setSize(1100,1000);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setMinimumSize(new Dimension(800,200));
		
		this.isInit = false;
		
		cellLabels = null;
		Controller controller = new Controller(this);
		
		
		Container main = this.getContentPane();
		main.setLayout(new BorderLayout());
		
		JPanel buttonsPane = new JPanel();
		JButton launch = new JButton("Launch game");
		this.launch = launch;
		launch.addActionListener(controller);
		JButton	nextRoundButton = new JButton("Next round");
		this.next = nextRoundButton;
		nextRoundButton.addActionListener(controller);
		buttonsPane.add(nextRoundButton);
		buttonsPane.add(launch);
		
		main.add(buttonsPane,"North");
		
		this.gridPanel = new JPanel();
		
		
		main.add(gridPanel);
		
		this.revalidate();
	
	}
	
	
	public void initGrid(Game game) {
		int size = game.getBoard().size();
		cellLabels = new Matrix<JLabel>(size);
		
		gridPanel.removeAll();
		gridPanel.setLayout(new GridLayout(size,size));
		
		for(int i = 0 ; i < cellLabels.size(); ++i) {
			for(int j = 0 ; j < cellLabels.size() ; ++j) {
				JLabel curr_l = new JLabel("");
				Border border = BorderFactory.createLineBorder(Color.BLACK,1);
				curr_l.setBorder(border);
				curr_l.setHorizontalAlignment(JLabel.CENTER);
				cellLabels.set(j, i, curr_l);
				gridPanel.add(cellLabels.get(j, i));
				Cell curr = game.getBoard().get(j, i);
				String cellType = curr.getClass().getSimpleName();
				if(cellType.equals("Floor")) {
					cellLabels.get(j, i).setBackground(Color.GRAY);
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
		this.revalidate();
		this.isInit = true;
	}
	
	public boolean isInit() {
		return this.isInit;
	}
	
	
	
	public JButton getNextButton() {
		return this.next;
	}
	
	public JButton getLaunchButton() {
		return this.launch;
	}
	
	public void clearFloor(Position pos) {
		cellLabels.get(pos.getColumn(), pos.getRow()).setText("");
		cellLabels.get(pos.getColumn(), pos.getRow()).setBackground(Color.gray);
		this.revalidate();
	}
	
	public void updateFloor(Position pos, Board board) {
		Floor curr = (Floor)board.get(pos);
		
		if(curr.isFull()) {
			cellLabels.get(pos.getColumn(), pos.getRow()).setText(board.get(pos).toString());
			cellLabels.get(pos.getColumn(), pos.getRow()).setBackground(Color.blue);
		}
		
		if(board.getTreasure().isFound()) {
			Position treasurePos = board.getTreasure().getPosition();
			cellLabels.get(treasurePos.getColumn(), treasurePos.getRow()).setText(board.getTreasure().toString());
			cellLabels.get(treasurePos.getColumn(), treasurePos.getRow()).setBackground(Color.green);
		}
		this.revalidate();
	}

}
