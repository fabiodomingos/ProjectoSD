package sdstore.businesserver.service.dto;

import java.util.Set;

public class CategoriaListDto {

	private Set<String> categoriaList;

	public CategoriaListDto(Set<String> listaCategoria) {
		this.categoriaList = listaCategoria;
	}
	
	public CategoriaListDto(){
		
	}

	public Set<String> getCategoriaList() {
		return categoriaList;
	}

	public void setCategoriaList(Set<String> categoriaList) {
		this.categoriaList = categoriaList;
	}
	
	
	
}
