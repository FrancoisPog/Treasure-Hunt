package treasure_hunt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.Timer;


public class Controller implements ActionListener{
	
	private GameFrame gameFrame;
	private EditionFrame editionFrame;
	private Game game;
	private Clock clock;
	
	/**
	 * Controller constructor<br>
	 * This constructor set the <code>actionListener</code> for the timer
	 * @param frame The frame to control
	 */
	public Controller(GameFrame frame) {
		this.editionFrame = null;
		this.gameFrame = frame;
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
		
		
		if(e.getSource() == gameFrame.getButton("new") || e.getSource() == gameFrame.getMenuItem("new")) {
			randomMap();
			return;
		}
		if(e.getSource() == gameFrame.getButton("play") || e.getSource() == gameFrame.getMenuItem("play")) {
			play();
			return;
		}
		
		if(e.getSource() == gameFrame.getButton("round") || e.getSource() == gameFrame.getMenuItem("round")) {
			executeRound();
			return;
		}
		
		if(e.getSource() == gameFrame.getButton("save") || e.getSource() == gameFrame.getMenuItem("save")) {
			saveBoard();
			return;
		}
		
		if(e.getSource() == gameFrame.getButton("open") || e.getSource() == gameFrame.getMenuItem("open") ) {
			openBoard();
			return;
		}
		
		if(e.getSource() == gameFrame.getButton("replay") || e.getSource() == gameFrame.getMenuItem("replay")) {
			replayBoard();
			return;
		}
		
		if(e.getSource() == gameFrame.getButton("stop") || e.getSource() == gameFrame.getMenuItem("stop")) {
			stop();
			return;
		}
		
		if(e.getSource() == gameFrame.getButton("switch")) {
			this.editionFrame = new EditionFrame(this);
			editionFrame.getCenterPanel().initGrid(game);
			return;
		}
		

		if(e.getSource() == gameFrame.getMenuItem("reset")){
			gameFrame.setSetting("size", 50);
			gameFrame.setSetting("timer", 100);
			gameFrame.setSetting("players", 3);
			gameFrame.setSetting("density",2);
			return;
		}
		
		if(this.editionFrame != null) {
			if(e.getSource() == editionFrame.getButtonPane().getButton("empty")) {
				editionGenerate();
				return;
			}
			if(e.getSource() == editionFrame.getButtonPane().getButton("play")) {
				boardFromEdition();
				return;
			}
		}
	}
	
	/**
	 * <p>Get a new random map</p>
	 * <p>If the user keep the same size, only the board is changed, otherwise a new game begin.</p>
	 */
	public void randomMap() {
		System.out.println("\n[Board]\trandom map");
		clock.stop();
		
		int mode = gameFrame.getSetting("density");
		System.out.println(mode);
		int size = gameFrame.getSetting("size")+2;
		int players = (gameFrame.getSetting("players"));
		
		
		// If same size, just modify the matrix
		if(this.game != null && size == this.game.getBoard().size()) {
			this.game.randomBoard(players,mode);
			this.gameFrame.getGamePane().initGrid(game);
			return;
		}
		
		// If different size, new game
		
		this.game = new Game(size, players,mode, false);
		this.gameFrame.getGamePane().initGrid(game);
		
	}
	
	/**
	 * <p>Launch the game play automatically.</p>
	 * <p>The game is stopped when the treasure is found, or if the user stop the game.</p>
	 */
	public void play() {
		int time = gameFrame.getSetting("timer");
		clock.start(time);
		System.out.println("[Game]\tlaunch");
		
		gameFrame.setEnable("play", false);
		gameFrame.setEnable("stop", true);
		gameFrame.setEnable("round", false);
		

		
	}
	
	/**
	 * <p>Stop the game when it's playing automatically.</p>
	 */
	public void stop() {
		clock.stop();
		
		gameFrame.setEnable("play", true);
		gameFrame.setEnable("stop", false);
		gameFrame.setEnable("round", true);
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
			gameFrame.setEnable("play", false);
			gameFrame.setEnable("stop", false);
			gameFrame.setEnable("round",false);
			return;
		}
		
		for(Hunter h : game.getHunters()) {
			
			Position oldPos = h.getPosition();
			Position curr = h.move();
			if(!h.getPosition().equals(oldPos) || game.getBoard().getTreasure().isFound()) {
				gameFrame.getGamePane().clearFloor(oldPos);
				gameFrame.getGamePane().updateFloor(curr, game.getBoard());
				
			}
			gameFrame.getGamePane().updateDataPane(this.game);
		}
		
		gameFrame.getGamePane().updateTreasure(game.getBoard().getTreasure());
				
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
			gameFrame.getGamePane().clearFloor(h.getCurrentFloor().getPosition());
		}
		
		int players = gameFrame.getSetting("players");
		this.game.replayGame(players);
		
		gameFrame.getGamePane().updateTreasure(this.game.getBoard().getTreasure());
		for(Hunter h : game.getHunters()) {
			gameFrame.getGamePane().updateFloor(h.getPosition(), game.getBoard());
		}
		
		gameFrame.getGamePane().initDataPane(this.game);

		gameFrame.setEnable("play", true);
		gameFrame.setEnable("stop", false);
		gameFrame.setEnable("round", true);
		System.out.println("[Board]\tready");
	}
	
	/**
	 * <p>Save the current map</p>
	 */
	public void saveBoard() {
		clock.stop();
		gameFrame.setEnable("play", true);
		gameFrame.setEnable("stop", false);
		gameFrame.setEnable("round", true);


		System.out.println("[Save]\topened");
		File file = FileManager.selectFile(this.gameFrame,'s');
		if(file == null) {
			System.out.println("\n[Open]\tcanceled");
			gameFrame.setEnable("play",true);
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
		if(gameFrame.getGamePane().isInit()) {
			gameFrame.setEnable("play", true);
			gameFrame.setEnable("stop", false);
			gameFrame.setEnable("round", true);
		}

		System.out.println("[Open]\topened");
		int players = gameFrame.getSetting("players");
		File file = FileManager.selectFile(this.gameFrame,'o');
		if(file == null) {
			System.out.println("[Open]\tcanceled");
			return 1;
		}
		try {
			this.game = new Game(file,players);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(gameFrame, "The chosen file is wrong, please try another one. ", "Something is wrong...", JOptionPane.ERROR_MESSAGE);
			return 1;
		}
		System.out.println("[Open]\tfile ok");
		gameFrame.setSetting("size", game.getBoard().size());
		gameFrame.getGamePane().initGrid(game);
		System.out.println("[Board]\tready");
		return 0;
	}
	
	
	public void editionGenerate() {
		int size = this.editionFrame.getButtonPane().getSettings("size");
		this.editionFrame.getCenterPanel().initGrid(size);
	}
	
	public void boardFromEdition() {
		System.out.println("edit");
		int players = (gameFrame.getSetting("players"));
		this.game = new Game(this.editionFrame.getCenterPanel().getMatrix(), players);
		this.gameFrame.getGamePane().initGrid(game);
		
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
