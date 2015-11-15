package pt.isel.si.examples;

import java.security.*;
import javax.crypto.*;
import java.util.*;

public class MeasureCipherTime {
	private static int TIMES = 1024;

	private static void measureAsymmetric(SecretKey data) throws Exception {
		KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
		KeyPair pair = keyGenerator.generateKeyPair();
		
		Cipher des = Cipher.getInstance("RSA");
        des.init(Cipher.WRAP_MODE, pair.getPublic());

		long start = System.nanoTime();
		for (int i=0; i<TIMES; ++i)
            des.wrap(data);
		System.out.println((System.nanoTime()-start)/TIMES);
	}
	
	private static SecretKey measureSymmetric() throws Exception {
		Random rand = new Random();
		byte[] data = new byte[8];
        rand.nextBytes(data);

		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		SecretKey secret = keyGenerator.generateKey();

		Cipher des = Cipher.getInstance("DES/CBC/PKCS5Padding");
        des.init(Cipher.ENCRYPT_MODE, secret);

		long start = System.nanoTime();
		for (int i=0; i<TIMES; ++i)
            des.doFinal(data);
		System.out.println((System.nanoTime()-start)/TIMES);
		
		return secret;
	}

	public static void main(String[] args) throws Exception {
        SecretKey secret = measureSymmetric();
		measureAsymmetric(secret);
	}
}



