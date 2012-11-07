package sdstore.businesserver.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

import sdstore.businesserver.exception.ProdutoListException;
import sdstore.businesserver.service.dto.CategoriaListDto;
import sdstore.businesserver.service.dto.ProdutoDto;
import sdstore.businesserver.service.dto.ProdutoListDto;

@WebService
public class PortalWebService {
	
	@WebMethod
	public String RetiraProduto(String codigo,Integer quantidade){
		RetiraProdutoService service = new RetiraProdutoService(codigo,quantidade);
		service.execute();
		String result = service.getResultado();
		return result;
	}
	
	
	@WebMethod
	public ProdutoDto PedeProduto(String codigo){
		PedeProdutoService service = new PedeProdutoService(codigo);
		service.execute();
		ProdutoDto result = service.getProdutoDto();
		return result;
	}

	
	@WebMethod
	public ProdutoListDto ListaProdutoWebService(String categoria){
		ListaProdutoService service = new ListaProdutoService(categoria);
		service.execute();
		ProdutoListDto result = service.getListProdutos();
		return result;
	}
	
	@WebMethod
	public CategoriaListDto ListaCategoriaWebService() {
		ListaCategoriaService service = new ListaCategoriaService();
		service.execute();
		CategoriaListDto result = service.getListCategoria();
		return result;	
	}
}
