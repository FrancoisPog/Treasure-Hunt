package treasure_hunt;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	protected Map<String,JMenuItem> items;
	
	/**
	 * Default menu bar constructor
	 */
	public MenuBar() {
		this.items = new HashMap<String,JMenuItem>();
	}
	
	/**
	 * Getter for menu item
	 * @param name	The item's name
	 * @return		The menu item
	 */
	public JMenuItem getItem(String name) {
		return this.items.get(name);
	}
}
