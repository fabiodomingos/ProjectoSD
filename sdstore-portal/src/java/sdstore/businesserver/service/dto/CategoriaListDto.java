package sdstore.businesserver.service.dto;

import java.util.List;
import java.util.Set;

public class CategoriaListDto {

	private Set<String> categoriaList;

	public CategoriaListDto(Set<String> categoriaList) {
		this.categoriaList = categoriaList;
	}
	
	public CategoriaListDto(){
		
	}

	public Set<String> getCategoriaList() {
		return categoriaList;
	}

	public void setCategoriaList(Set<String> listaCategoriasPortal) {
		this.categoriaList = listaCategoriasPortal;
	}
	
	
	
}
