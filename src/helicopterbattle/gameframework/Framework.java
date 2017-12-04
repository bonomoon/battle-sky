package helicopterbattle.gameframework;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import helicopterbattle.game.Bgm;
import helicopterbattle.net.GameClient;
import helicopterbattle.net.GameServer;
import helicopterbattle.net.packets.Packet00Login;

/**
 * Framework that controls the game (Game.java) that created it, update it and
 * draw it on the screen.
 * 
 * @author www.gametutorial.net
 */

public class Framework extends Canvas {
	private static final long serialVersionUID = 1L;

	Bgm introBgm = new Bgm("999.mp3", true);
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
		STARTING, VISUALIZING, GAME_CONTENT_LOADING, MAIN_MENU, SELECT_MENU, SELECT_MENU_LOADING, MULTI_PLAY, OPTIONS, PLAYING, GAMEOVER, DESTROYED
	}

	/**
	 * Current state of the game
	 */
	protected static GameState gameState;

	/**
	 * Elapsed game time in nanoseconds.
	 */
	private long gameTime;
	// It is used for calculating elapsed time.
	private long lastTime;

	// The actual game
	private Game game;
	private MainMenu mainMenu;
	private SelectMenu selectMenu;

	private Font font;

	private GameClient socketClient;
	private GameServer socketServer;

	// Images for menu.
	private BufferedImage menuBackGround;
	private BufferedImage gameTitleImg;
	private BufferedImage menuBorderImg;

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

	}

	/**
	 * Set variables and objects. This method is intended to set the variables and
	 * objects for this class, variables and objects for the actual game can be set
	 * in Game.java.
	 */
	private void Initialize() {
		font = new Font("monospaced", Font.BOLD, 28);

	}

	/**
	 * Load files (images). This method is intended to load files for this class,
	 * files for the actual game can be loaded in Game.java.
	 */
	private void LoadContent() {

	}

	/**
	 * In specific intervals of time (GAME_UPDATE_PERIOD) the game/logic is updated
	 * and then the game is drawn on the screen.
	 */
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

				break;
			case MULTI_PLAY:
				if (JOptionPane.showConfirmDialog(this, "Do you want to run the server") == 0) {
					socketServer = new GameServer(this);
					socketServer.start();
				}
				socketClient = new GameClient(this, "localhost");
				socketClient.start();
				Packet00Login loginPacket = new Packet00Login(
						JOptionPane.showInputDialog(this, "Please enter a username"));
				loginPacket.writeData(socketClient);
				gameState = GameState.SELECT_MENU;

				break;
			case MAIN_MENU:
				mainMenu.UpdateMainMenu();

				break;
			case SELECT_MENU:
				selectMenu.UpdateSelectMenu();
				
				break;
			case SELECT_MENU_LOADING:
				selectMenu = new SelectMenu();
				break;
			case OPTIONS:
				// ...
				break;
			case GAME_CONTENT_LOADING:
				introBgm.close();
				break;
			case STARTING:
				// Sets variables and objects.
				Initialize();
				// Load files - images, sounds, ...
				LoadContent();
				// When all things that are called above finished, we change game status to main
				// menu.
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
					mainMenu = new MainMenu();
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
			BufferedImage blankCursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
			java.awt.Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(blankCursorImg,
					new Point(0, 0), null);
			this.setCursor(blankCursor);

			game.Draw(g2d, mousePosition(), gameTime);
			break;
		case GAMEOVER:
			drawMenuBackground(g2d);
			g2d.setColor(Color.black);
			g2d.drawString("Press ENTER to restart or ESC to exit.", frameWidth / 2 - 113, frameHeight / 4 + 30);
			game.DrawStatistic(g2d, gameTime);
			g2d.setFont(font);
			g2d.drawString("GAME OVER", frameWidth / 2 - 90, frameHeight / 4);
			break;
		case MULTI_PLAY:
			drawMenuBackground(g2d);
			g2d.drawImage(gameTitleImg, frameWidth / 2 - gameTitleImg.getWidth() / 2, frameHeight / 4, null);
			break;
		case MAIN_MENU:
			mainMenu.Draw(g2d);
			add(mainMenu.addMenuButton(mainMenu.gameStartBtn, Framework.frameWidth / 2 - 250,
					Framework.frameHeight / 2 + 100, 500, 100));
			add(mainMenu.addMenuButton(mainMenu.multiStartBtn, Framework.frameWidth / 2 - 250,
					Framework.frameHeight / 2 + 200, 500, 100));
			add(mainMenu.addMenuButton(mainMenu.exitBtn, Framework.frameWidth / 2 - 250,
					Framework.frameHeight / 2 + 300, 500, 100));
			break;
		case SELECT_MENU:
			selectMenu.Draw(g2d);
			add(selectMenu.addMenuButton(selectMenu.jetSelectBtn, frameWidth / 4 - 550, frameHeight / 2 - 300, 600, 800));
			add(mainMenu.addMenuButton(selectMenu.airBallonSelectBtn, frameWidth / 4 - 50, frameHeight / 2 - 300, 600, 800));
			add(mainMenu.addMenuButton(selectMenu.heliSelectBtn, frameWidth / 2 - 50, frameHeight / 2 - 300, 600, 800));
			add(mainMenu.addMenuButton(selectMenu.heli2SelectBtn, frameWidth * 3 / 4 - 50, frameHeight / 2 - 300, 600, 800));
			break;
		case OPTIONS:
			// ...
			break;
		case GAME_CONTENT_LOADING:

			g2d.setColor(Color.white);
			g2d.drawString("GAME is LOADING", frameWidth / 2 - 50, frameHeight / 2);
			break;
		default:
			break;
		}
	}

	private void drawMenuBackground(Graphics2D g2d) {
		g2d.drawImage(menuBackGround, 0, 0, Framework.frameWidth, Framework.frameHeight, null);
		g2d.drawImage(menuBorderImg, 0, 0, Framework.frameWidth, Framework.frameHeight, null);
	}

	/**
	 * Starts new game.
	 */
	protected void newGame() {
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
		case MULTI_PLAY:
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
				gameState = GameState.SELECT_MENU;
			break;
		default:
			break;
		}
	}

	/**
	 * This method is called when mouse button is clicked.
	 * 
	 * @param e
	 *            MouseEvent
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		switch (gameState) {
		case SELECT_MENU:

			break;
		default:
			break;
		}
	}
}