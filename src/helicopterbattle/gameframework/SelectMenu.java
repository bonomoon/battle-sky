package helicopterbattle.gameframework;

import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class SelectMenu {
	private BufferedImage selectMenuImg;
	private BufferedImage jetSelectImg;
	private BufferedImage heliSelectImg;
	private BufferedImage heli2SelectImg;
	private BufferedImage airBallonSelectImg;
	
	protected JButton jetSelectBtn;
	protected JButton heliSelectBtn;
	protected JButton heli2SelectBtn;
	protected JButton airBallonSelectBtn;
	private JButton btn;

	public SelectMenu() {
		Initialize();
		LoadContent();
		Framework.gameState = Framework.GameState.SELECT_MENU;
//		Thread threadForInitSelectMenu = new Thread() {
//			@Override
//			public void run() {
//				// Sets variables and objects for the game.
//				Initialize();
//				// Load game files (images, sounds, ...)
//				LoadContent();
//				Framework.gameState = Framework.GameState.SELECT_MENU;
//			}
//		};
//		threadForInitSelectMenu.start();
	}

	private void Initialize() {
		jetSelectBtn = new JButton();
		heliSelectBtn = new JButton();
		heli2SelectBtn = new JButton();
		airBallonSelectBtn = new JButton();
	}

	public void LoadContent() {
		try {
			URL selectMenuImgUrl = this.getClass().getResource("/helicopterbattle/resources/images/select_menu.png");
			selectMenuImg = ImageIO.read(selectMenuImgUrl);
			
			URL jetSelectImgImgUrl = this.getClass().getResource("/helicopterbattle/resources/images/jet_select.png");
			jetSelectImg = ImageIO.read(jetSelectImgImgUrl);
			jetSelectBtn.setIcon(new ImageIcon(jetSelectImg));
	
			URL heliSelectImgUrl = this.getClass().getResource("/helicopterbattle/resources/images/helicopter_select.png");
			heliSelectImg = ImageIO.read(heliSelectImgUrl);
			heliSelectBtn.setIcon(new ImageIcon(heliSelectImg));	
			
			URL heli2SelectImgUrl = this.getClass().getResource("/helicopterbattle/resources/images/helicopter2_select.png");
			heli2SelectImg = ImageIO.read(heli2SelectImgUrl);
			heli2SelectBtn.setIcon(new ImageIcon(heli2SelectImg));
			
			URL airBallonSelectImgUrl = this.getClass().getResource("/helicopterbattle/resources/images/air_ballon_select.png");
			airBallonSelectImg = ImageIO.read(airBallonSelectImgUrl);
			airBallonSelectBtn.setIcon(new ImageIcon(airBallonSelectImg));
		} catch (IOException ex) {
			Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void UpdateSelectMenu() {
		UpdateJetPlaneBtn();
		UpdateAirBalloonBtn();
		UpdateHelicopter1Btn();
		UpdateHelicopter2Btn();
	}

	private void UpdateJetPlaneBtn() {
		jetSelectBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				jetSelectBtn.setIcon(new ImageIcon(jetSelectImg));
			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				Game.flightState = Game.FlightState.JETPLANE;
				jetSelectBtn.setVisible(false);
				heliSelectBtn.setVisible(false);
				airBallonSelectBtn.setVisible(false);
				heli2SelectBtn.setVisible(false);
				Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING;
			}
		});
	}

	private void UpdateHelicopter1Btn() {
		heliSelectBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				heliSelectBtn.setIcon(new ImageIcon(heliSelectImg));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// heliSelectBtn.setIcon(new
				// ImageIcon(this.getClass().getResource("/helicopterbattle/resources/images/jet_select.png")));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				Game.flightState = Game.FlightState.HELICOPTER1;
				jetSelectBtn.setVisible(false);
				heliSelectBtn.setVisible(false);
				airBallonSelectBtn.setVisible(false);
				heli2SelectBtn.setVisible(false);
//				newGame();
			}
		});
	}

	private void UpdateAirBalloonBtn() {
		airBallonSelectBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				airBallonSelectBtn.setIcon(new ImageIcon(airBallonSelectImg));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// heliSelectBtn.setIcon(new
				// ImageIcon(this.getClass().getResource("/helicopterbattle/resources/images/jet_select.png")));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				Game.flightState = Game.FlightState.AIRBALLOON;
				jetSelectBtn.setVisible(false);
				heliSelectBtn.setVisible(false);
				airBallonSelectBtn.setVisible(false);
				heli2SelectBtn.setVisible(false);
				//framework.newGame();
			}
		});
	}

	private void UpdateHelicopter2Btn() {
		heli2SelectBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				heli2SelectBtn.setIcon(new ImageIcon(heli2SelectImg));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// heliSelectBtn.setIcon(new
				// ImageIcon(this.getClass().getResource("/helicopterbattle/resources/images/jet_select.png")));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				Game.flightState = Game.FlightState.HELICOPTER2;
				jetSelectBtn.setVisible(false);
				heliSelectBtn.setVisible(false);
				airBallonSelectBtn.setVisible(false);
				heli2SelectBtn.setVisible(false);
				//framework.newGame();
			}
		});
	}

	public void Draw(Graphics2D g2d) {
		g2d.drawImage(selectMenuImg, 0, 0, Framework.frameWidth, Framework.frameHeight, null);
	}

//	private void newGame() {
//		// We set gameTime to zero and lastTime to current time for later calculations.
//		gameTime = 0;
//		lastTime = System.nanoTime();
//
//		game = new Game();
//	}
	
	public JButton addMenuButton(JButton btn, int xCoordinate, int yCoordinate, int width, int height) {
		this.btn = btn;
		this.btn.setBounds(xCoordinate, yCoordinate, width, height);
		this.btn.setBorderPainted(false);
		this.btn.setContentAreaFilled(false);
		this.btn.setFocusPainted(false);
		return this.btn;
	}
}
