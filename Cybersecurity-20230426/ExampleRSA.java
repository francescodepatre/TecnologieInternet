package dsg.crypto;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ExampleRSA {

	public ExampleRSA() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		
		// generate our RSA key pair
		KeyPairGenerator generator = null;
		try {
			generator = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		generator.initialize(2048);
		KeyPair pair = generator.generateKeyPair();
		
		// extract the private and public key
		PrivateKey privateKey = pair.getPrivate();
		PublicKey publicKey = pair.getPublic();
		
		// save the public key in a file 
		try (FileOutputStream fos = new FileOutputStream("public.key")) {
		    fos.write(publicKey.getEncoded());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String secretMessage = "This is a secret message";
		System.out.println("plaintext: " + secretMessage);
		
		Cipher encryptCipher = null;
		try {
			encryptCipher = Cipher.getInstance("RSA");
			encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		
		// encrypt the plaintext using the public key
		byte[] secretMessageBytes = secretMessage.getBytes(StandardCharsets.UTF_8);
		byte[] encryptedMessageBytes = null;
		try {
			encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
		// encode the ciphertext with the Base64 Alphabet (to store it in a database or send it via REST API)
		String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);	
		System.out.println("cyphertext: " + encodedMessage);
		
		Cipher decryptCipher = null;
		try {
			decryptCipher = Cipher.getInstance("RSA");
			decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		
		// decrypt the ciphertext using the private key
		byte[] decryptedMessageBytes = null;
		try {
			decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		
		String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
		
		System.out.println("decypted message: " + decryptedMessage);
		
	}

}
