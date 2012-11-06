package sdstore.businesserver.service.dto;

import java.util.List;

public class CategoriaListDto {

	private List<String> categoriaList;

	public CategoriaListDto(List<String> categoriaList) {
		this.categoriaList = categoriaList;
	}
	
	public CategoriaListDto(){
		
	}

	public List<String> getCategoriaList() {
		return categoriaList;
	}

	public void setCategoriaList(List<String> categoriaList) {
		this.categoriaList = categoriaList;
	}
	
	
	
}
