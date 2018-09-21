package schach.data.network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.SecretKey;

import schach.data.Variables;
import schach.data.network.security.AES;
import schach.data.network.security.RSA;
import schach.domain.game.Game;
import schach.domain.game.GameMode;
import schach.domain.game.Move;
import schach.ui.NetworkView;

public class UDPClient {
	
	private final InetAddress BROADCAST_ADDRESS;
	private final DatagramSocket socket;
	protected final NetworkView view;
	
	protected OwnOpenGame ownGame; 
	protected ScanResult selectedGame;
	protected NetworkGame runningGame;
	protected KeyPair RSA_key;
	protected TimeoutManager timeoutManager;
	
	protected Game game;
	public UDPListener listener; //TODO
	
	public UDPClient(DatagramSocket socket, NetworkView view) throws Exception {
			BROADCAST_ADDRESS = Variables.calculateBraodcastAddress();
			this.socket = socket;
			this.view = view;
			System.out.println("Your IP=" + InetAddress.getLocalHost().toString() + " | PORT=" + Variables.PORT + " | BROADCAST=" + BROADCAST_ADDRESS.toString());
	}
	
	private List<ScanResult> scanResultBuffer = new ArrayList<ScanResult>();
	public void searchForOpenGames(){
		scanResultBuffer.clear();
		System.out.println("Scanning for open games..."); 
		boolean success; 
		do {
			success = sendScanPacket();
		} while (!success);
		
		new Thread () {
			@Override 
			public void run() {
				try {
					sleep(Variables.WAIT_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				view.updateList(scanResultBuffer);
				System.out.println("Scan finished | results=" + scanResultBuffer.size()); 
			}
		}.start();
	}
	
	private boolean sendScanPacket() {
		try {
			DatagramPacket packet = PacketBuilder.buildScanPacket(BROADCAST_ADDRESS);
			socket.send(packet);
			System.out.println("Packet SEND | IPv4=" + BROADCAST_ADDRESS +  " | Size=" + packet.getLength() + "Byte(s) | ID=1"); 
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void joinGame(ScanResult sr, String password) {
		selectedGame = sr;
		try {
			RSA_key = RSA.generateKeyPair(); 
			DatagramPacket packet = PacketBuilder.buildHandshakeInitiationPacket(sr.getAddress(), sr.getUID(), password, RSA_key);
			socket.send(packet);
			System.out.println("Packet SEND | IPv4=" + sr.getAddress().toString() + " | Size=" + packet.getLength() + "Byte(s) | ID=3"); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void joinGame(ScanResult sr) {
		selectedGame = sr;
		try {
			RSA_key = RSA.generateKeyPair(); 
			DatagramPacket packet = PacketBuilder.buildHandshakeInitiationPacket(sr.getAddress(), sr.getUID(), RSA_key);
			socket.send(packet);
			System.out.println("Packet SEND | IPv4=" + sr.getAddress().toString() + " | Size=" + packet.getLength() + "Byte(s) | ID=3");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void reportScanResult(InetAddress address, byte [] payload) throws Exception {
		try {
			byte publicByte = payload [0];
			boolean isPublic;
			if(publicByte == 0) {
				isPublic = false;
			} else if (publicByte == 1) {
				isPublic = true;
			} else {
				throw new IllegalArgumentException();
			}
			byte [] uidBytes = Arrays.copyOfRange(payload, 1, Variables.UID_SIZE+1);
			byte [] nameBytes = Arrays.copyOfRange(payload, Variables.UID_SIZE+1, payload.length);
			String name = new String(nameBytes, "UTF-8");
			String uid = new String(uidBytes, "UTF-8");
			System.out.println("Game found | Name=\"" + name + "\" | public=" + isPublic); 
			scanResultBuffer.add(new ScanResult(name, uid, isPublic, address));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void respondToScan(InetAddress address) {
		try {
			String name = ownGame.getName();
			String uid = ownGame.getUID();
			byte publicByte = (byte) ((ownGame.isPublic() ? 1 : 0));
			DatagramPacket packet = PacketBuilder.buildScanResponsePacket(address, name, uid, publicByte);
			socket.send(packet);
			System.out.println("Packet SEND | IPv4=" + address.toString() + " | Size=" + packet.getLength() + "Byte(s) | ID=2"); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void acceptHandshakeInitiation(InetAddress address, byte [] publicKeyBytes) throws Exception {
		view.listener.setMode(ListeningMode.OFF);
		PublicKey publicKey = RSA.byteArrayToPublicKey(publicKeyBytes);
		SecretKey aesKey = AES.generateKey();
		System.out.println("Handshake Initiation ACCEPTED   AES-Key=" + byteToString(aesKey.getEncoded()));
		DatagramPacket packet = PacketBuilder.buildHandshakeAcknowledgementPacket(address, aesKey, publicKey);
		socket.send(packet);
		System.out.println("Packet SEND | IPv4=" + address.toString() + " | Size=" + packet.getLength() + "Byte(s) | ID=4"); 
		startGame(address, aesKey, false);
	}
	
	public void sendMove(Move move) {
		try { 
			String chessNotation = move.toString();
			runningGame.increaseMoveNumber();
			DatagramPacket packet = PacketBuilder.buildMovePacket(runningGame.getOpponentAddress(), runningGame.getAESKey(), chessNotation, runningGame.getMoveNumber());
			socket.send(packet);
			System.out.println("Move SEND | Notation=" + chessNotation + " | Sequence=" + runningGame.getMoveNumber());
			System.out.println("Packet SEND | IPv4=" + packet.getAddress().toString() + " | Size=" + packet.getLength() + "Byte(s) | ID=4"); 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void startGame(InetAddress address, SecretKey key, boolean start) throws Exception {
		runningGame = new NetworkGame(address, key); 
		view.dispose();
		if(start) {
			game = new Game(GameMode.MULTI_LAN_WHITE, this);
		} else {
			game = new Game(GameMode.MULTI_LAN_BLACK, this);
		}
		(timeoutManager = new TimeoutManager(this)).startThreads();
		view.listener.setMode(ListeningMode.INGAME);
	}
	
	public void surrender() {
		try {
			DatagramPacket packet = PacketBuilder.buildSurrenderPacket(runningGame.getOpponentAddress(), runningGame.getAESKey());
			socket.send(packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void sendNullPacket() {
		try {
			DatagramPacket packet = new DatagramPacket(new byte [] {}, 0, runningGame.getOpponentAddress(), Variables.PORT);
			socket.send(packet);
		} catch (Exception e) {
			
		}
	}
	
	public void setOwnGame(OwnOpenGame game) {
		this.ownGame = game;
	}
	
	//Aus dem Internet kopiert
	public static String byteToString(byte [] arr) throws Exception {
		char[] hexArray = "0123456789ABCDEF".toCharArray();
		char[] hexChars = new char[arr.length * 2];
	    for ( int j = 0; j < arr.length; j++ ) {
	        int v = arr[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}

}
