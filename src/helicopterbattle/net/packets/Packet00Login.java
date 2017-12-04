package helicopterbattle.net.packets;

import helicopterbattle.net.GameClient;
import helicopterbattle.net.GameServer;

public class Packet00Login extends Packet {

	private String username;
	
	public Packet00Login(byte[] data) {
		super(00);
		this.username = readData(data);
	}

	public Packet00Login(String username) {
		super(00);
		this.username = username;
	}
	@Override
	public void writeData(GameClient client) {
		client.sendData(getData());
	}

	@Override
	public void writeData(GameServer server) {
		server.sendDataToAll(getData());
	}

	@Override
	public byte[] getData() {
		return ("00" + this.username).getBytes();
	}
	
	public String getUserName() {
		return username;
	}

}