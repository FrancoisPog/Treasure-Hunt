package treasure_hunt;

import java.io.File;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * <p><strong>Game</strong> is the class which manage a game party.<p>
 * <p>A Game is characterized by : </p>
 * <ul>
 * 		<li><dt>Severals hunters</dt>
 * 			<dd>- The hunters are stored in a TreeSet</dd></li> 
 * 		<li><dt>A cell matrix</dt>
 * 			<dd>- The matrix generated for this game</dd></li>
 * 		<li><dt>A playing mode</dt>
 * 			<dd>- Automatically of manually</dd></li>
 * </ul>
 * <p>The Game class is used to manage a game process without worrying about the map and players</p>
 * @see treasure_hunt.Hunter
 * @see treasure_hunt.Board
 * 
 * @author François Poguet
 * @author Enzo Costantini
 */
public class Game {
	private TreeSet<Hunter> hunters;
	private Board board;
	private boolean playAuto;
	
	
	/**
	 * Game constructor from file
	 * @param file
	 * @param nbPlayers
	 * @throws Exception If the file is wrong
	 */
	public Game(File file, int nbPlayers) throws Exception {
		this.hunters = new TreeSet<Hunter>();
		this.board = new Board(file,this.hunters, nbPlayers);
	}
	
	/**
	 * Default Game constructor
	 * @param size			The map size for this game party
	 * @param nbPlayers		The number of players for this game party
	 * @param wallDensity	The walls density indicator
	 * @param playAuto		The playing mode in console only (auto/manual)
	 */
	public Game(int size, int nbPlayers,int wallDensity, boolean playAuto) {
		this.hunters = new TreeSet<Hunter>();
		this.board = new Board(size,hunters,nbPlayers,wallDensity);
		this.playAuto = playAuto;
	}
	
	/**
	 * Constructor to play the map of the project example
	 * @param playAuto	The playing mode 
	 */
	public Game(boolean playAuto) {
		this.hunters = new TreeSet<Hunter>();
		this.playAuto = playAuto;
		this.board = new Board(hunters);
	}
	
	
	
	/**
	 * Create a new random board (same size)
	 * @param nbPlayers
	 */
	public void randomBoard(int nbPlayers,int wallDensity) {
		removeHunter();
		board.randomMap(hunters, nbPlayers, wallDensity);
	}
	
	
	/**
	 * Replay the same map (just replace randomly hunters and reset treasure)
	 * @param nbPlayers
	 */
	public void replayGame(int nbPlayers) {
		removeHunter();
		this.board.resetMap(hunters, nbPlayers);
		
	}
	
	/**
	 * Remove all hunters
	 */
	public void removeHunter() {
		for(Hunter h : hunters) {
			h.getCurrentFloor().leave();
		}
		hunters.clear();
	}
	
	/**
	 * Execute a move of each hunter (console)
	 */
	public void execute() {
		for(Hunter h : hunters) {
			System.out.println("Hunter "+h+" :\n\tPosition "+h.getPosition()+" - Direction : "+h.getDirection()+" "+Hunter.dirToArrow(h.getDirection()));
			h.move();
		}
	}
	
	/**
	 * Execute the entire game party in console
	 */
	public void play() throws InterruptedException {
		
		@SuppressWarnings("resource") // Because we don't want to close System.in
		Scanner sc = new Scanner(System.in);
		
		System.out.println(board);
			
		while(!this.board.getTreasure().isFound()) {
			
			
			if(this.playAuto) {
				Thread.sleep(200);
			}else {
				sc.nextLine();
			}
			
			this.execute();
			
			System.out.println(board);
			
		}
		
		
		
	}
	
	/**
	 * Display the console welcome menu before launch the game
	 * @return The game with specified settings
	 */
	public static Game welcome() {
		@SuppressWarnings("resource") // Because we don't want to close System.in
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Hello,\nWelcome to the best treasure hunt game!");
		
		System.out.println("Do you want to use a random map instead of the default map ? (y/n)");
		boolean defaultMap = sc.next().equalsIgnoreCase("n");
		
		int size = 32;
		int players = 3;
		int wallDensity = 2;
		boolean auto = true;
		
		if(!defaultMap) {
			
			
			System.out.println("Do you want to personalize the random settings ? (y/n)");
			boolean personalize = sc.next().equalsIgnoreCase("y");
			
			if(personalize) {
				do {
					System.out.println("Enter the size wanted [10;100] : ");
					size = sc.nextInt()+2;
				}while(size < 10 || size > 100);
				do {
					System.out.println("Enter the number of players [1;10] : ");
					players = sc.nextInt();
				}while(players < 1 || players > 10);
				do {
					System.out.println("Enter the wall density (0:none, 1:low, 2:medium, 3:high) : ");
					wallDensity = sc.nextInt();
				}while(wallDensity < 0 || wallDensity > 3);
			}
		}
		
		System.out.println("Mode auto or manual ? (a/m)");
		auto = sc.next().equalsIgnoreCase("a");
		if(!defaultMap) {
			return new Game(size,players,wallDensity,auto);
		}
		return new Game(auto);
	}
	
	/**
	 * Execute the game ending and display result
	 */
	public void result() {
		Hunter winner = this.board.getTreasure().getWinner();
		System.out.println("The player "+winner+" won !\nSee you soon ! (the graphic version will be better)");
	}
	
	/**
	 * Getter for the board
	 * @return The game board
	 */
	public Board getBoard() {
		return this.board;
	}
	
	/**
	 * Getter for the hunters set
	 * @return	The hunters TreeSet
	 */
	public TreeSet<Hunter> getHunters(){
		return this.hunters;
	}
	
}
