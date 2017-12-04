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

public class MainMenu {
    // Images for menu.
    private BufferedImage menuBackGround;
    private BufferedImage gameTitleImg;
    private BufferedImage menuBorderImg;
	private BufferedImage gameStartPressImg;
	private BufferedImage multiStartPressImg;
	private BufferedImage exitPressImg;
    
    ImageIcon gameStartNotPressImg = new ImageIcon(this.getClass().getResource("/helicopterbattle/resources/images/game_start_not_press.png"));
    private final JButton gameStartBtn = new JButton(gameStartNotPressImg);
    ImageIcon multiStartNotPressImg = new ImageIcon(this.getClass().getResource("/helicopterbattle/resources/images/multi_start_not_press.png"));
    private final JButton multiStartBtn = new JButton(multiStartNotPressImg);
    ImageIcon exitNotPressImg = new ImageIcon(this.getClass().getResource("/helicopterbattle/resources/images/exit_not_press.png"));
    private final JButton exitBtn = new JButton(exitNotPressImg);
    
	public MainMenu() {
        Thread threadForInitMainMenu= new Thread() {
            @Override
            public void run() {
                // Sets variables and objects for the game.
                Initialize();
                // Load game files (images, sounds, ...)
                LoadContent();
//                Framework.gameState = Framework.GameState.PLAYING;
            }
        };
        threadForInitMainMenu.start();
    }
	
    private void Initialize() {
       
    }
    
    /**
     * Load files (images).
     * This method is intended to load files for this class, files for the actual game can be loaded in Game.java.
     */
    private void LoadContent() {
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
        		Framework.gameState = GameState.SELECT_MENU;
        	}
        });

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
        	}
        });

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
    	
    }
}
