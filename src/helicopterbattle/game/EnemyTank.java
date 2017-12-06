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
 * Enemy Tank.
 * 
 * @author www.gametutorial.net
 */

public class EnemyTank {
    
    // For creating new enemies.
    private static final long timeBetweenNewEnemiesInit = Framework.secInNanosec * 13;
    public static long timeBetweenNewEnemies = timeBetweenNewEnemiesInit;
    public static long timeOfLastCreatedEnemy = 0;
    
    // Health of the Tank.
    public int health;
    
    // Position of the Tank on the screen.
    public int xCoordinate;
    public int yCoordinate;
    
    // Moving speed and direction.
    private static final double movingXspeedInit = -4;
    private static double movingXspeed = movingXspeedInit;
    
    // Images of enemy Tank. Images are loaded and set in Game class in LoadContent() method.
    public static BufferedImage tankBodyImg;

    private final int numberOfAmmoInit = 10;
    public int numberOfAmmo;
    
    public EnemyTank() {
    	LoadContent();
    }
    /**
     * Initialize enemy Tank.
     * 
     * @param xCoordinate Starting x coordinate of Tank.
     * @param yCoordinate Starting y coordinate of Tank
     * @param helicopterBodyImg Image of Tank body.
     */
    public void Initialize(int xCoordinate, int yCoordinate)
    {
        health = 200;
        
        // Sets enemy position.
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.numberOfAmmo = numberOfAmmoInit;
        // Moving speed and direction of enemy.
        EnemyTank.movingXspeed = -1;
    }
    
    private void LoadContent()
    {
        try {
			// Load images for enemy Tank
			URL tankImgUrl = this.getClass().getResource("/helicopterbattle/resources/images/tank.png");
			EnemyTank.tankBodyImg = ImageIO.read(tankImgUrl);
        } 
        catch (IOException ex) {
            Logger.getLogger(PlayerHelicopter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * It sets speed and time between enemies to the initial properties.
     */
    public static void restartEnemy(){
        EnemyTank.timeBetweenNewEnemies = timeBetweenNewEnemiesInit;
        EnemyTank.timeOfLastCreatedEnemy = 0;
        EnemyTank.movingXspeed = movingXspeedInit;
    }
    
    /**
     * It increase enemy speed and decrease time between new enemies.
     */
    public static void speedUp(){
        if(EnemyTank.timeBetweenNewEnemies > Framework.secInNanosec)
            EnemyTank.timeBetweenNewEnemies -= Framework.secInNanosec / 100;
        
        EnemyTank.movingXspeed -= 0.25;
    }

    public boolean isShootingTime(long gameTime)
    {
       if ( ((gameTime - Bullet.timeOfLastCreatedEnemyBullet) >= Bullet.timeBetweenNewBullets) && this.numberOfAmmo >= 0 ) // 미사일 개수가 0 이상일 경우 
          return true; // 지금은 무조건 true 리턴. 즉 업데이트 마다 탱크가 사격함.
       else
          return false; // 미사일 바닥나서 false 리턴
    }
    
    /**
     * Checks if the enemy is left the screen.
     * 
     * @return true if the enemy is left the screen, false otherwise.
     */
    public boolean isLeftScreen()
    {
        if(xCoordinate < 0 - tankBodyImg.getWidth()) // When the entire Tank is out of the screen.
            return true;
        else
            return false;
    }
    
        
    /**
     * Updates position of Tank.
     */
    public void Update()
    {
        // Move enemy on x coordinate.
        xCoordinate += movingXspeed;
    }
    
    
    /**
     * Draws Tank to the screen.
     * 
     * @param g2d Graphics2D
     */
    public void Draw(Graphics2D g2d)
    { 
        g2d.drawImage(tankBodyImg, xCoordinate, yCoordinate, null);
    }
    
}
