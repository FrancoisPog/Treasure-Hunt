package treasure_hunt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.Timer;



/**
 * <p><strong>Controller</strong> is the class making the connection between the views and the model.<p>
 * <p>The controller use a <strong>Clock</strong> to play automatically</p>
 * 
 * @see treasure_hunt.Controller.Clock
 * @author François Poguet
 * @author Enzo Costantini
 *
 */
public class Controller implements ActionListener{
	
	private GameFrame gameFrame;
	private EditionFrame editionFrame;
	private Game game;
	private Clock clock;
	
	private char current_hunter;
	private boolean canAddHunter;
	
	/**
	 * Controller constructor<br>
	 * This constructor set the <code>actionListener</code> for the timer
	 * @param frame The game frame linked
	 */
	public Controller(GameFrame frame) {
		this.editionFrame = null;
		this.gameFrame = frame;
		this.game = null;
		this.current_hunter = 'A';
		this.canAddHunter = false;
		
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
		
		if(e.getSource() == gameFrame.getButtonPanel().getButton("new") || e.getSource() == gameFrame.getGameMenuBar().getItem("new")) {
			randomMap();
			return;
		}
		if(e.getSource() == gameFrame.getButtonPanel().getButton("play") || e.getSource() == gameFrame.getGameMenuBar().getItem("play")) {
			play();
			return;
		}
		
		if(e.getSource() == gameFrame.getButtonPanel().getButton("round") || e.getSource() == gameFrame.getGameMenuBar().getItem("round")) {
			executeRound();
			return;
		}
		
		if(e.getSource() == gameFrame.getButtonPanel().getButton("save") || e.getSource() == gameFrame.getGameMenuBar().getItem("save")) {
			saveBoard();
			return;
		}
		
		if(e.getSource() == gameFrame.getButtonPanel().getButton("open") || e.getSource() == gameFrame.getGameMenuBar().getItem("open") ) {
			openBoard();
			return;
		}
		
		if(e.getSource() == gameFrame.getButtonPanel().getButton("replay") || e.getSource() == gameFrame.getGameMenuBar().getItem("replay")) {
			replayBoard();
			return;
		}
		
		if(e.getSource() == gameFrame.getButtonPanel().getButton("stop") || e.getSource() == gameFrame.getGameMenuBar().getItem("stop")) {
			stop();
			return;
		}
		
		if(e.getSource() == gameFrame.getButtonPanel().getButton("editor") || e.getSource() == gameFrame.getGameMenuBar().getItem("editor")) {
			this.editionFrame = new EditionFrame(this);
			editionFrame.getEditionPanel().initGrid(32);
			if(this.game != null) {
				gameFrame.setEnable("send", true);
			}
			gameFrame.setEnable("editor", false);
			return;
		}
		
		if(e.getSource() == gameFrame.getButtonPanel().getButton("send") || e.getSource() == gameFrame.getGameMenuBar().getItem("send")) {
			if(this.editionFrame != null && this.editionFrame.isVisible()) {
				editionFrame.getEditionPanel().initGrid(game);
				editionFrame.getButtonPanel().setSettings("size", game.getBoard().size()-2);
			}
			return;
		}
		

		if(e.getSource() == gameFrame.getGameMenuBar().getItem("reset")){
			gameFrame.getButtonPanel().setSettings("size", 50);
			gameFrame.getButtonPanel().setSettings("timer", 100);
			gameFrame.getButtonPanel().setSettings("players", 3);
			gameFrame.getButtonPanel().setSettings("density",2);
			return;
		}
		
		if(this.editionFrame != null) {
			if(e.getSource() == editionFrame.getButtonPanel().getButton("empty")) {
				editionGenerate();
				return;
			}
			if(e.getSource() == editionFrame.getButtonPanel().getButton("play")) {
				if(!editionFrame.getEditionPanel().treasureIsInit()) {
					JOptionPane.showMessageDialog(editionFrame, "You must place a treasure","No treasure",JOptionPane.ERROR_MESSAGE);
					return;
				}
				boardFromEdition();
				return;
			}
		}
	}
	
