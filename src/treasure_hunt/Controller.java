package treasure_hunt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.Timer;


public class Controller implements ActionListener{
	
	private GameFrame frame;
	private Game game;
	private Clock clock;
	
	/**
	 * Controller constructor<br>
	 * This constructor set the <code>actionListener</code> for the timer
	 * @param frame The frame to control
	 */
	public Controller(GameFrame frame) {
		this.frame = frame;
		this.game = null;
		
		ActionListener action = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				executeRound();
				
			}
		};
		this.clock = new Clock(action);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == frame.getButton("new")) {
			randomMap();
			return;
		}
		if(e.getSource() == frame.getButton("play_game") && frame.isInit()) {
			play();
			return;
		}
		
		if(e.getSource() == frame.getButton("play_round") && frame.isInit()) {
			executeRound();
			return;
		}
		
		if(e.getSource() == frame.getButton("save") && frame.isInit()) {
			saveBoard();
			return;
		}
		
		if(e.getSource() == frame.getButton("open")) {
			openBoard();
			return;
		}
		
		if(e.getSource() == frame.getButton("replay")) {
			replayBoard();
			return;
		}
		
		if(e.getSource() == frame.getButton("stop")) {
			stop();
			return;
		}
	}
	
	/**
	 * <p>Get a new random map</p>
	 * <p>If the user keep the same size, only the board is changed, otherwise a new game begin.</p>
	 */
	public void randomMap() {
		System.out.println("\n[Board]\trandom map");
		clock.stop();
		
		int size = Integer.parseInt(frame.getData("size").getText());
		int players = Integer.parseInt(frame.getData("players").getText());
		
		// If same size, just modify the matrix
		if(this.game != null && size == this.game.getBoard().size()) {
			this.game.randomBoard(players);
			this.frame.initGrid(game);
			return;
		}
		
		// If different size, new game
		
		this.game = new Game(size, players);
		this.frame.initGrid(game);
	}
	
	/**
	 * <p>Launch the game play automatically.</p>
	 * <p>The game is stopped when the treasure is found, or if the user stop the game.</p>
	 */
	public void play() {
		int time = Integer.parseInt(frame.getData("timer").getText());
		clock.start(time);
		System.out.println("[Game]\tlaunch");
		

		frame.getButton("play_game").setEnabled(false);
		frame.getButton("stop").setEnabled(true);
		frame.getButton("play_round").setEnabled(false);
	}
	
	/**
	 * <p>Stop the game when it's playing automatically.</p>
	 */
	public void stop() {
		clock.stop();
		frame.getButton("play_game").setEnabled(true);
		frame.getButton("play_round").setEnabled(true);
		frame.getButton("stop").setEnabled(false);
	}
	
	/**
	 * <p>Execute a game round</p>
	 * <p>Each hunter move, and the board is updated.</p>
	 */
	public void executeRound() {
		System.out.print(".");
		if(game.getBoard().getTreasure().isFound()) {
			System.out.println("\n[Game]\t end");
			clock.stop();
			frame.getButton("stop").setEnabled(false);
			frame.getButton("play_game").setEnabled(false);
			frame.getButton("play_round").setEnabled(false);
			return;
		}
		
		for(Hunter h : game.getHunters()) {
			Position oldPos = h.getPosition();
			Position curr = h.move();
			if(!h.getPosition().equals(oldPos) || game.getBoard().getTreasure().isFound()) {
				frame.clearFloor(oldPos);
				frame.updateFloor(curr, game.getBoard());
			}
		}
		
		frame.updateTreasure(game.getBoard().getTreasure());
				
	}
	
	/**
	 * <p>Replay the current map.</P>
	 * <p>The board isn't modify, only hunters are replaced.<p>
	 */
	public void replayBoard() {
		clock.stop();
		
		System.out.println("[board]\treplay map");
		while(!this.game.getHunters().isEmpty()) {
			Hunter h = this.game.getHunters().pollFirst();
			h.getCurrentFloor().leave();
			frame.clearFloor(h.getCurrentFloor().getPosition());
		}
		
		int players = Integer.parseInt(frame.getData("players").getText());
		this.game.replayGame(players);
		
		frame.updateTreasure(this.game.getBoard().getTreasure());
		for(Hunter h : game.getHunters()) {
			frame.updateFloor(h.getPosition(), game.getBoard());
		}

		frame.getButton("play_game").setEnabled(true);
		frame.getButton("play_round").setEnabled(true);
		frame.getButton("stop").setEnabled(false);
		System.out.println("[Board]\tready");
	}
	
	/**
	 * <p>Save the current map</p>
	 */
	public void saveBoard() {
		clock.stop();
		frame.getButton("stop").setEnabled(false);
		frame.getButton("play_game").setEnabled(true);
		frame.getButton("play_round").setEnabled(true);
		System.out.println("[Save]\topened");
		File file = FileManager.selectFile(this.frame,'s');
		if(file == null) {
			System.out.println("\n[Open]\tcanceled");
			frame.getButton("play_game").setEnabled(true);
			return;
		}
		String filePath = file.getPath();
		int pointIndex = filePath.lastIndexOf('.');
		if(pointIndex == -1 || !filePath.substring(pointIndex+1).equals("pog")) {
			filePath += ".pog";
			file = new File(filePath);
		}
		
		FileManager.saveMap(game.getBoard(), file);
		System.out.println("[Save]\tmap saved");
		
	}
	
	/**
	 * <p>Open a map in a file.</p>
	 */
	public int openBoard() {
		clock.stop();
		if(frame.isInit()) {
			frame.getButton("stop").setEnabled(false);
			frame.getButton("play_game").setEnabled(true);
			frame.getButton("play_round").setEnabled(true);
		}
		System.out.println("[Open]\topened");
		int players = Integer.parseInt(frame.getData("players").getText());
		File file = FileManager.selectFile(this.frame,'o');
		if(file == null) {
			System.out.println("[Open]\tcanceled");
			return 1;
		}
		try {
			this.game = new Game(file,players);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(frame, "The chosen file is wrong, please try another one. ", "Something is wrong...", JOptionPane.ERROR_MESSAGE);
			return 1;
		}
		System.out.println("[Open]\tfile ok");
		frame.setData("size", game.getBoard().size()+"");
		frame.initGrid(game);
		System.out.println("[Board]\tready");
		return 0;
	}
	
	
	/**
	 * Inner class to manage the time
	 * @author François Poguet
	 *
	 */
	public class Clock{
		private Timer timer;
		private boolean isActive;
		
		public Clock(ActionListener action) {
			timer = new Timer(100, action);
			isActive = false;
		}
		
		public void start(int delay) {
			if(isActive) {
				System.out.println("[Timer]\talready started");
				return;
			}
			System.out.println("\n[Timer]\tstart");
			timer.setDelay(delay);
			timer.start();
			isActive = true;
		}
		
		public void stop() {
			if(!isActive) {
				System.out.println("[Timer]\talready stopped");
				return;
			}
			System.out.println("[Timer]\tstop\n");
			timer.stop();
			isActive = false;
		}
	}

}
