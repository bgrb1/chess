package schach.data.network;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import schach.data.Variables;
import schach.data.network.security.AES;
import schach.data.network.security.RSA;

class PacketBuilder {
	
	static DatagramPacket buildScanPacket(InetAddress BROADCAST_ADDRESS) throws Exception {
		byte [] payload =  new byte[] {1};
		return new DatagramPacket(payload, payload.length, BROADCAST_ADDRESS, Variables.PORT);
	}
	
	static DatagramPacket buildScanResponsePacket(InetAddress address, String name, String uid, byte publicByte) throws Exception {
		byte [] nameBytes = name.getBytes();
		byte [] uidBytes = uid.getBytes(); 
		int length = 2 + uidBytes.length + nameBytes.length;
		byte [] payload = new byte[length];
		payload[0] = 2;
		payload[1] = publicByte;
		System.arraycopy(uidBytes, 0, payload, 2, uidBytes.length);
		System.arraycopy(nameBytes, 0, payload, uidBytes.length+2, nameBytes.length);
		return new DatagramPacket(payload, length, address, Variables.PORT);
	}
	
	static DatagramPacket buildHandshakeInitiationPacket(InetAddress address, String uid, String password, KeyPair key) throws Exception {
		/*byte[] versionBytes = new byte[4];
		ByteBuffer.wrap(versionBytes).putInt(Variables.PROTOCOL_VERSION);*/
		//Einfügen
		byte [] keyBytes = AES.encrypt(key.getPublic().getEncoded(), password.getBytes());
		byte [] checksum = MessageDigest.getInstance(Variables.CHECKSUM_ALGORITHM).digest(key.getPublic().getEncoded());
		int length = 1 + keyBytes.length + checksum.length;
		byte [] payload = new byte [length];
		payload[0] = 3;
		System.arraycopy(keyBytes, 0, payload, 1, keyBytes.length);
		System.arraycopy(checksum, 0, payload, 1+keyBytes.length, checksum.length);
		return new DatagramPacket(payload, length, address, Variables.PORT);
	}
	
	static DatagramPacket buildHandshakeInitiationPacket(InetAddress address, String uid, KeyPair key) throws Exception {
		/*byte[] versionBytes = new byte[4];
		ByteBuffer.wrap(versionBytes).putInt(Variables.PROTOCOL_VERSION);*/
		//Einfügen
		byte [] keyBytes = key.getPublic().getEncoded();
		int length = 1 + keyBytes.length;
		byte [] payload = new byte [length];
		payload[0] = 3;
		System.arraycopy(keyBytes, 0, payload, 1, keyBytes.length);
		return new DatagramPacket(payload, length, address, Variables.PORT);
	}
	
	static DatagramPacket buildHandshakeAcknowledgementPacket(InetAddress address, SecretKey key, PublicKey publicKey) throws Exception {
		byte [] encryptedKey = RSA.encrypt(key.getEncoded(), publicKey);
		byte [] checksum = MessageDigest.getInstance(Variables.CHECKSUM_ALGORITHM).digest(key.getEncoded());
		int length = encryptedKey.length + checksum.length +1;
		byte [] payload = new byte [length];
		payload [0] = 4;
		System.arraycopy(encryptedKey, 0, payload, 1, encryptedKey.length);
		System.arraycopy(checksum, 0, payload, 1+encryptedKey.length, checksum.length);
		return new DatagramPacket(payload, length, address, Variables.PORT);
	} 
	
	static DatagramPacket buildFailedHandshakePacket(InetAddress address) throws Exception {
		byte [] payload = new byte [1];
		payload [0] = 4;
		return new DatagramPacket(payload, 1, address, Variables.PORT);
	}
	
	static DatagramPacket buildMovePacket(InetAddress address, SecretKey key, String chessNotation, int sequence) throws Exception {
		String transmittedText = sequence+chessNotation;
		byte [] encryptedText = AES.encrypt(transmittedText.getBytes(), key);
		int length = 1+encryptedText.length;
		byte [] payload = new byte [length];
		payload [0] = 5;
		System.arraycopy(encryptedText, 0, payload, 1, encryptedText.length);
		return new DatagramPacket(payload, length, address, Variables.PORT);
	}
	static DatagramPacket buildSurrenderPacket(InetAddress address, SecretKey key) throws Exception {
		byte [] testArray = new byte [] {1, 2, 3, 4};
		byte [] encryptedArray = AES.encrypt(testArray, key);
		byte [] payload = new byte [1+encryptedArray.length];
		payload [0] = 6;
		System.arraycopy(encryptedArray, 0, payload, 1, encryptedArray.length);
		return new DatagramPacket(payload, 1+encryptedArray.length, address, Variables.PORT);
	}

}