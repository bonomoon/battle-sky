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

	private Framework framework;

	private BufferedImage selectMenuImg;

	ImageIcon jetSelectImg = new ImageIcon(
			this.getClass().getResource("/helicopterbattle/resources/images/jet_select.png"));
	protected final JButton jetSelectBtn = new JButton(jetSelectImg);

	ImageIcon heliSelectImg = new ImageIcon(
			this.getClass().getResource("/helicopterbattle/resources/images/helicopter_select.png"));
	protected final JButton heliSelectBtn = new JButton(heliSelectImg);

	ImageIcon heli2SelectImg = new ImageIcon(
			this.getClass().getResource("/helicopterbattle/resources/images/helicopter2_select.png"));
	protected final JButton heli2SelectBtn = new JButton(heli2SelectImg);

	ImageIcon airBallonSelectImg = new ImageIcon(
			this.getClass().getResource("/helicopterbattle/resources/images/air_ballon_select.png"));
	protected final JButton airBallonSelectBtn = new JButton(airBallonSelectImg);

	private JButton btn;

	public SelectMenu() {
		Framework.gameState = Framework.GameState.STARTING;

		Thread threadForInitSelectMenu = new Thread() {
			@Override
			public void run() {
				// Sets variables and objects for the game.
				Initialize();
				// Load game files (images, sounds, ...)
				LoadContent();
				Framework.gameState = Framework.GameState.SELECT_MENU;
			}
		};
		threadForInitSelectMenu.start();
	}

	private void Initialize() {

	}

	public void LoadContent() {
		try {
			URL selectMenuImgUrl = this.getClass().getResource("/helicopterbattle/resources/images/select_menu.png");
			selectMenuImg = ImageIO.read(selectMenuImgUrl);
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
				jetSelectBtn.setIcon(new ImageIcon(
						this.getClass().getResource("/helicopterbattle/resources/images/jet_select.png")));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// jetSelectBtn.setIcon(new
				// ImageIcon(this.getClass().getResource("/helicopterbattle/resources/images/jet_select.png")));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				Game.flightState = Game.FlightState.JETPLANE;
				jetSelectBtn.setVisible(false);
				heliSelectBtn.setVisible(false);
				airBallonSelectBtn.setVisible(false);
				heli2SelectBtn.setVisible(false);
				framework.newGame();
			}
		});
	}

	private void UpdateHelicopter1Btn() {
		heliSelectBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				heliSelectBtn.setIcon(new ImageIcon(
						this.getClass().getResource("/helicopterbattle/resources/images/helicopter_select.png")));
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
				framework.newGame();
			}
		});
	}

	private void UpdateAirBalloonBtn() {
		airBallonSelectBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				airBallonSelectBtn.setIcon(new ImageIcon(
						this.getClass().getResource("/helicopterbattle/resources/images/air_ballon_select.png")));
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
				framework.newGame();
			}
		});
	}

	private void UpdateHelicopter2Btn() {
		heli2SelectBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				heli2SelectBtn.setIcon(new ImageIcon(
						this.getClass().getResource("/helicopterbattle/resources/images/helicopter2_select.png")));
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
				framework.newGame();
			}
		});
	}

	public void Draw(Graphics2D g2d) {
		g2d.drawImage(selectMenuImg, 0, 0, Framework.frameWidth, Framework.frameHeight, null);
	}

	public JButton addMenuButton(JButton btn, int xCoordinate, int yCoordinate, int width, int height) {
		this.btn = btn;
		this.btn.setBounds(xCoordinate, yCoordinate, width, height);
		this.btn.setBorderPainted(false);
		this.btn.setContentAreaFilled(false);
		this.btn.setFocusPainted(false);
		return this.btn;
	}
}
