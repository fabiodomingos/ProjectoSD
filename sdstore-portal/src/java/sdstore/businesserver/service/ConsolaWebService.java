package sdstore.businesserver.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.BindingProvider;

import sdstore.businesserver.service.dto.CategoriaListDto;
import sdstore.businesserver.service.dto.ProdListDto;
import sdstore.businesserver.service.dto.ProdutoDto;
import sdstore.stubs.PortalWebService;
import sdstore.stubs.PortalWebServiceService;
import sdstore.stubs.ProdutoListDto;

@WebService
public class ConsolaWebService {
	
private static Map<String, String> endpointUrlMap;
	
	static{
		ConsolaWebService.endpointUrlMap = new HashMap<String, String>();
		ConsolaWebService.endpointUrlMap.put("fornecedor1", "http://localhost:8080/sdstore-server-fornecedor1/BusinessServerFornecedor1");
	}
	
	PortalWebService webService;
	
	{
		PortalWebServiceService service = new PortalWebServiceService();
		webService = service.getPortalWebServicePort();
	}
	
	private void updateEndpointUrl(String fornecedorName){
		String endpointUrl = ConsolaWebService.endpointUrlMap.get(fornecedorName);
//		if(endpointUrl==null){
//			throw new FornecedorNameException_Exception();
//		}
		BindingProvider bp = (BindingProvider)webService;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);
	}
	
	@WebMethod
	public CategoriaListDto listaCategoriaWebService(){
		updateEndpointUrl("fornecedor1");
		List<String> listaCategoria =  webService.listaCategoriaWebService().getCategoriaList();
		CategoriaListDto dto = new CategoriaListDto();
		dto.setCategoriaList(listaCategoria);
		return dto;
	}
	
	@WebMethod
	public ProdListDto listaProduto(String categoria){
		updateEndpointUrl("fornecedor1");
		List<sdstore.stubs.ProdutoDto> listaProduto = webService.listaProdutoWebService(categoria).getListaDto();
		List<ProdutoDto> lista = new ArrayList<ProdutoDto>();
//		teste - listaProduto está vazia
		System.out.println(listaProduto);
		
		for(sdstore.stubs.ProdutoDto prod : listaProduto){
			ProdutoDto novo = new ProdutoDto();
			novo.setCategoria(prod.getCategoria());
			novo.setDescricao(prod.getDescricao());
			novo.setId(prod.getId());
			novo.setPreco(prod.getPreco());
			novo.setQuantidade(prod.getQuantidade());
			lista.add(novo);
		}
		ProdListDto dto = new ProdListDto(lista);
//		dto.setListaDto(lista);
		return dto;
	}
	
}
