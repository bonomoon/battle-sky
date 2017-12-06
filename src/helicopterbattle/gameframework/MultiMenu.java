package helicopterbattle.gameframework;

import java.awt.Graphics2D;

import javax.swing.JOptionPane;

import helicopterbattle.net.GameClient;
import helicopterbattle.net.GameServer;
import helicopterbattle.net.packets.Packet00Login;



public class MultiMenu {
	private GameClient socketClient;
	private GameServer socketServer;
	
	public MultiMenu() {
		LoadContent();
	}
	
	private void LoadContent() {

	}
	public void updateMultiSession() {
		socketClient = new GameClient(this, "localhost");
		socketClient.start();
		Packet00Login loginPacket = new Packet00Login(
				JOptionPane.showInputDialog(this, "Please enter a username"));
		loginPacket.writeData(socketClient);
		Framework.gameState = Framework.GameState.SELECT_MENU;
	}
	public void startServer() {
		socketServer = new GameServer();
		socketServer.start();		
	}
	public void startClient() {
		socketClient = new GameClient(this, "localhost");
		socketClient.start();
		Packet00Login loginPacket = new Packet00Login(
				JOptionPane.showInputDialog(this, "Please enter a username"));
		loginPacket.writeData(socketClient);		
	}
	public void Draw(Graphics2D g2d) {
		
	}
}
