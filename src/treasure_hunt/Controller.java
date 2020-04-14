package treasure_hunt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


public class Controller implements ActionListener{
	
	private GameFrame frame;
	private Game game;
	private Timer timer;
	
	
	public Controller(GameFrame frame) {
		this.frame = frame;
		this.game = null;
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
	
	public void launchGame() {
		if(timer != null) {
			timer.stop();
		}
		this.game = new Game(30, 2);
		frame.initGrid(game);
	}
	
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
	
	public void executeRound() {
		if(game.getBoard().getTreasure().isFound()) {
			timer.stop();
			return;
		}
		
		for(Hunter h : game.getHunters()) {
			System.out.println("yep");
			frame.clearFloor(h.getPosition()); // A optimiser
			Position curr = h.move();
			frame.updateFloor(curr, game.getBoard());
			
		}
				
	}

}
