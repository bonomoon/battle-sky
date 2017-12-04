package helicopterbattle.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.parser.Entity;

import helicopterbattle.game.PlayerHelicopter;
import helicopterbattle.game.PlayerHelicopterMP;
import helicopterbattle.gameframework.Framework;
import helicopterbattle.net.packets.Packet;
import helicopterbattle.net.packets.Packet.PacketTypes;
import helicopterbattle.net.packets.Packet00Login;
import javazoom.jl.player.Player;

public class GameServer extends Thread {

	private DatagramSocket socket;
	private Framework frame;
	private List<PlayerHelicopterMP> connectedPlayers = new ArrayList<PlayerHelicopterMP>();
	public GameServer(Framework frame) {
		try {
			this.socket = new DatagramSocket(1331);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(true) {
			byte[] data = new byte[1024];
			DatagramPacket packet= new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
//			String message = new String(packet.getData());
//			System.out.println("CLIENT [" + packet.getAddress().getHostAddress() + ":" + packet.getPort() + " ]> " + message);
//			if(message.trim().equalsIgnoreCase("ping")) {
//				System.out.println("Returning pong");
//				sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
//			}
		}
	}
	
	private void parsePacket(byte[] data, InetAddress address, int port) {
		String message = new String(data).trim();
		PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
		switch(type) {
			default:
			case INVALID:
				break;
			case LOGIN:
				Packet00Login packet = new Packet00Login(data);
				System.out.println("["+address.getHostAddress()+":"+port+"]"+packet.getUserName()+" has connected...");
				PlayerHelicopterMP player = null;
				if(address.getHostAddress().equalsIgnoreCase("localhost")) {
					player = new PlayerHelicopterMP(PlayerHelicopter.xCoordinate, PlayerHelicopter.yCoordinate, packet.getUserName(), address, port);	
				}
				if(player != null) {
					this.connectedPlayers.add(player);
				}

				break;
			case DISCONNECT:
				break;
		}
	}

	public void sendData(byte[] data, InetAddress ipAddress, int port) {
		DatagramPacket packet= new DatagramPacket(data, data.length, ipAddress, 1331);
		try {
			this.socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendDataToAll(byte[] data) {
		for(PlayerHelicopterMP p : connectedPlayers) {
			sendData(data, p.ipAddress, p.port);
		}
		
	}
}

