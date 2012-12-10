package sdstore.presentationserver.handler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class ReadWriteAsymKeys {

	public static void writeKeys(String publicKeyPath,String privateKeyPath) throws Exception{
//		gera um MD5withRSA par de chaves
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("MD5WithRSA");
		keyGen.initialize(1024);
		KeyPair key = keyGen.generateKeyPair();
		System.out.println("Fim da gerecao da chave");
		
		byte[] pubEncoded = key.getPublic().getEncoded();
		
//		vai escrever no ficheiro a chave publica
		FileOutputStream fout = new FileOutputStream(publicKeyPath);
		fout.write(pubEncoded);
		fout.flush();
		fout.close();

//		vai escrever no ficheiro a chave privada
		byte[] privEncoded = key.getPrivate().getEncoded();
		fout = new FileOutputStream(privateKeyPath);
		fout.write(privEncoded);
		fout.flush();
		fout.close();
		
		System.out.println("Acabou de escrever a MD5withRSA chave");
	}
	
	public static void readKeys(String publicKeyPath,String privateKeyPath) throws Exception{
		
		System.out.println("A ler a chave publica");
		FileInputStream fin = new FileInputStream(publicKeyPath);
		byte[] pubEncoded = new byte[fin.available()];
		fin.read(pubEncoded);
		fin.close();
		
		X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(pubEncoded);
		KeyFactory keyFacPub = KeyFactory.getInstance("MD5WithRSA");
		PublicKey pub = keyFacPub.generatePublic(pubSpec);
		System.out.println(pub);
		
		System.out.println("A ler a chave privada");
		fin = new FileInputStream(privateKeyPath);
		byte[] privEncoded = new byte[fin.available()];
		fin.read(privEncoded);
		fin.close();
		
		PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(privEncoded);
		KeyFactory keyFacPriv = KeyFactory.getInstance("MD5WithRSA");
		
		PrivateKey priv = keyFacPub.generatePrivate(privSpec);
		KeyPair key = new KeyPair(pub, priv);
		System.out.println("Acabou o carregamento das chaves");
	}
}
