package sdstore.businesserver.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.BindingProvider;

import sdstore.businesserver.service.dto.CarrinhoDto;
import sdstore.businesserver.service.dto.CategoriaListDto;
import sdstore.businesserver.service.dto.ProdListDto;
import sdstore.businesserver.service.dto.ProdutoDto;
import sdstore.stubs.PortalWebService;
import sdstore.stubs.PortalWebServiceService;

@WebService
public class ConsolaWebService {
	
	private static Map<String, String> endpointUrlMap;
//	carrinho de compras do cliente
	private List<ProdutoDto> carrinhoCompras = new ArrayList<ProdutoDto>();
	
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
		for(sdstore.stubs.ProdutoDto prod : listaProduto){
			ProdutoDto novo = new ProdutoDto();
			novo.setCategoria(prod.getCategoria());
			novo.setDescricao(prod.getDescricao());
			novo.setId(prod.getId());
			novo.setPreco(prod.getPreco()+0.1*prod.getPreco());
			novo.setQuantidade(prod.getQuantidade());
			lista.add(novo);
		}
		ProdListDto dto = new ProdListDto(lista);
		return dto;
	}
	
	@WebMethod
	public CarrinhoDto listaCarrinho(){
		updateEndpointUrl("fornecedor1");
		Double precoTotal = 0.0;
		CarrinhoDto dto = new CarrinhoDto(carrinhoCompras);
		for(ProdutoDto prod : carrinhoCompras){
			System.out.println(prod);
			precoTotal = precoTotal + prod.getPreco()*prod.getQuantidade();
		}
		dto.setTotalPreco(precoTotal);
		return dto;
	}
	
	@WebMethod
	public void juntaCarrinho(String codigo,Integer quantidade){
		updateEndpointUrl("fornecedor1");
		sdstore.stubs.ProdutoDto dtoRecebido = webService.pedeProduto(codigo);
		Integer controlo = 0;
		ProdutoDto prodEnviar = new ProdutoDto();
		if(carrinhoCompras.isEmpty()){			
			prodEnviar.setId(dtoRecebido.getId());
			prodEnviar.setQuantidade(quantidade);
			prodEnviar.setPreco(dtoRecebido.getPreco());
			prodEnviar.setCategoria(dtoRecebido.getCategoria());
			prodEnviar.setDescricao(dtoRecebido.getDescricao());
			carrinhoCompras.add(prodEnviar);
		}
		else{
			for(ProdutoDto dto: carrinhoCompras){
				if(dto.getId().equals(codigo)){
					dto.setQuantidade(quantidade+dto.getQuantidade());
					controlo=1;
				}
			}
			if(controlo==0){
			prodEnviar.setId(dtoRecebido.getId());
			prodEnviar.setQuantidade(quantidade);
			prodEnviar.setPreco(dtoRecebido.getPreco());
			prodEnviar.setCategoria(dtoRecebido.getCategoria());
			prodEnviar.setDescricao(dtoRecebido.getDescricao());
			carrinhoCompras.add(prodEnviar);
			}
		}

	}
	
	@WebMethod
	public void limpaCarrinho(){
		updateEndpointUrl("fornecedor1");
		carrinhoCompras.clear();
		
	}
	
	@WebMethod
	public void encomenda(){
		updateEndpointUrl("fornecedor1");
		for(ProdutoDto prod: carrinhoCompras){
			String resultado = webService.retiraProduto(prod.getId(), prod.getQuantidade());
		}
		carrinhoCompras.clear();
	}
}
