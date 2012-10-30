package sdstore.businesserver.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

import sdstore.businesserver.service.dto.CategoriaListDto;
import sdstore.businesserver.service.dto.CategoriaSelectionDto;
import sdstore.businesserver.service.dto.ClienteSelectionDto;
import sdstore.businesserver.service.dto.ProdutoDto;
import sdstore.businesserver.service.dto.ProdutoListDto;
import sdstore.businesserver.service.dto.ProdutoSelectionDto;

@WebService
public class PortalWebService {
	
	@WebMethod
	public ProdutoDto AddProdutoWebService(ProdutoSelectionDto dto){
		AddProdutoService service = new AddProdutoService(dto);
		service.execute();
		ProdutoDto result = service.getProduto();
		return result;
	}
	
	@WebMethod
	public ProdutoDto RemoveProdutoWebService(ProdutoSelectionDto dto){
		RemoveProdutoService service = new RemoveProdutoService(dto);
		service.execute();
		ProdutoDto result = service.getProduto();
		return result;
	}
	
	@WebMethod
	public ProdutoDto JuntaWebService(ProdutoSelectionDto dto){
		JuntaService service = new JuntaService(dto);
		service.execute();
		ProdutoDto result = service.getProduto();
		return result;
	}
	
	@WebMethod
	public ProdutoListDto ListaProdutoWebService(ProdutoSelectionDto dto){
		ListaProdutoService service = new ListaProdutoService(dto);
		service.execute();
		ProdutoListDto result = service.getListProduto();
		return result;
	}
	
	@WebMethod
	public CategoriaListDto ListaCategoriaWebService(CategoriaSelectionDto dto){
		ListaCategoriaService service = new ListaCategoriaService(dto);
		service.execute();
		CategoriaListDto result = service.getListCategoria();
		return result;	
	}
	
	@WebMethod
	public ProdutoListDto CarrinhoWebService(ClienteSelectionDto dto){
		CarrinhoService service = new CarrinhoService(dto);
		service.execute();
		ProdutoListDto result = service.getCarrinho();
		return result;
	}
	
	@WebMethod
	public ProdutoDto LimpaWebService(ClienteSelectionDto dto){
		LimpaService service = new LimpaService(dto);
		service.execute();
		ProdutoDto result = service.getLimpeza();
		return result;
	}
	
	@WebMethod
	public ProdutoDto EncomendaWebService(ProdutoSelectionDto dto){
		EncomendaService service = new EncomendaService(dto);
		service.execute();
		ProdutoDto result = service.getEncomenda();
		return result;
	}
	
	

}
