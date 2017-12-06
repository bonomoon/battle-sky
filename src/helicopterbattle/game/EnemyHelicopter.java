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

public class EnemyHelicopter {
    
    // For creating new enemies.
    private static final long timeBetweenNewEnemiesInit = Framework.secInNanosec * 2;
    public static long timeBetweenNewEnemies = timeBetweenNewEnemiesInit;
    public static long timeOfLastCreatedEnemy = 0;
    
    // Health of the helicopter.
    public int health;
    
    // Position of the helicopter on the screen.
    public int xCoordinate;
    public int yCoordinate;
    
    // Moving speed and direction.
    private static final double movingXspeedInit = (double) (Math.random() * (2 - (1) + 1)) + 1;
    private static double movingXspeed = movingXspeedInit;
    private static double movingYspeed;
    // Images of enemy helicopter. Images are loaded and set in Game class in LoadContent() method.
    public static BufferedImage helicopterBodyImg;
    public static BufferedImage helicopterFrontPropellerAnimImg;
    public static BufferedImage helicopterRearPropellerAnimImg;
    
    // Animation of the helicopter propeller.
    private Animation helicopterFrontPropellerAnim;
    private Animation helicopterRearPropellerAnim;
    // Offset for the propeler. We add offset to the position of the position of helicopter.
    private static int offsetXFrontPropeller = 4;
    private static int offsetYFrontPropeller = -7;
    private static int offsetXRearPropeller = 205;
    private static int offsetYRearPropeller = 6;


    /**
     * Initialize enemy helicopter.
     * 
     * @param xCoordinate Starting x coordinate of helicopter.
     * @param yCoordinate Starting y coordinate of helicopter.
     * @param helicopterBodyImg Image of helicopter body.
     * @param helicopterFrontPropellerAnimImg Image of front helicopter propeller.
     * @param helicopterRearPropellerAnimImg Image of rear helicopter propeller.
     */
    public EnemyHelicopter() {
        LoadContent();
    }
    private void LoadContent()
    {
        try 
        {
			// Load images for enemy helicopter
			URL helicopterBodyImgUrl = this.getClass()
					.getResource("/helicopterbattle/resources/images/2_helicopter_body.png");
			helicopterBodyImg = ImageIO.read(helicopterBodyImgUrl);

			URL helicopterFrontPropellerAnimImgUrl = this.getClass()
					.getResource("/helicopterbattle/resources/images/2_front_propeller_anim.png");
			helicopterFrontPropellerAnimImg = ImageIO.read(helicopterFrontPropellerAnimImgUrl);

			URL helicopterRearPropellerAnimImgUrl = this.getClass()
					.getResource("/helicopterbattle/resources/images/2_rear_propeller_anim.png");
			helicopterRearPropellerAnimImg = ImageIO.read(helicopterRearPropellerAnimImgUrl);
        } 
        catch (IOException ex) {
            Logger.getLogger(PlayerHelicopter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Now that we have images of propeller animation we initialize animation object.
        helicopterFrontPropellerAnim = new Animation(helicopterFrontPropellerAnimImg, 204, 34, 3, 20, true, xCoordinate + offsetXFrontPropeller, yCoordinate + offsetYFrontPropeller, 0);
        helicopterRearPropellerAnim = new Animation(helicopterRearPropellerAnimImg, 54, 54, 4, 20, true, xCoordinate + offsetXRearPropeller, yCoordinate + offsetYRearPropeller, 0);
    }
    
    public void Initialize(int xCoordinate, int yCoordinate)
    {
        health = 100;
        
        // Sets enemy position.
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        
        // Initialize animation object.
        helicopterFrontPropellerAnim = new Animation(helicopterFrontPropellerAnimImg, 158, 16, 3, 20, true, xCoordinate + offsetXFrontPropeller, yCoordinate + offsetYFrontPropeller, 0);
        helicopterRearPropellerAnim = new Animation(helicopterRearPropellerAnimImg, 47, 47, 10, 10, true, xCoordinate + offsetXRearPropeller, yCoordinate + offsetYRearPropeller, 0);
       
        // Moving speed and direction of enemy.
        EnemyHelicopter.movingXspeed = -4;
        EnemyHelicopter.movingYspeed = (int) (Math.random() * (2 - (-2) + 1)) - 2;
    }
    
    /**
     * It sets speed and time between enemies to the initial properties.
     */
    public static void restartEnemy(){
        EnemyHelicopter.timeBetweenNewEnemies = timeBetweenNewEnemiesInit;
        EnemyHelicopter.timeOfLastCreatedEnemy = 0;
        EnemyHelicopter.movingXspeed = movingXspeedInit;
    }
    
    
    /**
     * It increase enemy speed and decrease time between new enemies.
     */
    public static void speedUp(){
        if(EnemyHelicopter.timeBetweenNewEnemies > Framework.secInNanosec)
            EnemyHelicopter.timeBetweenNewEnemies -= Framework.secInNanosec / 100;
        
        EnemyHelicopter.movingXspeed -= 0.25;
    }
    
    
    /**
     * Checks if the enemy is left the screen.
     * 
     * @return true if the enemy is left the screen, false otherwise.
     */
    public boolean isLeftScreen()
    {
        if(xCoordinate < 0 - helicopterBodyImg.getWidth()) // When the entire helicopter is out of the screen.
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
	    yCoordinate += movingYspeed;
        // Moves helicoper propeler animations with helicopter.
        helicopterFrontPropellerAnim.changeCoordinates(xCoordinate + offsetXFrontPropeller, yCoordinate + offsetYFrontPropeller);
        helicopterRearPropellerAnim.changeCoordinates(xCoordinate + offsetXRearPropeller, yCoordinate + offsetYRearPropeller);
    }
    
    
    /**
     * Draws helicopter to the screen.
     * 
     * @param g2d Graphics2D
     */
    public void Draw(Graphics2D g2d)
    { 
        helicopterFrontPropellerAnim.Draw(g2d);
        g2d.drawImage(helicopterBodyImg, xCoordinate, yCoordinate, null);
        helicopterRearPropellerAnim.Draw(g2d);
    }
    
}
