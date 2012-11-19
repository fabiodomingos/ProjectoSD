package sdstore.businesserver.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

import sdstore.businesserver.exception.CategoriaNameException;
import sdstore.businesserver.exception.ProdutoExistException;
import sdstore.businesserver.exception.ProdutoListException;
import sdstore.businesserver.exception.QuantidadeException;
import sdstore.businesserver.service.dto.CategoriaListDto;
import sdstore.businesserver.service.dto.ProdutoDto;
import sdstore.businesserver.service.dto.ProdutoListDto;

@WebService
public class PortalWebService {
	
	@WebMethod
	public String RetiraProduto(String codigo,Integer quantidade) throws ProdutoExistException, QuantidadeException{
		RetiraProdutoService service = new RetiraProdutoService(codigo,quantidade);
		service.execute();
		String result = service.getResultado();
		return result;
	}
	
	
	@WebMethod
	public ProdutoDto PedeProduto(String codigo) throws ProdutoExistException{
		PedeProdutoService service = new PedeProdutoService(codigo);
		service.execute();
		ProdutoDto result = service.getProdutoDto();
		return result;
	}

	
	@WebMethod
	public ProdutoListDto GetListaProdutoWebService() throws ProdutoListException{
		GetListaProdutosService service = new GetListaProdutosService();
		service.execute();
		ProdutoListDto result = service.getListaProdutos();
		return result;
	}
	
	@WebMethod
	public CategoriaListDto ListaCategoriaWebService() throws ProdutoListException{
		ListaCategoriaService service = new ListaCategoriaService();
		service.execute();
		CategoriaListDto result = service.getListCategoria();
		return result;	
	}
}
