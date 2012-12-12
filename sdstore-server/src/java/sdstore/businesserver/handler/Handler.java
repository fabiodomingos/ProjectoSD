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

import sdstore.businesserver.BusinessServerInitListener;
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
//    	Key serverPublicKey = readPublicKey(BusinessServerInitListener.caminhoChavePublica);
    	Key privateKey = readPrivateKey(BusinessServerInitListener.caminhoChavePrivada);
    	
    	Boolean outboundProperty = (Boolean)
        context.get (MessageContext.MESSAGE_OUTBOUND_PROPERTY);
    
    	if (outboundProperty.booleanValue()) {
    		System.out.println("Outbound Fornecedor SOAP message: "+context);
    		try{
    			SOAPMessage message = context.getMessage();
    			
//    			System.out.println("A IMPRIMIR MENSAGEM QUE SAI");
//    			message.writeTo(System.out);
    			
    			SOAPPart soapPart = message.getSOAPPart();
    			SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
    			SOAPBody soapBody = soapEnvelope.getBody();
    			SOAPHeader soapHeader = soapEnvelope.getHeader();
    			
    			if(soapHeader == null) {
    				// header is optional
    				soapHeader = soapEnvelope.addHeader();
    			}	
////    			Faz a assinatura digital
    			if(soapBody.getFirstChild()!=null){
    				plainText = soapBody.getFirstChild().getTextContent().getBytes();
    			}else{
    				plainText="".getBytes();
    			}
//    			System.out.println("VOU FAZER A ASSINATURA DIGITAL");
    			cipherDigest = makeDigitalSignature(plainText,privateKey);
//    			// create new SOAP header element
//    			System.out.println("FIZ A ASSINATURA DIGITAL E VOU METER NO CABECALHO");
    				Name name = soapEnvelope.createName("timestamp", "bn", "http://www.sd.com/");
    				SOAPElement element = soapHeader.addChildElement(name);
    				element.addTextNode(b64e.encodeBuffer(cipherDigest));
    				result = true;
//    				System.out.println("IMPRIMIR A MENSAGEM COM A CIFRA FEITA");
//    				message.writeTo(System.out);

    				
    		}catch(Exception e){
    			System.out.println("[OUTBOUND] Apanhou excecao no metodo de criacao");
    			System.out.println(e.getClass().toString());
    			System.out.println(e.getMessage());
    			result = false;
    		}
		}
		else {
          System.out.println("Inbound Fornecedor SOAP message: "+context);
			try{
			SOAPMessage message = context.getMessage();
//			System.out.println("A IMPRIMIR A MENSAGEM QUE CHEGA");
//			message.writeTo(System.out);
			SOAPPart soapPart = message.getSOAPPart();
			SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
			SOAPBody soapBody = message.getSOAPBody();
			SOAPHeader soapHeader = soapEnvelope.getHeader();
			
			if(soapHeader==null){
				soapHeader = soapEnvelope.addHeader();
			}

			if(soapBody.getFirstChild()!=null){
				plainText = soapBody.getFirstChild().getTextContent().getBytes();
			}else{
				plainText="".getBytes();
			}
			
			Key serverPublicKey = readPublicKey("C:/Users/Mimoso/workspace/ProjectoSD/sdstore-server/src/resources/WEB-INF/keysPortal/pubPortal.key");
//			verificar a assinatura
//			System.out.println("VOU DESCODIFICAR A MENSAGEM QUE RECEBI");
			message.writeTo(System.out);
			cipherDigest = b64d.decodeBuffer(soapHeader.getLastChild().getFirstChild().getTextContent());
//			System.out.println("DESCODIFIQUEI A MENSAGEM QUE RECEBI");
			soapHeader.detachNode();
			soapHeader.detachNode();
//			message.writeTo(System.out);
			
			if(!verifyDigitalSignature(cipherDigest,plainText,serverPublicKey)){
				return false;
			}
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
	
	
	private Key readPrivateKey(String privateKeyPath){
	       Key priv = null;
			try{
				System.out.println("Reading private key from file " + privateKeyPath + " ...");
				FileInputStream fin = new FileInputStream(privateKeyPath);
//				System.out.println("[FIZ O fileINPUT privada]");
				byte[] privEncoded = new byte[ fin.available() ];
				fin.read(privEncoded);
				fin.close();
//				System.out.println("[FIZ o privEncoded]");

				PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(privEncoded);
//				System.out.println("[FIZ o PKCS8]");
				KeyFactory keyFacPriv = KeyFactory.getInstance("RSA");
//				System.out.println("[FIZ keyFacPriv]");
				
				priv = keyFacPriv.generatePrivate(privSpec);
//				System.out.println("[Gerei o priv]");
				
				System.out.println(priv);
				System.out.println("---");
	    
	       }catch(Exception e){
	    	   System.out.println("[APANHEI EXCEPCAO DO READ PRIVATE]");
	       }
	       
	       return priv;
	    }
	
	public static Key readPublicKey(String publicKeyPath){
		Key pub = null;
		try{
			System.out.println("Reading public key from file " + publicKeyPath + " ...");
			FileInputStream fin = new FileInputStream(publicKeyPath);
//			System.out.println("[FIZ O fileINPUT publica]");
			byte[] pubEncoded = new byte[ fin.available() ];
			fin.read(pubEncoded);
			fin.close();
//			System.out.println("[FIZ o pubEncoded]");

			X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(pubEncoded);
//			System.out.println("[FIZ o X509]");
			KeyFactory keyFacPub = KeyFactory.getInstance("RSA");
//			System.out.println("[FIZ keyFacPub]");

			pub = keyFacPub.generatePublic(pubSpec);
//			System.out.println("[Gerei o pub]");
        
			System.out.println(pub);
			System.out.println("---");
		}catch(Exception e){
			System.out.println("[APANHEI EXCEPCAO DO READ PUBLIC]");
		}
		
        return pub;

	}
}
