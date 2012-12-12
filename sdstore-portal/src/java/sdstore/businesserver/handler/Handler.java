package sdstore.businesserver.handler;

import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

//import org.jboss.com.sun.corba.se.spi.ior.MakeImmutable;

import sun.misc.*;

public class Handler implements SOAPHandler<SOAPMessageContext> {

	@Override
    public void close(MessageContext messageContext) {
        return;
    }

	@Override
	public Set<QName> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		return setTimestamp(context);
		
//		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return false;
	}

    private boolean setTimestamp(SOAPMessageContext context) {
    	
    	byte[] plainText;
    	byte[] cipherDigest;
    	boolean result = false;
    	BASE64Encoder b64e = new BASE64Encoder();
    	BASE64Decoder b64d = new BASE64Decoder();
    	
//    	vai buscar a chave privada
    	String caminhoPrivada = "C:/Users/Mimoso/workspace/ProjectoSD/sdstore-portal/src/resources/WEB-INF/keysPortal/privPortal.key";
    	String caminhoPub = "C:/Users/Mimoso/workspace/ProjectoSD/sdstore-portal/src/resources/WEB-INF/keysPortal/pubPortal.key";
////    	Key serverPublicKey = readPublicKey("C:\Users\Mimoso\workspace\ProjectoSD\sdstore-portal\src\resources\WEB-INF\keysPortal\pubPortal.key");
//    	Key serverPublicKey = readPublicKey(caminhoPub);
    	Key privateKey = readPrivateKey(caminhoPrivada);
    	
    	Boolean outboundProperty = (Boolean)
        context.get (MessageContext.MESSAGE_OUTBOUND_PROPERTY);
    
    	if (outboundProperty.booleanValue()) {
    		System.out.println("Outbound Portal SOAP message: "+context);
    		try{
    			SOAPMessage message = context.getMessage();			
    			SOAPPart soapPart = message.getSOAPPart();
    			SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
    			SOAPBody soapBody = soapEnvelope.getBody();
    			SOAPHeader soapHeader = soapEnvelope.getHeader();
    			
//    			message.writeTo(System.out);
//    			System.out.println("PASSEI PELO OUTBOUND");
    			if(soapHeader == null) {
    				// header is optional
    				soapHeader = soapEnvelope.addHeader();
    			}
			
//    			Faz a assinatura digital
    			if(soapBody.getFirstChild()!=null){
    				plainText = soapBody.getFirstChild().getTextContent().getBytes();
    			}else{
    				plainText="".getBytes();
    			}
//    			System.out.println("VOU FAZER A ASSINATURA DIGITAL LADO DO PORTAL");
    			cipherDigest = makeDigitalSignature(plainText,privateKey);
    			// create new SOAP header element
//    			System.out.println("VOU CODIFICAR A SEGUINTE MENSAGEM");
//    			message.writeTo(System.out);
    				Name name = soapEnvelope.createName("timestamp", "bn", "http://www.sd.com/");
    				SOAPElement element = soapHeader.addChildElement(name);
    				element.addTextNode(b64e.encodeBuffer(cipherDigest));
    				result = true;
//    				System.out.println("VOU IMPRIMIR A MENSAGEM CODIFICADA");
//    				message.writeTo(System.out);
    				
    		}catch(Exception e){
    			System.out.println("[OUTBOUND] Apanhou excecao no metodo de criacao");
    			System.out.println(e.getClass().toString());
    			System.out.println(e.getMessage());
    			result = false;
    		}
		}
		else {
          System.out.println("Inbound Portal SOAP message: "+context);
			try{
			SOAPMessage message = context.getMessage();
			SOAPPart soapPart = message.getSOAPPart();
			SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
			SOAPBody soapBody = message.getSOAPBody();
			SOAPHeader soapHeader = soapEnvelope.getHeader();
			
			if(soapHeader==null){
				soapHeader = soapEnvelope.addHeader();
			}
//			
			if(soapBody.getFirstChild()!=null){
				plainText = soapBody.getFirstChild().getTextContent().getBytes();
			}else{
				plainText="".getBytes();
			}
			
////			serverPublicKey - falta buscar a chave com o readKeys
//			
//			Key fornecedorPublicKey = readPublicKey("");
////			verificar a assinatura
//			cipherDigest = b64d.decodeBuffer(soapHeader.getLastChild().getFirstChild().getTextContent());
//			soapHeader.detachNode();
//			soapHeader.detachNode();
//			
//			if(!verifyDigitalSignature(cipherDigest,plainText,serverPublicKey)){
//				return false;
//			}
			
			result = true;
			
			}catch(Exception e){
				System.out.println("[INDOUND] Apanhou excecao no metodo de criacao");
				System.out.println(e.getClass().toString());
    			System.out.println(e.getMessage());
    			result = false;
			}
		}
    	return result;
    }

	private boolean verifyDigitalSignature(byte[] cipherDigest,
			byte[] plainText, Key serverPublicKey) throws Exception {
		Signature sig = Signature.getInstance("MD5WithRSA");
		sig.initVerify((PublicKey) serverPublicKey);
		sig.update(plainText);
		try{
			return sig.verify(cipherDigest);
		}catch(SignatureException e){
			return false;
		}
	}

	private byte[] makeDigitalSignature(byte[] plainText, Key privateKey) throws Exception{
		Signature sig = Signature.getInstance("MD5WithRSA");
		sig.initSign((PrivateKey) privateKey);
		sig.update(plainText);
		byte[] signature = sig.sign();
		return signature;
	}


	private Key readPublicKey(String publicKeyPath){
		Key pub = null;
		try{
			System.out.println("Reading public key from file " + publicKeyPath + " ...");
			FileInputStream fin = new FileInputStream(publicKeyPath);
			byte[] pubEncoded = new byte[ fin.available() ];
			fin.read(pubEncoded);
			fin.close();

			X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(pubEncoded);
			KeyFactory keyFacPub = KeyFactory.getInstance("RSA");

			pub = keyFacPub.generatePublic(pubSpec);
			
			System.out.println(pub);
			System.out.println("---");
		}catch(Exception e){
			System.out.println("[APANHEI EXCEPCAO DO READ PUBLIC]");
		}
		
        return pub;

	}
	
	private Key readPrivateKey(String privateKeyPath){
	       Key priv = null;
			try{
				System.out.println("Reading private key from file " + privateKeyPath + " ...");
				FileInputStream fin = new FileInputStream(privateKeyPath);
				byte[] privEncoded = new byte[ fin.available() ];
				fin.read(privEncoded);
				fin.close();

				PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(privEncoded);
				KeyFactory keyFacPriv = KeyFactory.getInstance("RSA");
				
				priv = keyFacPriv.generatePrivate(privSpec);
				
				System.out.println(priv);
				System.out.println("---");
	    
	       }catch(Exception e){
	    	   System.out.println("[APANHEI EXCEPCAO DO READ PRIVATE]");
	       }
	       
	       return priv;
	    }
}
