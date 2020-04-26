package treasure_hunt;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class ButtonsPane extends JPanel {

	private static final long serialVersionUID = 1L;
	private Map<String,JButton> buttons;
	private Map<String,JLabel> settings;
	
	private JComboBox<String> density;
	
	public ButtonsPane(ActionListener controller) {
		this.settings = new HashMap<String,JLabel>();
		this.buttons = new HashMap<String,JButton>();
		
		this.setLayout(new GridLayout(1,2));
		
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
		this.buttons.put("play",playGame);
		manageGamePane.add(playGame);
		
		JButton playRound = new JButton("Round");
		playRound.setEnabled(false);
		playRound.addActionListener(controller);
		this.buttons.put("round",playRound);
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
		
		JPanel densityPane = new JPanel();
		String[] densityModes = {"None","Low","Medium","High"};
		density = new JComboBox<String>(densityModes);
		density.setSelectedIndex(2);
		densityPane.setBorder(new TitledBorder("Wall density"));
		densityPane.add(density);
		settingsPane.add(densityPane);
		
		
		settingsPane.add(makeButtonArea("Size setting", "Size :","", 10, 120, 10, 2, 50,"size"));
		settingsPane.add(makeButtonArea("Players setting", "","hunter(s)", 1, 10, 1, 2, 3,"players"));
		settingsPane.add(makeButtonArea("Timer setting", "","ms", 100, 2000, 100, 3, 100,"timer"));
		
		this.add(gamePane);
		this.add(settingsPane);
		
		this.revalidate();
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
		this.settings.put(name,valuelabel);
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
	
	
	
	/**
	 * Getter for buttons
	 * @param name	The button's name
	 * @return		The buttons
	 */
	public JButton getButton(String name) {
		return this.buttons.get(name);
	}
	
	/**
	 * Getter for settings
	 * @param name	The setting'a name
	 * @return		The setting value
	 */
	public int getSettings(String name) {
		if(name.equals("density")) {
			return this.density.getSelectedIndex();
		}
		return Integer.parseInt(this.settings.get(name).getText());
	}
	
	/**
	 * Setter for settings
	 * @param name	The setting's name
	 * @param value	The new setting value
	 */
	public void setSettings(String name, int value) {
		if(name.equals("density")) {
			this.density.setSelectedIndex(value);
		}
		this.settings.get(name).setText(""+value);
	}

}
