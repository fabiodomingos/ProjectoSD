package sdstore.businesserver.service.dto;

import java.util.List;
import java.util.Set;

public class ProdListDto {

private Set<ProdutoDto> listaDto;
	
	public ProdListDto(){
		
	}


	public Set<ProdutoDto> getListaDto() {
		return listaDto;
	}

	public ProdListDto(Set<ProdutoDto> listaDto) {
		this.listaDto = listaDto;
	}

	public void setListaDto(Set<ProdutoDto> listaDto) {
		this.listaDto = listaDto;
	}
}
