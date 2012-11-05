package sdstore.businesserver.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

import sdstore.businesserver.exception.CatalogoNameException;
import sdstore.businesserver.exception.CategoriaListException;
import sdstore.businesserver.exception.CategoriaNameException;
import sdstore.businesserver.exception.ClienteNomeException;
import sdstore.businesserver.exception.FornecedorNameException;
import sdstore.businesserver.exception.FornecedorProdutoExistsException;
import sdstore.businesserver.exception.ProdutoIdException;
import sdstore.businesserver.exception.ProdutoListException;
import sdstore.businesserver.service.dto.CatalogoDto;
import sdstore.businesserver.service.dto.CategoriaDto;
import sdstore.businesserver.service.dto.CategoriaListDto;
import sdstore.businesserver.service.dto.CategoriaSelectionDto;
import sdstore.businesserver.service.dto.ClienteSelectionDto;
import sdstore.businesserver.service.dto.FornecedorProdutoRegisterDto;
import sdstore.businesserver.service.dto.FornecedorSelectionDto;
import sdstore.businesserver.service.dto.ProdutoDto;
import sdstore.businesserver.service.dto.ProdutoListDto;
import sdstore.businesserver.service.dto.ProdutoSelectionDto;

@WebService
public class PortalWebService {
	
	@WebMethod
	public FornecedorProdutoRegisterDto AddProdutoWebService(FornecedorProdutoRegisterDto dto) throws FornecedorNameException{
		AddProdutoService service = new AddProdutoService(dto);
		service.execute();
		FornecedorProdutoRegisterDto result = service.getProduto();
		return result;
	}
	
//	@WebMethod
//	public ProdutoDto RemoveProdutoWebService(ProdutoSelectionDto dto){
//		RemoveProdutoService service = new RemoveProdutoService(dto);
//		service.execute();
//		ProdutoDto result = service.getProduto();
//		return result;
//	}
	
	@WebMethod
	public void JuntaWebService(ProdutoDto dto, Integer quantidade, ClienteSelectionDto cliente) throws ProdutoIdException, ClienteNomeException, FornecedorProdutoExistsException{
		JuntaService service = new JuntaService(dto,quantidade,cliente);
		service.execute();
		//ProdutoDto result = service.getProduto();
//		return result;
	}
	
	@WebMethod
	public ProdutoListDto ListaProdutoWebService(CategoriaDto dto, String nomeCatalogo) throws ProdutoListException, CatalogoNameException{
		ListaProdutoService service = new ListaProdutoService(dto, nomeCatalogo);
		service.execute();
		ProdutoListDto result = service.getListProduto();
		return result;
	}
	
	@WebMethod
	public CategoriaListDto ListaCategoriaWebService(FornecedorSelectionDto dto) throws CategoriaNameException, CategoriaListException{
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
	public void LimpaWebService(ClienteSelectionDto dto) throws ClienteNomeException{
		LimpaService service = new LimpaService(dto);
		service.execute();
	}
	
	@WebMethod
	public ProdutoDto EncomendaWebService(ProdutoSelectionDto dto){
		EncomendaService service = new EncomendaService(dto);
		service.execute();
		ProdutoDto result = service.getEncomenda();
		return result;
	}
	
	

}
