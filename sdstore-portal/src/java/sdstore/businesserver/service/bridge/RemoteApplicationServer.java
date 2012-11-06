package sdstore.businesserver.service.bridge;

import java.util.HashMap;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;

import sdstore.businesserver.exception.CategoriaNameException;
import sdstore.businesserver.exception.FornecedorNameException;
import sdstore.businesserver.exception.ServiceBridgeFornecedorException;
import sdstore.businesserver.service.PortalWebService;
import sdstore.businesserver.service.dto.CategoriaDto;
import sdstore.businesserver.service.dto.CategoriaListDto;
import sdstore.businesserver.service.dto.FornecedorSelectionDto;

public class RemoteApplicationServer implements ApplicationServerBridge{
	
	private static Map<String, String> endpointUrlMap;
	
	static{
		RemoteApplicationServer.endpointUrlMap = new HashMap<String, String>();
		RemoteApplicationServer.endpointUrlMap.put("fornecedor1", "url");
	}
	
	FornecedorWebService webService;
	
	{
		FornecedorWebServiceService service = new FornecedorWebServiceService();
		webService = service.getFornecedorWebServicePort();
	}
	
	private void updateEndpointUrl(String fornecedorName){
		String endpointUrl = RemoteApplicationServer.endpointUrlMap.get(fornecedorName);
//		if(endpointUrl==null){
//			throw new FornecedorNameException_Exception();
//		}
		BindingProvider bp = (BindingProvider)webService;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);
	}
	
	public CategoriaListDto CategoriaList( ) throws ServiceBridgeFornecedorException{
		
		updateEndpointUrl("fornecedor1");
		CategoriaListDto result = null;
		try{
			result=webService.ListaCategoriaService();
		}catch(WebServiceException ex){
			throw new ServiceBridgeFornecedorException("CategoriaList");
		}
		return result;
		
//		CategoriaListDto result = null;
//		try{
//			result = webService.ListaCategoriaWebService(dto);
//		}catch(WebServiceException ex){
//			throw new ServiceBridgeFornecedorException("PortalCategoriaList");
//		}
		
	}

}
