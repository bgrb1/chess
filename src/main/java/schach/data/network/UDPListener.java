package schach.data.network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.SecretKey;

import schach.data.Variables;
import schach.data.network.security.AES;
import schach.data.network.security.RSA;


public class UDPListener extends Thread {
	
	private final DatagramSocket socket;
	private final UDPClient udp;

	private ListeningMode mode = ListeningMode.OFF;
	
	public UDPListener(UDPClient udp, DatagramSocket socket) { 
		super();
		this.udp = udp;
		this.socket = socket;
	}
	
	
	@Override
	public void run() {
		while(true) {
			try {
				DatagramPacket packet = new DatagramPacket(new byte[1024*56], 1024*56);
				socket.receive(packet);
				if(packet.getAddress().equals(InetAddress.getLocalHost())) {
					continue;
				}
				int len = packet.getLength();
				if(len == 0 && mode == ListeningMode.INGAME) {
					if(udp.runningGame.getOpponentAddress().equals(packet.getAddress())) {
						udp.timeoutManager.packetRecieved();
						continue;
					}
				}
				byte [] payload = packet.getData();
				byte [] content = new byte[len-1];
				for(int i = 1; i < len; i++){
					content[i-1] = payload[i];
				}
				byte packetID = payload[0];
				System.out.println("Packet RECIVED | IPv4=" + packet.getAddress().toString() + " | Size=" + len + "Byte(s) | ID=" + packetID); 
				resolvePacket(packet.getAddress(), packetID, content);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void resolvePacket(InetAddress address, byte packetID, byte [] content) throws Exception { //0 wird zu error mit error codes
		if(mode != ListeningMode.OFF) {
			if(mode == ListeningMode.SCAN && packetID == 2) {
				udp.reportScanResult(address, content);
			} else if (mode == ListeningMode.WAIT_FOR_PLAYER && packetID == 1) {
				udp.respondToScan(address);
			} else if (mode == ListeningMode.WAIT_FOR_PLAYER && packetID == 3) {
				//Überprüfe passwort, Handshake einleiten
				resolveHandshakeInitiation(address, content);
			} else if (mode == ListeningMode.HANDSHAKE && packetID == 4) {
				//Überprüfung, aufbau der Verbindung
				resolveHandshakeAcknowledgement(address, content);
			} else if (mode == ListeningMode.INGAME && packetID == 5) {
				resolveMovePacket(address, content);
			} else if (mode == ListeningMode.INGAME && packetID == 6) {
				resolveSurrenderPacket(address, content);
			} else if(mode !=ListeningMode.WAIT_FOR_PLAYER && packetID == 3) {
				//Error Game no longer available
			} else if(packetID == 0) {
				//byte errorID = content[0];
				//Error-Handeling
			}
		}
	}
	
	private void resolveMovePacket(InetAddress address, byte [] content) throws Exception {
		if(address.equals(udp.runningGame.getOpponentAddress())) {
			byte [] decryptedText = AES.decrypt(content, udp.runningGame.getAESKey());
			String transmittedText = new String(decryptedText, "UTF-8");
			String regex = "^([0-9]{1,10})([QKRNPB][a-h][1-8][-x][a-h][1-8])$";
			Pattern pattern = Pattern.compile(regex);
			Matcher m = pattern.matcher(transmittedText);
			if(m.matches()) {
				String sequence = m.group(1);
				String chessNotation = m.group(2);
				if(Integer.parseInt(sequence) == udp.runningGame.getMoveNumber()+1) {
					udp.runningGame.increaseMoveNumber();
					if(udp.game.getNetworkHandler().handleNetworkInput(chessNotation)) {
						System.out.println("Move RECIEVED | Notation=" + chessNotation + " | Sequence=" + sequence);
					} else {
						System.out.println("FAILED Move RECIEVED | Notation=" + chessNotation + " | Sequence=" + sequence);
					}
				}
			}
		}
	}
	
	private void resolveSurrenderPacket(InetAddress address, byte [] content) throws Exception {
		if(address.equals(udp.runningGame.getOpponentAddress())) {
			byte [] arr = AES.decrypt(content, udp.runningGame.getAESKey());
			if(arr[0] == 1 && arr[1]  == 2 && arr[2] == 3 && arr[3] == 4) {
				udp.game.surrender(false);
			}
		}
	}
	
	private void resolveHandshakeInitiation(InetAddress address, byte [] content) throws Exception {
		try {
			if(!udp.ownGame.isPublic()) {
				int keyLength = ((Variables.RSA_PUBLIC_KEY_LENGTH)/16 + 1) * 16;
				byte [] encryptedPublicKey = new byte [keyLength];
				System.arraycopy(content, 0, encryptedPublicKey, 0, keyLength);
				byte [] checksum = new byte [Variables.HASH_LENGTH];
				System.arraycopy(content, keyLength, checksum, 0, Variables.HASH_LENGTH);
				byte [] publicKey = AES.decrypt(encryptedPublicKey, udp.ownGame.getPassword().getBytes());
				if(verifyChecksum(checksum, publicKey)) {
					udp.acceptHandshakeInitiation(address, publicKey);
				} else {
					socket.send(PacketBuilder.buildFailedHandshakePacket(address));
				}
			} else {
				byte [] publicKey = new byte [Variables.RSA_PUBLIC_KEY_LENGTH];
				System.arraycopy(content, 0, publicKey, 0, Variables.RSA_PUBLIC_KEY_LENGTH);
				udp.acceptHandshakeInitiation(address, publicKey);
			}
		} catch (Exception e) {
			e.printStackTrace(); 
			socket.send(PacketBuilder.buildFailedHandshakePacket(address));
		}
	}
	
	private void resolveHandshakeAcknowledgement(InetAddress address, byte [] content) {
		try {
			if(address.equals(udp.selectedGame.getAddress())) {
				int keyLength = Variables.RSA_KEY_SIZE/8;
				byte [] encryptedAESKey = new byte [keyLength];
				System.arraycopy(content, 0, encryptedAESKey, 0, keyLength);
				byte [] checksum = new byte [Variables.HASH_LENGTH];
				System.arraycopy(content, keyLength, checksum, 0, Variables.HASH_LENGTH);
				byte [] keyBytes = RSA.decrypt(encryptedAESKey, udp.RSA_key.getPrivate());
				SecretKey aesKey = AES.byteArrayToSecretKey(keyBytes);
				if(verifyChecksum(checksum, keyBytes)) {
					System.out.println("Handshake Acknowledgement ACCEPTED   AES-Key=" + UDPClient.byteToString(aesKey.getEncoded()));
					udp.startGame(address, aesKey, true);
				} else {
					udp.view.reportFailedJoining();
				}
			}
		} catch (Exception e) {
			udp.view.reportFailedJoining(); 
			e.printStackTrace();
		}
	}
	
	private boolean verifyChecksum(byte [] checksum, byte [] arr) throws Exception {
		byte [] testSum = MessageDigest.getInstance(Variables.CHECKSUM_ALGORITHM).digest(arr);
		for(int i = 0; i < Variables.HASH_LENGTH; i++) {
			if(checksum[i] != testSum[i]) {
				return false;
			}
		}
		return true;
	}
	
	public void setMode(ListeningMode mode) {
		this.mode = mode;
	}
}
