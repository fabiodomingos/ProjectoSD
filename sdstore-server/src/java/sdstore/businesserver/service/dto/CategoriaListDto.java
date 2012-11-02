package sdstore.businesserver.service.dto;

import java.util.List;

public class CategoriaListDto {
	
	private List<CategoriaDto> categoriaList;

	public CategoriaListDto(List<CategoriaDto> categoriaList) {
		this.categoriaList = categoriaList;
	}

	public List<CategoriaDto> getCategoriaList() {
		return categoriaList;
	}

	public void setCategoriaList(List<CategoriaDto> categoriaList) {
		this.categoriaList = categoriaList;
	}
	
	
	
	

}
