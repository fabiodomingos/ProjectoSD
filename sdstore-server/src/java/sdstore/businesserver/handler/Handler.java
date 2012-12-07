package sdstore.businesserver.handler;

import java.util.Date;
import java.util.Set;

import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class Handler implements SOAPHandler<SOAPMessageContext> {

	@Override
    public void close(MessageContext messageContext) {
        return;
    }

	@Override
	public Set getHeaders() {
		return null;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		setTimestamp(context);
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return false;
	}

    private void setTimestamp(SOAPMessageContext context) {
    	Boolean outboundProperty = (Boolean)
        context.get (MessageContext.MESSAGE_OUTBOUND_PROPERTY);
    
    	if (outboundProperty.booleanValue()) {
    		System.out.println("Outbound SOAP message:");
        
    		try{
    			SOAPMessage message = context.getMessage();			
    			SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
		
		SOAPHeader soapHeader = soapEnvelope.getHeader();
		if(soapHeader == null) {
			// header is optional
			soapHeader = soapEnvelope.addHeader();
		}
		// create new SOAP header element
		{
			Name name = soapEnvelope.createName("timestamp", "bn", "http://www.sd.com/");
			SOAPElement element = soapHeader.addChildElement(name);
			
			element.addTextNode( Long.toString(new Date().getTime())  );
		}
		
		 message.writeTo(System.out);
         System.out.println("\n");
		}

		catch (Exception e) {
		// print error information
		System.out.println("Caught exception in build method: ");
		System.out.println(e.getClass().toString());
		System.out.println(e.getMessage());
		}
		
		}
		else {
          System.out.println("Inbound SOAP message: none");
		}
    }
}
