import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Scanner;


public class SignatureDemo {
	public static byte[] generateSignature(
			FileInputStream input,
			PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException
	{
		Signature signatureFunction = Signature.getInstance("SHA256withRSA");
		signatureFunction.initSign(privateKey);
		
		byte[] data = new byte[1024];
		int nread;
		while ( (nread=input.read(data,0,data.length)) != -1 ) {
			signatureFunction.update(data, 0, nread);
		}
		return signatureFunction.sign();
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, KeyStoreException, CertificateException, UnrecoverableKeyException {
        // TODO: bring private key from real source (e.g. filesystem, citizen card)
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		PrivateKey privateKey = generator.generateKeyPair().getPrivate();

        // generate signature
		FileInputStream in1 = new FileInputStream("apresentacao.pptx");
		FileOutputStream out1 = new FileOutputStream("apresentacao1.sign");
		
		out1.write(generateSignature(in1, privateKey));
		out1.close();

        // verify signature
        in1.reset();
        FileInputStream signature = new FileInputStream("");
    }
}


























