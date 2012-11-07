package sdstore.businesserver.service.dto;

import java.util.List;

public class ProdListDto {

private List<ProdutoDto> listaDto;
	
	public ProdListDto(){
		
	}

//	public ProdListDto(List<ProdutoDto> listaNovaProdutos) {
//		
//	}

	public List<ProdutoDto> getListaDto() {
		return listaDto;
	}

	public ProdListDto(List<ProdutoDto> listaDto) {
		this.listaDto = listaDto;
	}

	public void setListaDto(List<ProdutoDto> listaDto) {
		this.listaDto = listaDto;
	}
}
