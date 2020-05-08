package treasure_hunt;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;

public class GameFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	
	private ButtonsPane buttonsPane;
	private MenuBar menuBar;
	private GamePane gamePane;
	
	
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
		
		Controller controller = new Controller(this);
		
		// Main container
		Container main = this.getContentPane();
		main.setLayout(new BorderLayout());
		
		// Buttons pane
		ButtonsPane buttonsPane = new ButtonsPane(controller);
		main.add(buttonsPane,"North");
		this.buttonsPane = buttonsPane;
		
		// Game pane
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
	
	
	
	
	
	
	
	
	
	
	

}
