package sdstore.businesserver.service;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.transaction.xa.XAException;

import sdstore.businesserver.exception.ProdutoExistException;
import sdstore.businesserver.exception.ProdutoListException;
import sdstore.businesserver.exception.QuantidadeException;
import sdstore.businesserver.service.dto.CategoriaListDto;
import sdstore.businesserver.service.dto.ProdutoDto;
import sdstore.businesserver.service.dto.ProdutoListDto;

@WebService
@HandlerChain(file="handler-chain.xml")
public class PortalWebService {
	
	@WebMethod
	public String RetiraProduto(String codigo,Integer quantidade,String tx) throws ProdutoExistException, QuantidadeException,XAException{
		RetiraProdutoService service = new RetiraProdutoService(codigo,quantidade,tx);
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
	
	
	@WebMethod
	public String CanCommitService(String tx){
		CanCommitService service = new CanCommitService(tx);
		service.execute();
		String resultado = service.getResultado();
		return resultado;
	}
	
	@WebMethod
	public void AbortService(String tx){
		AbortService service = new AbortService(tx);
		service.execute();
	}
	
	@WebMethod
	public String CommitService(String tx){
		CommitService service = new CommitService(tx);
		service.execute();
		String resultado = service.getResultado();
		return resultado;
	}
}
