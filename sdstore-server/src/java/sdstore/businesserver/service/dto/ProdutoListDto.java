package sdstore.businesserver.service.dto;

import java.util.List;

public class ProdutoListDto {

	private List<ProdutoDto> listaDto;
	
	public ProdutoListDto(){
		
	}

	public ProdutoListDto(List<ProdutoDto> listaNovaProdutos) {
		// TODO Auto-generated constructor stub
	}

	public List<ProdutoDto> getListaDto() {
		return listaDto;
	}

	public void setListaDto(List<ProdutoDto> listaDto) {
		this.listaDto = listaDto;
	}
	
}
