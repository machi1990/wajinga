package com.machi.wajinga.dao.tools;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Encryption {
	private Cipher ecipher;
	private Cipher dcipher;
	private SecretKey key;
	
	private static Encryption instance;
	
	public static Encryption instance () {
		if (instance == null) {
			instance = new Encryption();
		}
		
		return instance;
	}
	
	private Encryption() {
		try {
			key = KeyGenerator.getInstance("DES").generateKey();

			ecipher = Cipher.getInstance("DES");
			dcipher = Cipher.getInstance("DES");

			ecipher.init(Cipher.ENCRYPT_MODE, key);

			dcipher.init(Cipher.DECRYPT_MODE, key);

			String encrypted = encrypt("This is a classified message!");

			String decrypted = decrypt(encrypted);

			System.out.println("Decrypted: " + decrypted);

		} catch (NoSuchAlgorithmException e) {
			System.out.println("No Such Algorithm:" + e.getMessage());
			return;
		} catch (NoSuchPaddingException e) {
			System.out.println("No Such Padding:" + e.getMessage());
			return;
		} catch (InvalidKeyException e) {
			System.out.println("Invalid Key:" + e.getMessage());
			return;
		}

	}

	public String encrypt(String str) {
		try {
			byte[] utf8 = str.getBytes("UTF8");
			byte[] enc = ecipher.doFinal(utf8);
			enc = Base64.getEncoder().encode(enc);
			return new String(enc);
		}
		catch (Exception e) {
		}
		return null;
	}

	public String decrypt(String str) {
		try {
			byte[] dec = Base64.getDecoder().decode(str.getBytes());
			byte[] utf8 = dcipher.doFinal(dec);
			return new String(utf8, "UTF8");
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
