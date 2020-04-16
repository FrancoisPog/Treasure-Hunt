package treasure_hunt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;
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
		if(e.getSource() == frame.getButton("new")) {
			randomMap();
			return;
		}
		if(e.getSource() == frame.getButton("play_game") && frame.isInit()) {
			playAuto();
			return;
		}
		
		if(e.getSource() == frame.getButton("save") && frame.isInit()) {
			saveBoard();
			return;
		}
		
		if(e.getSource() == frame.getButton("open")) {
			openBoard();
			frame.setInit(true);
			return;
		}
		
		if(e.getSource() == frame.getButton("replay")) {
			replayBoard();
			frame.setInit(true);
		}
	}
	
	/**
	 * Get a new random map
	 */
	public void randomMap() {
		System.out.println("New random map");
		if(timer != null) {
			timer.stop();
			this.timer = null;
		}
		
		
		int size = Integer.parseInt(frame.getData("size").getText());
		int players = Integer.parseInt(frame.getData("players").getText());
		
		// If same size, just modify the matrix
		if(this.game != null && size == this.game.getBoard().size()) {
			System.out.println("(same size)");
			this.game.randomBoard(players);
			this.frame.initGrid(game);
			return;
		}
		
		// If different size, new game
		
		this.game = new Game(size, players);
		this.frame.initGrid(game);
	}
	
	/**
	 * Play game automatically
	 */
	public void playAuto() {
		System.out.println("Launch Auto Game");
		ActionListener action = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				executeRound();
				
			}
		};

		if(timer == null) {
			System.out.println("Timer set");
			int time = Integer.parseInt(frame.getData("timer").getText());
			timer = new Timer(time,action);
			timer.start();
		}

		frame.getButton("play_game").setEnabled(false);
		
	}
	
	/**
	 * Execute a game round
	 */
	public void executeRound() {
		System.out.println("Round execution");
		if(game.getBoard().getTreasure().isFound()) {
			timer.stop();
			this.timer = null;
			return;
		}
		
		for(Hunter h : game.getHunters()) {
			frame.clearFloor(h.getPosition()); // A optimiser
			Position curr = h.move();
			frame.updateFloor(curr, game.getBoard());
			
		}
		
		frame.updateTreasure(game.getBoard().getTreasure());
				
	}
	
	/**
	 * Replay the current map
	 */
	public void replayBoard() {
		if(this.timer != null) {
			this.timer.stop();
			this.timer = null;
		}
		
		
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
	}
	
	/**
	 * Save the current map
	 */
	public void saveBoard() {
		File file = FileManager.selectFile(this.frame,'s');
		if(file == null) {
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
	 * Open a map
	 */
	public void openBoard() {
		int players = Integer.parseInt(frame.getData("players").getText());
		File file = FileManager.selectFile(this.frame,'o');
		if(file == null) {
			return;
		}
		try {
			this.game = new Game(file,players);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(frame, "The chosen file is wrong, please try another one. ", "Something is wrong...", JOptionPane.ERROR_MESSAGE);
			return;
		}
		frame.setData("size", game.getBoard().size()+"");
		frame.initGrid(game);
	}

}
