package schach.data.network;

import java.net.InetAddress;

import javax.crypto.SecretKey;

public class NetworkGame {
	
	
	private int moveNumber = 0;
	private final InetAddress opponent;
	private final SecretKey key;
	
	public NetworkGame(InetAddress opponent, SecretKey key) {
		this.opponent = opponent;
		this.key = key;
	}
	
	
	
	public int getMoveNumber() {
		return moveNumber; //TODO
	}
	
	public void increaseMoveNumber() {
		moveNumber++;
	}
	
	public SecretKey getAESKey() {
		return key;
	}
	
	public InetAddress getOpponentAddress() {
		return opponent;
	}

	

}
