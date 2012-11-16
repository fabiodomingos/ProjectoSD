package sdstore.businesserver.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.BindingProvider;

import sdstore.businesserver.exception.CategoriaNameException;
import sdstore.businesserver.exception.ProdutoExistException;
import sdstore.businesserver.exception.ProdutoListException;
import sdstore.businesserver.exception.QuantidadeException;
import sdstore.businesserver.service.dto.CarrinhoDto;
import sdstore.businesserver.service.dto.CategoriaListDto;
import sdstore.businesserver.service.dto.ProdListDto;
import sdstore.businesserver.service.dto.ProdutoDto;
import sdstore.stubs.CategoriaNameException_Exception;
import sdstore.stubs.PortalWebService;
import sdstore.stubs.PortalWebServiceService;
import sdstore.stubs.ProdutoExistException_Exception;
import sdstore.stubs.ProdutoListException_Exception;
import sdstore.stubs.QuantidadeException_Exception;

@WebService
public class ConsolaWebService {
	
	private static Map<String, String> endpointUrlMap;
//	carrinho de compras do cliente
	private List<ProdutoDto> carrinhoCompras = new ArrayList<ProdutoDto>();
	private Set<String> listaCategoriasPortal = new HashSet<String>();
	
	static{
		ConsolaWebService.endpointUrlMap = new HashMap<String, String>();
		ConsolaWebService.endpointUrlMap.put("fornecedor1", "http://localhost:8080/sdstore-server-fornecedor1/BusinessServerFornecedor1");
		ConsolaWebService.endpointUrlMap.put("fornecedor2", "http://localhost:8080/sdstore-server-fornecedor2/BusinessServerFornecedor2");
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
	public CategoriaListDto listaCategoriaWebService() throws ProdutoListException{
		try{
				updateEndpointUrl("fornecedor1");
				List<String> listaCategoria =  webService.listaCategoriaWebService().getCategoriaList();
				CategoriaListDto dto = new CategoriaListDto();
//				dto.setCategoriaList(listaCategoria);
				for(String cate: listaCategoria){
					juntaCategorias(cate);
				}
				updateEndpointUrl("fornecedor2");
				List<String> listaCategoria2 =  webService.listaCategoriaWebService().getCategoriaList();
//				CategoriaListDto dto2 = new CategoriaListDto();
//				dto2.setCategoriaList(listaCategoria2);
				for(String cate: listaCategoria2){
					juntaCategorias(cate);
				}
				dto.setCategoriaList(listaCategoriasPortal);
				return dto;
		}catch(ProdutoListException_Exception e){
			throw new ProdutoListException();
		}
	}
	
	@WebMethod
	public ProdListDto listaProduto(String categoria) throws ProdutoListException, CategoriaNameException{
		
		updateEndpointUrl("fornecedor1");
		try{
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
		}catch(ProdutoListException_Exception e){
			throw new ProdutoListException();
		}catch(CategoriaNameException_Exception e){
			throw new CategoriaNameException(categoria);
		}
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
	public void juntaCarrinho(String codigo,Integer quantidade) throws ProdutoExistException{
		updateEndpointUrl("fornecedor1");
		try{
		sdstore.stubs.ProdutoDto dtoRecebido = webService.pedeProduto(codigo);
		Integer controlo = 0;
		ProdutoDto prodEnviar = new ProdutoDto();
		if(carrinhoCompras.isEmpty()){			
			prodEnviar.setId(dtoRecebido.getId());
			prodEnviar.setQuantidade(quantidade);
			prodEnviar.setPreco(dtoRecebido.getPreco()+dtoRecebido.getPreco()*0.1);
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
			prodEnviar.setPreco(dtoRecebido.getPreco()+dtoRecebido.getPreco()*0.1);
			prodEnviar.setCategoria(dtoRecebido.getCategoria());
			prodEnviar.setDescricao(dtoRecebido.getDescricao());
			carrinhoCompras.add(prodEnviar);
			}
		}
		}catch(ProdutoExistException_Exception e){
			throw new ProdutoExistException(codigo);
		}

	}
	
	@WebMethod
	public void limpaCarrinho(){
		updateEndpointUrl("fornecedor1");
		carrinhoCompras.clear();
		
	}
	
	@WebMethod
	public void encomenda() throws ProdutoExistException, QuantidadeException{
		String nome = null;
		Integer quantidade = 0;
		try{		
		updateEndpointUrl("fornecedor1");
		for(ProdutoDto prod: carrinhoCompras){
			String resultado = webService.retiraProduto(prod.getId(), prod.getQuantidade());
			nome = prod.getId();
			quantidade = prod.getQuantidade();
		}
		carrinhoCompras.clear();
		}catch(ProdutoExistException_Exception e){
			throw new ProdutoExistException(nome);
		}catch(QuantidadeException_Exception e){
			carrinhoCompras.clear();
			throw new QuantidadeException(quantidade);
		}
	}
	
	
	public void juntaCategorias(String categoria){
		
		listaCategoriasPortal.add(categoria);
	}
}
