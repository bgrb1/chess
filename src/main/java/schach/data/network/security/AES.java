package schach.data.network.security;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import schach.data.Variables;

public final class AES {
	
	public final static SecretKey generateKey() throws Exception {
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(Variables.AES_KEY_SIZE);
		return generator.generateKey();
	}
	
	public final static SecretKey byteArrayToSecretKey(byte [] keyBytes) throws Exception {
		SecretKey key = new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
		return key;
	}
	
	public final static byte [] encrypt(byte [] plain, SecretKey key) throws Exception {
		Cipher aes = Cipher.getInstance("AES");
		aes.init(Cipher.ENCRYPT_MODE, key);
		return aes.doFinal(plain);		
	}
	
	public final static byte [] encrypt(byte [] plain, byte [] key) throws Exception {
		key = MessageDigest.getInstance("SHA-256").digest(key);
		key = Arrays.copyOf(key, 16);
		SecretKeySpec sks = new SecretKeySpec(key, "AES");
				
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, sks);
		
		return cipher.doFinal(plain);	
	}
	
	public final static byte [] decrypt(byte [] encrypted, SecretKey key) throws Exception {			
		Cipher aes = Cipher.getInstance("AES");
		aes.init(Cipher.DECRYPT_MODE, key);	
		return aes.doFinal(encrypted);
	} 
	
	public final static byte [] decrypt(byte [] encrypted, byte [] key) throws Exception {	
		key = MessageDigest.getInstance("SHA-256").digest(key);
		key = Arrays.copyOf(key, 16);
		SecretKeySpec sks = new SecretKeySpec(key, "AES");
				
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, sks);	
		
		return cipher.doFinal(encrypted);
	}

}
