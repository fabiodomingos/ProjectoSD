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
	
	PortalWebService webService;
	
	{
		PortalWebServiceService service = new PortalWebServiceService();
		webService = service.getPortalWebServicePort();
	}
	
	private void updateEndpointUrl(String fornecedorName) throws FornecedorNameExcetion_Exception{
		String endpointUrl = RemoteApplicationServer.endpointUrlMap.get(fornecedorName);
		if(endpointUrl==null){
			throw new FornecedorNameException_Exception();
		}
		BindingProvider bp = (BindingProvider)webService;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);
	}
	
	public CategoriaListDto PortalCategoriaList(FornecedorSelectionDto dto) throws ServiceBridgeFornecedorException, FornecedorNameException, CategoriaNameException{
		
		String fornecedorName = dto.getNome();
		updateEndpointUrl(fornecedorName);
		CategoriaListDto result = null;
		try{
			result = webService.ListaCategoriaWebService(dto);
		}catch(WebServiceException ex){
			throw new ServiceBridgeFornecedorException("PortalCategoriaList");
		}
		
	}

}
