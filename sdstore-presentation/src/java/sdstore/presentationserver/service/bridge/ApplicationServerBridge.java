package sdstore.presentationserver.service.bridge;

import sdstore.presentationserver.exception.ProdutoListException;
import sdstore.presentationserver.service.stubs.CarrinhoDto;
import sdstore.presentationserver.service.stubs.CategoriaListDto;
import sdstore.presentationserver.service.stubs.ProdListDto;
import sdstore.presentationserver.service.stubs.ProdutoListException_Exception;

public interface ApplicationServerBridge{

	public void Encomenda();
	
	public void Limpa();
	
	public void Junta(String codigo,Integer quantidade);
	
	public CarrinhoDto Carrinho();
	
	public ProdListDto listaProdutosCategoria(String categoria);
	
	public CategoriaListDto listaCategoria() throws ProdutoListException;
}
