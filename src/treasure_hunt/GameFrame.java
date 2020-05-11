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
	private EditPane editPane;
	private boolean isGameMode;
	
	
	/**
	 * Game frame constructor
	 * This constructor build the frame without the grid
	 */
	public GameFrame() {
		super("Treasure Hunt");
		this.setSize(1400,1000);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(1600,700));
		this.setLocationRelativeTo(null);
		
		Controller controller = new Controller(this);
		
		// Main container
		Container main = this.getContentPane();
		main.setLayout(new BorderLayout());
		
		// Buttons pane
		ButtonsPane buttonsPane = new ButtonsPane(controller);
		main.add(buttonsPane,"North");
		this.buttonsPane = buttonsPane;
		
//		// Game pane
		this.gamePane = new GamePane(this);
		main.add(gamePane);
		this.isGameMode = true;
		
		
		
		
	
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
	
	public EditPane getEditPane() {
		return this.editPane;
	}
	
	public boolean isGameMode() {
		return this.isGameMode;
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
	
	
	public void switchMode() {
		if(this.isGameMode) {
			this.editPane = new EditPane(this);
			this.getContentPane().remove(this.gamePane);
			this.getContentPane().add(editPane,"Center");
			this.isGameMode = false;
			setEnable("save", false);
			setEnable("open", false);
			setEnable("round", false);
			setEnable("stop", false);
			setEnable("play", false);
			setEnable("new", false);
			setEnable("replay", false);
		}else {
			this.gamePane = new GamePane(this);
			this.getContentPane().remove(this.editPane);
			this.getContentPane().add(gamePane,"Center");
			this.isGameMode = true;
			setEnable("open", true);
			setEnable("new", true);
		}
		this.revalidate();
	}
	
	
	
	
	
	
	
	

}
