package treasure_hunt;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

public class ViewComponents {
	
	public static JButton makeButton(String label, ActionListener listener, boolean enable, Map<String,JButton> buttons, String name) {
		JButton btn = new JButton(label);
		btn.setEnabled(enable);
		btn.addActionListener(listener);
		buttons.put(name, btn);
		return btn;
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
	public static JPanel makeButtonArea(String title, String prefix,String suffix, int min, int max, int step, int size, int defaultValue,Map<String,JLabel> settings ,String name ) {
		JPanel btn = new JPanel();
		btn.setBorder(new TitledBorder(title));
		btn.add(new JLabel(prefix));
		
		JLabel valuelabel = new JLabel(Integer.toString(defaultValue));
		settings.put(name,valuelabel);
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
	
	
	public static void makeMenuItem(String label,JMenu parent, ActionListener listener, boolean enable,Map<String,JMenuItem> items, String name, int keyEvent1, int keyEvent2) {
		JMenuItem item = new JMenuItem(label);
		item.addActionListener(listener);
		item.setEnabled(enable);
		item.setAccelerator(KeyStroke.getKeyStroke(keyEvent1, keyEvent2));
		items.put(name,item);
		parent.add(item);
		
		
		//return item;
	}
	
}
