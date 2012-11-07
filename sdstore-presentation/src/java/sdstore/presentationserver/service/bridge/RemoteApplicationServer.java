package sdstore.presentationserver.service.bridge;

import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import sdstore.presentationserver.service.stubs.CarrinhoDto;
import sdstore.presentationserver.service.stubs.CategoriaListDto;
import sdstore.presentationserver.service.stubs.ConsolaWebService;
import sdstore.presentationserver.service.stubs.ConsolaWebServiceService;
import sdstore.presentationserver.service.stubs.ProdListDto;

public class RemoteApplicationServer implements ApplicationServerBridge{

	private static Map<String,String> endpointUrlMap;
	
	static{
		RemoteApplicationServer.endpointUrlMap = new HashMap<String,String>();
		RemoteApplicationServer.endpointUrlMap.put("Portal", "http://localhost:8080/sdstore-portal/Portal?wsdl");
	}
	
	ConsolaWebService webService;
	{
		ConsolaWebServiceService servico = new ConsolaWebServiceService();
		webService = servico.getConsolaWebServicePort();
	}
	
	private void updateEndpointUrl(){
		String endpointUrl = RemoteApplicationServer.endpointUrlMap.get("Portal");
		if (endpointUrl == null) {
//			throw new OperatorPrefixException_Exception();
		}
		BindingProvider bp = (BindingProvider)webService;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);
	}

	@Override
	public CategoriaListDto listaCategoria() {
		updateEndpointUrl();
		CategoriaListDto resultado = webService.listaCategoriaWebService();
		return resultado;
	}

	@Override
	public ProdListDto listaProdutosCategoria(String categoria) {
		updateEndpointUrl();
		ProdListDto resultado = webService.listaProduto(categoria);
		return resultado;
	}

	@Override
	public void Junta(String codigo,Integer quantidade) {
		updateEndpointUrl();
		webService.juntaCarrinho(codigo, quantidade);
	}

	@Override
	public CarrinhoDto Carrinho() {
		updateEndpointUrl();
		CarrinhoDto dtoCarrinho = webService.listaCarrinho();
		return dtoCarrinho;
	}
	
	@Override
	public void Limpa(){
		updateEndpointUrl();
		webService.limpaCarrinho();
	}
	
	@Override
	public void Encomenda() {
		updateEndpointUrl();
		webService.encomenda();
		
	}
	
	
}
