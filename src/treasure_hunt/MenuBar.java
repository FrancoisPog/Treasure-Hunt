package treasure_hunt;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * Class menubar
 * @author francois
 *
 */
public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;
	private Map<String,JMenuItem> menu;
	
	/**
	 * Constructor
	 * @param controller The controller
	 */
	public MenuBar(ActionListener controller) {
		this.menu = new HashMap<String,JMenuItem>();
		
		JMenu file = new JMenu( "Files" );
        file.setMnemonic( 'F' );

        JMenuItem saveMap = new JMenuItem( "Save map" );
        saveMap.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK) );
		saveMap.addActionListener( controller );
		saveMap.setEnabled(false);
        file.add(saveMap);
        menu.put("save",saveMap);
        
        JMenuItem openMap = new JMenuItem( "Open map" );
        openMap.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK) );
		openMap.addActionListener( controller );
		openMap.setEnabled(true);
        file.add(openMap);
        menu.put("open",openMap);
        
        JMenu game = new JMenu("Game");
        game.setMnemonic('G');

        JMenuItem randomMap = new JMenuItem("New random map");
        randomMap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.SHIFT_DOWN_MASK));
		randomMap.addActionListener(controller);
		randomMap.setEnabled(true);
        game.add(randomMap);
        menu.put("random",randomMap);
        
        JMenuItem replay = new JMenuItem("Replay map");
        replay.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.SHIFT_DOWN_MASK));
		replay.addActionListener(controller);
		replay.setEnabled(false);
        game.add(replay);
        menu.put("replay",replay);
        
        game.addSeparator();
        
        JMenuItem play = new JMenuItem("Play auto");
        play.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.SHIFT_DOWN_MASK));
		play.addActionListener(controller);
		play.setEnabled(false);
        game.add(play);
        menu.put("play",play);
        
        JMenuItem stop = new JMenuItem("Stop");
        stop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.SHIFT_DOWN_MASK));
		stop.addActionListener(controller);
		stop.setEnabled(false);
        game.add(stop);
        menu.put("stop",stop);
       
        
        JMenuItem round = new JMenuItem("Play round");
        round.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, KeyEvent.CTRL_DOWN_MASK));
		round.addActionListener(controller);
		round.setEnabled(false);
        game.add(round);
        menu.put("round",round);
        
        JMenu settings = new JMenu("Settings");
        settings.setMnemonic('S');
        
        JMenuItem reset = new JMenuItem("Reset settings");
        reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.SHIFT_DOWN_MASK));
        reset.addActionListener(controller);
        settings.add(reset);
        menu.put("reset",reset);
        
        JMenu help = new JMenu("Help");
        help.setMnemonic('H');
        
        JMenuItem manual = new JMenuItem("Manual");
        manual.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.CTRL_DOWN_MASK));
        manual.addActionListener(controller);
        help.add(manual);
        menu.put("manual",manual);
        
        JMenuItem join = new JMenuItem("Join us");
        join.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, KeyEvent.CTRL_DOWN_MASK));
        join.addActionListener(controller);
        help.add(join);
        menu.put("join",join);
        
        
        this.add(file);
        this.add(game);
        this.add(settings);
        this.add(help);
	}
	
	/**
	 * Getter for menu item
	 * @param name
	 * @return
	 */
	public JMenuItem getItem(String name) {
		return this.menu.get(name);
	}

}
