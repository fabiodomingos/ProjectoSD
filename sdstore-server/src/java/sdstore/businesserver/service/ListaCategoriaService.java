package sdstore.businesserver.service;

import java.util.ArrayList;
import java.util.List;

import sdstore.businesserver.domain.Catalogo;

import sdstore.businesserver.domain.Produto;
import sdstore.businesserver.exception.ProdutoListException;
import sdstore.businesserver.service.dto.CategoriaListDto;

public class ListaCategoriaService extends PortalService {
	
	CategoriaListDto result;
	
	public ListaCategoriaService(){
		
	}

	@Override
	public final void dispatch(){
			List<Produto> listaProdutos = Catalogo.getProdutoList();
			List<String> listaCategoria = new ArrayList<String>();
			
			for(Produto p : listaProdutos){
				listaCategoria.add(p.getCategoria());
			}
			result = new CategoriaListDto(listaCategoria);
	}
	
	public CategoriaListDto getListCategoria(){
		return this.result;
	}

}