	/**
	 * Get a new random map.<br>
	 * N.B.If the user keep the same size, only the board is changed, otherwise a new game begin.
	 */
	public void randomMap() {
		this.current_hunter = 'A';
		
		clock.stop();
		
		int mode = gameFrame.getButtonPanel().getSettings("density");
		
		int size = gameFrame.getButtonPanel().getSettings("size")+2;
		int players = gameFrame.getButtonPanel().getSettings("players");
		
		if(gameFrame.getButtonPanel().getSettings("randomHunters") == 0) {
			players = 0;
			this.canAddHunter = true;
			
		}else {
			this.canAddHunter = false;
		}
		
		
		// If same size, just modify the matrix
		if(this.game != null && size == this.game.getBoard().size()) {
			this.game.randomBoard(players,mode);
			this.gameFrame.getGamePanel().initGrid(game);
			if(this.canAddHunter) {
				gameFrame.getGamePanel().addMessage("<html>Click on a cell to add hunter.</html>");
			}
			return;
		}
		
		// If different size, new game
		this.game = new Game(size, players,mode);
		this.gameFrame.getGamePanel().initGrid(game);
		if(this.editionFrame != null && this.editionFrame.isVisible()) {
			gameFrame.setEnable("send", true);
		}
		
		if(this.canAddHunter) {
			gameFrame.getGamePanel().addMessage("<html>Click on a cell to add hunter.</html>");
		}
		
		
	}
	
	/**
	 * Launch the game play automatically.<br>
	 * The game is stopped when the treasure is found, or if the user stop the game.
	 */
	public void play() {
		if(game.getHunters().size() == 0) {
			JOptionPane.showMessageDialog(gameFrame, "You have to place at least one hunter before playing.");
			return;
		}
		int time = gameFrame.getButtonPanel().getSettings("timer");
		clock.start(time);
		
		
		gameFrame.setEnable("play", false);
		gameFrame.setEnable("stop", true);
		gameFrame.setEnable("round", false);
		
		this.canAddHunter = false;
		
	}
	
	/**
	 * Stop the game when it's playing automatically.
	 */
	public void stop() {
		clock.stop();
		
		gameFrame.setEnable("play", true);
		gameFrame.setEnable("stop", false);
		gameFrame.setEnable("round", true);
	}
	
	/**
	 * Execute a game round.<br>
	 * Each hunter move, and the board is updated.
	 */
	public void executeRound() {
		if(game.getHunters().size() == 0) {
			JOptionPane.showMessageDialog(gameFrame, "You have to place at least one hunter before playing.");
			return;
		}
		
		if(game.getBoard().getTreasure().isFound()) {
			
			clock.stop();
			gameFrame.getGamePanel().addMessage("<html>The winner is "+game.getBoard().getTreasure().getWinner()+" !</html>");
			gameFrame.setEnable("play", false);
			gameFrame.setEnable("stop", false);
			gameFrame.setEnable("round",false);
			return;
		}
		
		for(Hunter h : game.getHunters()) {
			
			Position oldPos = h.getPosition();
			Position curr = h.move();
			
			if(!h.getPosition().equals(oldPos) || game.getBoard().getTreasure().isFound()) {
				gameFrame.getGamePanel().clearFloor(oldPos);
				gameFrame.getGamePanel().updateFloor(curr, game.getBoard());
				
			}
			gameFrame.getGamePanel().updateDataPane(this.game);
		}
		
		gameFrame.getGamePanel().updateTreasure(game.getBoard().getTreasure());
		this.canAddHunter = false;
	}
	
	/**
	 * Replay the current map.<br>
	 * The board isn't modify, only hunters are replaced.
	 */
	public void replayBoard() {
		clock.stop();
		this.current_hunter = 'A';
		
		
		while(!this.game.getHunters().isEmpty()) {
			Hunter h = this.game.getHunters().pollFirst();
			h.getCurrentFloor().leave();
			gameFrame.getGamePanel().clearFloor(h.getCurrentFloor().getPosition());
		}
		
		int players = gameFrame.getButtonPanel().getSettings("players");
		if(gameFrame.getButtonPanel().getSettings("randomHunters") == 0) {
			players = 0;
			this.canAddHunter = true;
		}else {
			this.canAddHunter = false;
		}
		this.game.replayGame(players);
		
		gameFrame.getGamePanel().updateTreasure(this.game.getBoard().getTreasure());
		for(Hunter h : game.getHunters()) {
			gameFrame.getGamePanel().updateFloor(h.getPosition(), game.getBoard());
		}
		
		gameFrame.getGamePanel().initDataPanel(this.game);
		
		if(this.canAddHunter) {
			gameFrame.getGamePanel().addMessage("<html>Click on a cell to add hunter.</html>");
		}

		gameFrame.setEnable("play", true);
		gameFrame.setEnable("stop", false);
		gameFrame.setEnable("round", true);
		
		
	}
	
