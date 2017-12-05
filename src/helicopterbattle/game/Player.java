package helicopterbattle.game;

import java.awt.Graphics2D;

import helicopterbattle.gameframework.Game;

public interface Player {

	/**
	 * Resets the player.
	 * 
	 * @param xCoordinate Starting x coordinate of helicopter.
	 * @param yCoordinate Starting y coordinate of helicopter.
	 */
	void Reset(int xCoordinate, int yCoordinate);

	/**
	 * Checks if player is shooting. It also checks if player can 
	 * shoot (time between bullets, does a player have any bullet left).
	 * 
	 * @param gameTime The current elapsed game time in nanoseconds.
	 * @return true if player is shooting.
	 */
	boolean isShooting(long gameTime);

	/**
	 * Checks if player is fired a rocket. It also checks if player can 
	 * fire a rocket (time between rockets, does a player have any rocket left).
	 * 
	 * @param gameTime The current elapsed game time in nanoseconds.
	 * @return true if player is fired a rocket.
	 */
	boolean isFiredRocket(long gameTime);
	
	//Checks if player moving helicopter and sets its moving speed if player is moving.
	void isMoving();
	
	//Updates position of helicopter, animations.
	void Update(); 

	boolean IsPropeller(Game.FlightState flightState);

	/**
	 * Draws helicopter to the screen.
	 * 
	 * @param g2d Graphics2D
	 */
	void Draw(Graphics2D g2d);

}