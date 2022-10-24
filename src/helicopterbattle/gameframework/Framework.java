package helicopterbattle.gameframework;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import helicopterbattle.game.Bgm;

/**
 * Framework that controls the game (Game.java) that created it, update it and
 * draw it on the screen.
 * 
 * @author www.gametutorial.net
 */

public class Framework extends Canvas {
	private static final long serialVersionUID = 1L;

	private Bgm introBgm = new Bgm("999.mp3", true);
	/**
	 * Width of the frame.
	 */
	public static int frameWidth;
	/**
	 * Height of the frame.
	 */
	public static int frameHeight;

	/**
	 * Time of one second in nanoseconds. 1 second = 1 000 000 000 nanoseconds
	 */
	public static final long secInNanosec = 1000000000L;

	/**
	 * Time of one millisecond in nanoseconds. 1 millisecond = 1 000 000 nanoseconds
	 */
	public static final long milisecInNanosec = 1000000L;

	/**
	 * FPS - Frames per second How many times per second the game should update?
	 */
	private final int GAME_FPS = 60;
	/**
	 * Pause between updates. It is in nanoseconds.
	 */
	private final long GAME_UPDATE_PERIOD = secInNanosec / GAME_FPS;

	/**
	 * Possible states of the game
	 */
	public static enum GameState {
		VISUALIZING, GAME_CONTENT_LOADING, MAIN_MENU, SELECT_MENU, SELECT_MENU_LOADING, MULTI_MENU, OPTIONS, PLAYING, GAMEOVER, DESTROYED
	}
	protected static GameState gameState;
	/**
	 * Elapsed game time in nanoseconds.
	 */
	public static long gameTime;
	// It is used for calculating elapsed time.
	private long lastTime;

	// The actual game
	private Game game;
	private MainMenu mainMenu;
	private SelectMenu selectMenu;
	private MultiMenu multiMenu;

	private Font font = new Font("monospaced", Font.BOLD, 28);

	public Framework() {
		super();

		introBgm.start();
		gameState = GameState.VISUALIZING;

		// We start game in new thread.
		Thread gameThread = new Thread() {
			@Override
			public void run() {
				GameLoop();
			}
		};
		gameThread.start();
		
		Thread threadForInitContent = new Thread() {
			@Override
			public void run() {
				mainMenu = new MainMenu();
				selectMenu = new SelectMenu();
				multiMenu = new MultiMenu();
				
			}
		};
		threadForInitContent.start();
	}

	/**
	 * In specific intervals of time (GAME_UPDATE_PERIOD) the game/logic is updated
	 * and then the game is drawn on the screen.
	 * @throws InterruptedException 
	 */
//	public void Splash() throws InterruptedException{
//		JSplash sp = new JSplash(this.getClass().getResource("game_menu_background.jpg"), true, true, false, "V1", null, Color.BLUE, Color.BLACK);
//		sp.splashOn();
//		sp.setProgress(20, "init");
//		Thread.sleep(1000);
//		sp.setProgress(40, "Loading");
//		Thread.sleep(1000);
//		sp.setProgress(60, "Apprying Configs");
//		Thread.sleep(1000);
//		sp.setProgress(80, "Starting App");
//		Thread.sleep(1000);
//		sp.splashOff();
//		gameState = GameState.VISUALIZING;
//	}
	