	/**
	 * Save the current map
	 */
	public void saveBoard() {
		clock.stop();
		gameFrame.setEnable("play", true);
		gameFrame.setEnable("stop", false);
		gameFrame.setEnable("round", true);


		
		File file = FileManager.selectFile(this.gameFrame,'s');
		if(file == null) {
			
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
		
		
	}
	
	/**
	 * Open a map in a file.
	 */
	public void openBoard() {
		clock.stop();
		if(gameFrame.getGamePanel().isInit()) {
			gameFrame.setEnable("play", true);
			gameFrame.setEnable("stop", false);
			gameFrame.setEnable("round", true);
		}

		
		int players = gameFrame.getButtonPanel().getSettings("players");
		if(gameFrame.getButtonPanel().getSettings("randomHunters") == 0) {
			players = 0;
			this.canAddHunter = true;
		}else {
			this.canAddHunter = false;
		}
		File file = FileManager.selectFile(this.gameFrame,'o');
		if(file == null) {
			
			return ;
		}
		try {
			this.game = new Game(file,players);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(gameFrame, "The chosen file is wrong, please try another one. ", "Something is wrong...", JOptionPane.ERROR_MESSAGE);
			return ;
		}
		
		gameFrame.getButtonPanel().setSettings("size", game.getBoard().size()-2);
		gameFrame.getGamePanel().initGrid(game);
		if(this.editionFrame != null && this.editionFrame.isVisible()) {
			gameFrame.setEnable("send", true);
		}
		if(this.canAddHunter) {
			gameFrame.getGamePanel().addMessage("<html>Click on a cell to add hunter.</html>");
		}
		
		return;
	}
	
	/**
	 * Generate a new empty map on the edition frame
	 */
	public void editionGenerate() {
		int conf = JOptionPane.showConfirmDialog(editionFrame, "Generate a new empty map will overwrite the current map on editor.", "Are you sure ?",JOptionPane.WARNING_MESSAGE);
		if(conf != 0) {
			return;
		}
		int size = this.editionFrame.getButtonPanel().getSettings("size")+2;
		this.editionFrame.getEditionPanel().initGrid(size);
	}
	
	/**
	 * Generate the board from edition in the game frame
	 */
	public void boardFromEdition() {
		
		int players = (gameFrame.getButtonPanel().getSettings("players"));
		if(gameFrame.getButtonPanel().getSettings("randomHunters") == 0) {
			players = 0;
			this.canAddHunter = true;
		}else {
			this.canAddHunter = false;
		}
		this.current_hunter = 'A';
		this.game = new Game(this.editionFrame.getEditionPanel().getMatrix(), players);
		this.gameFrame.getGamePanel().initGrid(game);
		this.gameFrame.getButtonPanel().setSettings("size", game.getBoard().size()-2);
		this.gameFrame.setEnable("send", true);
		if(this.canAddHunter) {
			gameFrame.getGamePanel().addMessage("<html>Click on a cell to add hunter.</html>");
		}
		
	}
	
	/**
	 * Action executed when the edition frame is closing
	 */
	public void closingEditor() {
		gameFrame.setEnable("editor", true);
		gameFrame.setEnable("send", false);
	}
	
	/**
	 * Add a hunter on the game board by clicking (after board generation)
	 * @param col	The column of the position	
	 * @param row	The row of the position
	 */
	public void addHunter(int col, int row) {
		if(!this.canAddHunter) {
			return;
		}
		Floor_c floor = (Floor_c) this.game.getBoard().get(col, row);
		if(floor.isFull()) {
			game.getHunters().remove(new Hunter(floor.toString().charAt(1), null));
			floor.leave();
			gameFrame.getGamePanel().updateFloor(floor.getPosition(), this.game.getBoard());
			gameFrame.getGamePanel().initDataPanel(game);
			return;
		}
		if(game.getHunters().size() == 10) {
			JOptionPane.showMessageDialog(gameFrame, "The number maximum of hunter is 10.");
			return;
		}
		Hunter h = new Hunter(current_hunter, floor);
		floor.come(h);
		this.game.getHunters().add(h);
		
		gameFrame.getGamePanel().updateFloor(floor.getPosition(), this.game.getBoard());
		this.current_hunter++;
		gameFrame.getGamePanel().initDataPanel(game);
	}
	
	
	/**
	 * <p>The class <strong>Clock</strong> is used to play automatically.<p>
	 * @author François Poguet
	 * @author Enzo Costantini
	 *
	 */
	public class Clock{
		private Timer timer;
		private boolean isActive;
		
		/**
		 * Default clock constructor
		 * @param action	The action to execute
		 */
		public Clock(ActionListener action) {
			timer = new Timer(100, action);
			isActive = false;
		}
		
		/**
		 * Start the clock
		 * @param delay	The timer delay
		 */
		public void start(int delay) {
			if(isActive) {
				
				return;
			}
			
			timer.setDelay(delay);
			timer.start();
			isActive = true;
		}
		
		/**
		 * Stop the timer
		 */
		public void stop() {
			if(!isActive) {
				
				return;
			}
			
			timer.stop();
			isActive = false;
		}
	}


	
	
	

}
