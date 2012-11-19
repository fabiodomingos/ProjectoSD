package sdstore.businesserver.service.dto;

import java.util.Set;

public class ProdutoListDto {

	private Set<ProdutoDto> listaDto;
	
	public ProdutoListDto(){
		
	}

	public ProdutoListDto(Set<ProdutoDto> listaNovaProdutos) {
		listaDto=listaNovaProdutos;
	}

	public Set<ProdutoDto> getListaDto() {
		return listaDto;
	}

	public void setListaDto(Set<ProdutoDto> listaDto) {
		this.listaDto = listaDto;
	}
	
}
