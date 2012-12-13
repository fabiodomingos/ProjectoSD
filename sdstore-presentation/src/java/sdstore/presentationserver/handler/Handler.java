package sdstore.presentationserver.handler;

import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
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
    	
    	String caminhoServidorPublica = "C:/Users/Diogo/workspace/ProjectoSD/ProjectoSD/sdstore-presentation/src/resources/keysPortal/pubPortal.key";
//    	vai buscar a chave publica
    	Key serverPublicKey = readPublicKey(caminhoServidorPublica);
    	
    	Boolean outboundProperty = (Boolean)
        context.get (MessageContext.MESSAGE_OUTBOUND_PROPERTY);
    
    	if (outboundProperty.booleanValue()) {
    		try{
    			SOAPMessage message = context.getMessage();			
    			SOAPPart soapPart = message.getSOAPPart();
    			SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
    			SOAPBody soapBody = soapEnvelope.getBody();
    			SOAPHeader soapHeader = soapEnvelope.getHeader();

    				result = true;
    				
    		}catch(Exception e){
    			System.out.println("[OUTBOUND] Apanhou excecao no metodo de criacao");
    			System.out.println(e.getClass().toString());
    			System.out.println(e.getMessage());
    			result = false;
    		}
		}
		else {
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
	
////			verificar a assinatura
			cipherDigest = b64d.decodeBuffer(soapHeader.getLastChild().getFirstChild().getTextContent());
			soapHeader.detachNode();
			soapHeader.detachNode();
		
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

	
	private Key readPublicKey(String publicKeyPath){
		Key pub = null;
		try{
			FileInputStream fin = new FileInputStream(publicKeyPath);
			byte[] pubEncoded = new byte[ fin.available() ];
			fin.read(pubEncoded);
			fin.close();
			
			X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(pubEncoded);
			KeyFactory keyFacPub = KeyFactory.getInstance("RSA");

			pub = keyFacPub.generatePublic(pubSpec);
		}catch(Exception e){
			System.out.println("[APANHEI EXCEPCAO DO READ PUBLIC]");
		}
        return pub;
	}
}
