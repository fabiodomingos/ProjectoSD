package sdstore.presentationserver.service.bridge;

import sdstore.presentationserver.service.stubs.CategoriaListDto;
import sdstore.presentationserver.service.stubs.ProdListDto;

public interface ApplicationServerBridge{

//	public void Encomenda(ClienteSelectionDto dto);
	
//	public void Limpa(ClienteSelectionDto dto);
	
//	public void Junta(ProdutoSelectDto dto);
	
//	public ProdutoListDto Carrinho(ClienteSelectionDto dto);
	
	public ProdListDto listaProdutosCategoria(String categoria);
	
	public CategoriaListDto listaCategoria();
}
