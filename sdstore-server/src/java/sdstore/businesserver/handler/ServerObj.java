package sdstore.businesserver.handler;

import java.security.*;

public class ServerObj {

	private static KeyPair keys;
	private static String identificador;
		
	public ServerObj(KeyPair chave,String id){
		setKeyPair(chave);
		setIdentificador(id);
	}
	
	public static void setKeyPair(KeyPair chave){
		keys = chave;
	}
	
	public static void setIdentificador(String id){
		identificador = id;
	}
	
	public static Key getServerPrivateKey(){
		return keys.getPrivate();
	}
	
	public static Key getServerPublicKey(){
		return keys.getPublic();
	}
	
	public static String getIdentificador(){
		return identificador;
	}
}
