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

import helicopterbattle.gameframework.Framework.GameState;

public class MainMenu{
	
	// Images for menu.
    private BufferedImage menuBackGround;
    private BufferedImage gameTitleImg;
    private BufferedImage menuBorderImg;
	private BufferedImage gameStartPressImg;
	private BufferedImage multiStartPressImg;
	private BufferedImage exitPressImg;
    private JButton btn;
    
    ImageIcon gameStartNotPressImg = new ImageIcon(this.getClass().getResource("/helicopterbattle/resources/images/game_start_not_press.png"));
    protected final JButton gameStartBtn = new JButton(gameStartNotPressImg);
    ImageIcon multiStartNotPressImg = new ImageIcon(this.getClass().getResource("/helicopterbattle/resources/images/multi_start_not_press.png"));
    protected final JButton multiStartBtn = new JButton(multiStartNotPressImg);
    ImageIcon exitNotPressImg = new ImageIcon(this.getClass().getResource("/helicopterbattle/resources/images/exit_not_press.png"));
    protected final JButton exitBtn = new JButton(exitNotPressImg);
    
	public MainMenu() {
    	Framework.gameState = Framework.GameState.STARTING;
    	
        Thread threadForInitMainMenu= new Thread() {
            @Override
            public void run() {
                // Sets variables and objects for the game.
                Initialize();
                // Load game files (images, sounds, ...)
                LoadContent();
                Framework.gameState = Framework.GameState.MAIN_MENU;
            }
        };
        threadForInitMainMenu.start();
    }

    private void Initialize() {

    }
    
    public void LoadContent() {
        try {
            URL menuBackGroundUrl = this.getClass().getResource("/helicopterbattle/resources/images/game_menu_backgroud.jpg");
            menuBackGround = ImageIO.read(menuBackGroundUrl);
            
            URL menuBorderImgUrl = this.getClass().getResource("/helicopterbattle/resources/images/menu_border.png");
            menuBorderImg = ImageIO.read(menuBorderImgUrl);
                 
            URL gameTitleImgUrl = this.getClass().getResource("/helicopterbattle/resources/images/helicopter_battle_title.png");
            gameTitleImg = ImageIO.read(gameTitleImgUrl);

            URL gameStartPressImgUrl = this.getClass().getResource("/helicopterbattle/resources/images/game_start_press.png");
            gameStartPressImg = ImageIO.read(gameStartPressImgUrl);   
            
			URL multiStartPressImgUrl = this.getClass().getResource("/helicopterbattle/resources/images/multi_start_press.png");
			multiStartPressImg = ImageIO.read(multiStartPressImgUrl);
			
			URL exitPressImgUrl = this.getClass().getResource("/helicopterbattle/resources/images/exit_press.png");
			exitPressImg = ImageIO.read(exitPressImgUrl);
        } catch (IOException ex) {
            Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void UpdateMainMenu() {
    	UpdateGameStartBtn();
    	UpdateMultiStartBtn();
    	UpdateExitBtn();
    }
    
    private void UpdateGameStartBtn() {
        gameStartBtn.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseExited(MouseEvent e) {
        		gameStartBtn.setIcon(new ImageIcon(this.getClass().getResource("/helicopterbattle/resources/images/game_start_not_press.png")));
        	}
        	@Override
        	public void mouseEntered(MouseEvent e) {
        		gameStartBtn.setIcon(new ImageIcon(gameStartPressImg));                    		
        	}
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		gameStartBtn.setVisible(false);
        		multiStartBtn.setVisible(false);
        		exitBtn.setVisible(false);
//        		Framework.gameState = GameState.SELECT_MENU;
        		return;
        	}
        });
    }
    private void UpdateMultiStartBtn() {
        multiStartBtn.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseExited(MouseEvent e) {
        		 multiStartBtn.setIcon(new ImageIcon(this.getClass().getResource("/helicopterbattle/resources/images/multi_start_not_press.png")));
        	}
        	@Override
        	public void mouseEntered(MouseEvent e) {
        		multiStartBtn.setIcon(new ImageIcon(multiStartPressImg));                    		
        	}
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		gameStartBtn.setVisible(false);
        		multiStartBtn.setVisible(false);
        		exitBtn.setVisible(false);
        		Framework.gameState = GameState.MULTI_PLAY;
        		return;
        	}
        });
    }
    private void UpdateExitBtn() {
        exitBtn.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseExited(MouseEvent e) {
        		 exitBtn.setIcon(new ImageIcon(this.getClass().getResource("/helicopterbattle/resources/images/exit_not_press.png")));
        	}
        	@Override
        	public void mouseEntered(MouseEvent e) {
        		exitBtn.setIcon(new ImageIcon(exitPressImg));                    		
        	}
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		System.exit(0);
        	}
        });
    }
    public void Draw(Graphics2D g2d) {
        drawMenuBackground(g2d);
    }
    
    private void drawMenuBackground(Graphics2D g2d){
        g2d.drawImage(menuBackGround,  0, 0, Framework.frameWidth, Framework.frameHeight, null);
        g2d.drawImage(menuBorderImg,  0, 0, Framework.frameWidth, Framework.frameHeight, null);
        g2d.drawImage(gameTitleImg, Framework.frameWidth/2 - gameTitleImg.getWidth()/2, Framework.frameHeight/4, null);
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
