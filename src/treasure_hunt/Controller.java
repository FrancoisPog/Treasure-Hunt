package treasure_hunt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


public class Controller implements ActionListener{
	
	private GameFrame frame;
	private Game game;
	private Timer timer;
	
	/**
	 * Controller constructor
	 * @param frame The frame to control
	 */
	public Controller(GameFrame frame) {
		this.frame = frame;
		this.game = null;
		this.timer = null;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == frame.getLaunchButton()) {
			launchGame();
			return;
		}
		if(e.getSource() == frame.getNextButton() && frame.isInit()) {
			playAuto();
			return;
		}
		
	}
	
	/**
	 * Init a new game
	 */
	public void launchGame() {
		if(timer != null) {
			timer.stop();
		}
		
		int size = (int) frame.getSizeLabel().getValue();
		int players = (int) frame.getPlayersLabel().getValue();
		this.game = new Game(size, players);
		frame.initGrid(game);
	}
	
	/**
	 * Let the hunter play
	 */
	public void playAuto() {
		ActionListener action = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				executeRound();
				
			}
		};
		timer = new Timer(100,action);
		timer.start();
		
	}
	
	/**
	 * Execute a game round
	 */
	public void executeRound() {
		if(game.getBoard().getTreasure().isFound()) {
			timer.stop();
			return;
		}
		
		for(Hunter h : game.getHunters()) {
			frame.clearFloor(h.getPosition()); // A optimiser
			Position curr = h.move();
			frame.updateFloor(curr, game.getBoard());
			
		}
				
	}

}
