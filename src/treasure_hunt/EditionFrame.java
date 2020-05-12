package treasure_hunt;

import java.awt.Dimension;

import javax.swing.JFrame;

public class EditionFrame extends JFrame {
	private GameFrame gameFrame;
		
	public EditionFrame(GameFrame parent) {
		super("Treasure Hunt - Edition");
		this.setSize(1400,1000);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setMinimumSize(new Dimension(1600,700));
		this.setLocationRelativeTo(null);
		
		this.gameFrame = parent;
	}
}
