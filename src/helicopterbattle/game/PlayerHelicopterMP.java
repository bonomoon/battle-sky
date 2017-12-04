package helicopterbattle.game;

import java.net.InetAddress;

public class PlayerHelicopterMP extends PlayerHelicopter {
	public InetAddress ipAddress;
	public int port;
	
	public PlayerHelicopterMP(int xCoordinate, int yCoordinate, String username, InetAddress ipAddress, int port) {
		super( xCoordinate, yCoordinate, username);
		this.ipAddress = ipAddress;
		this.port = port;
	}
}