package schach.data.network.security;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import schach.data.Variables;

public final class RSA {
	
	public final static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(Variables.RSA_KEY_SIZE);      
        return generator.genKeyPair();
	}
	
	public final static PublicKey byteArrayToPublicKey(byte [] keyBytes) throws Exception {
	    KeyFactory factory = KeyFactory.getInstance("RSA");
	    EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(keyBytes);
	    return factory.generatePublic(publicKeySpec);
	}
	
	public final static byte [] encrypt(byte [] plainBytes, PublicKey publicKey) throws Exception {
        Cipher rsa = Cipher.getInstance("RSA");  
        rsa.init(Cipher.ENCRYPT_MODE, publicKey);  
        return rsa.doFinal(plainBytes); 
	}
	
	public final static byte [] decrypt(byte [] encryptedBytes, PrivateKey privateKey) throws Exception {
        Cipher rsa = Cipher.getInstance("RSA");  
        rsa.init(Cipher.DECRYPT_MODE, privateKey);  
        return rsa.doFinal(encryptedBytes); 
	}

}
