package treasure_hunt;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ButtonsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	protected Map<String,JButton> buttons;
	protected Map<String,JLabel> settings;
	
	public ButtonsPanel() {
		this.buttons = new HashMap<String, JButton>();
		this.settings = new HashMap<String, JLabel>();
	}
	
	/**
	 * Getter for a button
	 * @param name	The button's name
	 * @return	The button
	 */
	public JButton getButton(String name) {
		return buttons.get(name);
	}
	
	/**
	 * Getter for settings
	 * @param name	The setting'a name
	 * @return		The setting value
	 */
	public int getSettings(String name) {
		return Integer.parseInt(this.settings.get(name).getText());
	}
	
	/**
	 * Setter for settings
	 * @param name	The setting's name
	 * @param value	The new setting value
	 */
	public void setSettings(String name, int value) {
		this.settings.get(name).setText(""+value);
	}

}
