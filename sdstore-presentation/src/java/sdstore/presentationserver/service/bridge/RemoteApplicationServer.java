package sdstore.presentationserver.service.bridge;

import java.util.HashMap;
import java.util.Map;

public class RemoteApplicationServer implements ApplicationServerBridge{

	private static Map<String,String> endpointUrlMap;
	
	static{
		RemoteApplicationServer.endpointUrlMap = new HashMap<String,String>();
		RemoteApplicationServer.endpointUrlMap.put("Portal", "http://localhost:8080/sdstore-server/blabla?wsdl");
	}
	
//	PortalWebService webService:
//	{
//		PortalWebServiceService servico = new PortalWebServiceService();
//		webService = servico.getPortalWebServicePort();
//	}
}
