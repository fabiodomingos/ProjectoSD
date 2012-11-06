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
		RemoteApplicationServer.endpointUrlMap.put("fornecedor1", "http://localhost:8080/sdstore-server-fornecedor1/BusinessServerFornecedor1");
	}
	
	PortalWebService webService;
	
	{
		PortalWebServiceService service = new PortalWebServiceService();
		webService = service.getPortalWebServicePort();
	}
	
	private void updateEndpointUrl(String fornecedorName){
		String endpointUrl = RemoteApplicationServer.endpointUrlMap.get(fornecedorName);
//		if(endpointUrl==null){
//			throw new FornecedorNameException_Exception();
//		}
		BindingProvider bp = (BindingProvider)webService;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);
	}

	@Override
	public sdstore.stubs.CategoriaListDto ListaCategoria() {
		updateEndpointUrl("fornecedor1");
//		sdstore.stubs.CategoriaListDto result = null;
		return webService.ListaCategoriaWebService();
	}
	
	@Override 
	public sdstore.stubs.ProdutoListDto ListaProduto(String categoria){
		updateEndpointUrl("fornecedor1");
		return webService.ListaProdutoWebService(categoria);
	}
	
	
}
