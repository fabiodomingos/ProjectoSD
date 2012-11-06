package sdstore.businesserver.service.bridge;

import sdstore.stubs.CategoriaListDto;
import sdstore.stubs.ProdutoListDto;

public interface ApplicationServerBridge {

	public CategoriaListDto ListaCategoria(); 
	
	public ProdutoListDto ListaProduto(String categoria);
}
