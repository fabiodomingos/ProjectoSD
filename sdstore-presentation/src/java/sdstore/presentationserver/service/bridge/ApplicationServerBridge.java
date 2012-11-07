package sdstore.presentationserver.service.bridge;

import sdstore.presentationserver.service.stubs.CarrinhoDto;
import sdstore.presentationserver.service.stubs.CategoriaListDto;
import sdstore.presentationserver.service.stubs.ProdListDto;
import sdstore.presentationserver.service.stubs.ProdutoDto;

public interface ApplicationServerBridge{

//	public void Encomenda(ClienteSelectionDto dto);
	
//	public void Limpa(ClienteSelectionDto dto);
	
	public void Junta(String codigo,Integer quantidade);
	
	public CarrinhoDto Carrinho();
	
	public ProdListDto listaProdutosCategoria(String categoria);
	
	public CategoriaListDto listaCategoria();
}
