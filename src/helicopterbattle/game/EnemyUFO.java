package helicopterbattle.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import helicopterbattle.gameframework.Framework;

/**
 * Enemy helicopter.
 * 
 * @author www.gametutorial.net
 */

public class EnemyUFO {
    
    // For creating new enemies.
    private static final long timeBetweenNewEnemiesInit = Framework.secInNanosec * 7;
    public static long timeBetweenNewEnemies = timeBetweenNewEnemiesInit;
    public static long timeOfLastCreatedEnemy = 0;
    
    // Health of the helicopter.
    public int health;
    
    // Position of the helicopter on the screen.
    public int xCoordinate;
    public int yCoordinate;
    
    // Moving speed and direction.
    private static final double movingXspeedInit = -40;
    private static double movingXspeed = movingXspeedInit;
    // Images of enemy helicopter. Images are loaded and set in Game class in LoadContent() method.
    public static BufferedImage ufoBodyImg;

    /**
     * Initialize enemy helicopter.
     * 
     * @param xCoordinate Starting x coordinate of helicopter.
     * @param yCoordinate Starting y coordinate of helicopter.
     * @param helicopterBodyImg Image of helicopter body.
     * @param helicopterFrontPropellerAnimImg Image of front helicopter propeller.
     * @param helicopterRearPropellerAnimImg Image of rear helicopter propeller.
     */
    public EnemyUFO() {
        LoadContent();
    }
    private void LoadContent()
    {
        try 
        {
			// Load images for enemy helicopter
			URL ufoBodyImgUrl = this.getClass()
					.getResource("/helicopterbattle/resources/images/ufo.png");
			ufoBodyImg = ImageIO.read(ufoBodyImgUrl);
        } 
        catch (IOException ex) {
            Logger.getLogger(PlayerHelicopter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Initialize(int xCoordinate, int yCoordinate)
    {
        health = 50;
        
        // Sets enemy position.
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }
    
    /**
     * It sets speed and time between enemies to the initial properties.
     */
    public static void restartEnemy(){
        EnemyUFO.timeBetweenNewEnemies = timeBetweenNewEnemiesInit;
        EnemyUFO.timeOfLastCreatedEnemy = 0;
        EnemyUFO.movingXspeed = movingXspeedInit;
    }
    
    
    /**
     * It increase enemy speed and decrease time between new enemies.
     */
    public static void speedUp(){
        if(EnemyUFO.timeBetweenNewEnemies > Framework.secInNanosec)
            EnemyUFO.timeBetweenNewEnemies -= Framework.secInNanosec / 100;
        
        EnemyUFO.movingXspeed -= 0.25;
    }
    
    
    /**
     * Checks if the enemy is left the screen.
     * 
     * @return true if the enemy is left the screen, false otherwise.
     */
    public boolean isLeftScreen()
    {
        if(xCoordinate < 0 - ufoBodyImg.getWidth()) // When the entire helicopter is out of the screen.
            return true;
        else
            return false;
    }
    
        
    /**
     * Updates position of helicopter, animations.
     */
    public void Update()
    {
        // Move enemy on x coordinate.
        xCoordinate += movingXspeed;
    }
    
    
    /**
     * Draws helicopter to the screen.
     * 
     * @param g2d Graphics2D
     */
    public void Draw(Graphics2D g2d)
    { 
        g2d.drawImage(ufoBodyImg, xCoordinate, yCoordinate, null);
    }
    
}