	private void GameLoop() {
		// This two variables are used in VISUALIZING state of the game. We used them to
		// wait some time so that we get correct frame/window resolution.
		long visualizingTime = 0, lastVisualizingTime = System.nanoTime();

		// This variables are used for calculating the time that defines for how long we
		// should put threat to sleep to meet the GAME_FPS.
		long beginTime, timeTaken, timeLeft;

		while (true) {
			beginTime = System.nanoTime();

			switch (gameState) {
			case PLAYING:
				gameTime += System.nanoTime() - lastTime;
				game.UpdateGame(gameTime, mousePosition());
				lastTime = System.nanoTime();
				break;
			case GAMEOVER:
				if (JOptionPane.showConfirmDialog(this, " 랭킹을 등록하시겠습니까?? ") == 0) {
					setCursor(false);
					try {
						new RankView();
						Thread.sleep(100000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else
					restartGame();
				break;
			case MULTI_MENU:
				if (JOptionPane.showConfirmDialog(this, "Do you want to run the server") == 0) {
					multiMenu.startServer();
				}
				multiMenu.startClient();
				gameState = GameState.SELECT_MENU;
				break;
			case MAIN_MENU:
				mainMenu.UpdateMainMenu();
				break;
			case SELECT_MENU:
				selectMenu.UpdateSelectMenu();
				break;
			case OPTIONS:
				break;
			case GAME_CONTENT_LOADING:
				introBgm.close();
				newGame();
				break;
			case VISUALIZING:
				// On Ubuntu OS (when I tested on my old computer) this.getWidth() method
				// doesn't return the correct value immediately (eg. for frame that should be
				// 800px width, returns 0 than 790 and at last 798px).
				// So we wait one second for the window/frame to be set to its correct size.
				// Just in case we
				// also insert 'this.getWidth() > 1' condition in case when the window/frame
				// size wasn't set in time,
				// so that we although get approximately size.
				if (this.getWidth() > 1 && visualizingTime > secInNanosec) {
					frameWidth = this.getWidth();
					frameHeight = this.getHeight();
					gameState = GameState.MAIN_MENU;
				} else {
					visualizingTime += System.nanoTime() - lastVisualizingTime;
					lastVisualizingTime = System.nanoTime();
				}
				break;
			default:
				break;
			}

			// Repaint the screen.
			repaint();

			// Here we calculate the time that defines for how long we should put threat to
			// sleep to meet the GAME_FPS.
			timeTaken = System.nanoTime() - beginTime;
			timeLeft = (GAME_UPDATE_PERIOD - timeTaken) / milisecInNanosec; // In milliseconds
			// If the time is less than 10 milliseconds, then we will put thread to sleep
			// for 10 millisecond so that some other thread can do some work.
			if (timeLeft < 10)
				timeLeft = 10; // set a minimum
			try {
				// Provides the necessary delay and also yields control so that other thread can
				// do work.
				Thread.sleep(timeLeft);
			} catch (InterruptedException ex) {
			}
		}
	}

	/**
	 * Draw the game to the screen. It is called through repaint() method in
	 * GameLoop() method.
	 */
	@Override
	public void Draw(Graphics2D g2d) {
		switch (gameState) {
		case PLAYING:
			setCursor(true);
			game.Draw(g2d, mousePosition(), gameTime);
			break;
		case GAMEOVER:
			drawGameOver(g2d);
			break;
		case MULTI_MENU:
			mainMenu.Draw(g2d);
			break;
		case MAIN_MENU:
			mainMenu.Draw(g2d);
			add(mainMenu.getButton(mainMenu.gameStartBtn, Framework.frameWidth / 2 - 250,
					Framework.frameHeight / 2 + 100, 500, 100));
			add(mainMenu.getButton(mainMenu.multiStartBtn, Framework.frameWidth / 2 - 250,
					Framework.frameHeight / 2 + 200, 500, 100));
			add(mainMenu.getButton(mainMenu.exitBtn, Framework.frameWidth / 2 - 250,
					Framework.frameHeight / 2 + 300, 500, 100));
			break;
		case SELECT_MENU:
			selectMenu.Draw(g2d);
			add(selectMenu.addMenuButton(selectMenu.jetSelectBtn, frameWidth / 4 - 550, frameHeight / 2 - 300, 600, 800));
			add(mainMenu.getButton(selectMenu.airBallonSelectBtn, frameWidth / 4 - 50, frameHeight / 2 - 300, 600, 800));
			add(mainMenu.getButton(selectMenu.heliSelectBtn, frameWidth / 2 - 50, frameHeight / 2 - 300, 600, 800));
			add(mainMenu.getButton(selectMenu.heli2SelectBtn, frameWidth * 3 / 4 - 50, frameHeight / 2 - 300, 600, 800));
			break;
		case OPTIONS:
			// ...
			break;
		case GAME_CONTENT_LOADING:
			drawGameContentLoading(g2d);
			break;
		default:
			break;
		}
	}
	
	private void drawGameContentLoading(Graphics2D g2d) {
		g2d.setColor(Color.white);
		g2d.drawString("GAME is LOADING", frameWidth / 2 - 50, frameHeight / 2);		
	}
	private void drawGameOver(Graphics2D g2d) {
		g2d.setColor(Color.white);
		g2d.drawString("Press ENTER to restart or ESC to exit.", frameWidth / 2 - 113, frameHeight / 4 + 30);
		game.DrawStatistic(g2d, gameTime);
		g2d.setFont(font);
		g2d.drawString("GAME OVER", frameWidth / 2 - 90, frameHeight / 4);
	}

	/**
	 * Starts new game.
	 */
	private void newGame() {
		// We set gameTime to zero and lastTime to current time for later calculations.
		gameTime = 0;
		lastTime = System.nanoTime();

		game = new Game();
	}

	/**
	 * Restart game - reset game time and call RestartGame() method of game object
	 * so that reset some variables.
	 */
	private void restartGame() {
		// We set gameTime to zero and lastTime to current time for later calculations.
		gameTime = 0;
		lastTime = System.nanoTime();

		game.RestartGame();

		// We change game status so that the game can start.
		gameState = GameState.PLAYING;
	}

	/**
	 * Returns the position of the mouse pointer in game frame/window. If mouse
	 * position is null than this method return 0,0 coordinate.
	 * 
	 * @return Point of mouse coordinates.
	 */
	private Point mousePosition() {
		try {
			Point mp = this.getMousePosition();

			if (mp != null)
				return this.getMousePosition();
			else
				return new Point(0, 0);
		} catch (Exception e) {
			return new Point(0, 0);
		}
	}

	/**
	 * This method is called when keyboard key is released.
	 * 
	 * @param e
	 *            KeyEvent
	 */
	@Override
	public void keyReleasedFramework(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);

		switch (gameState) {
		case GAMEOVER:
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
				restartGame();
			break;
		default:
			break;
		}
	}
}