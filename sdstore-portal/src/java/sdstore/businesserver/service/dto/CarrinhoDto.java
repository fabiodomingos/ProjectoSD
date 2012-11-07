package sdstore.businesserver.service.dto;

import java.util.List;

public class CarrinhoDto {

	private List<ProdutoDto> listaDto;
	private Double totalPreco;
	
	public CarrinhoDto(){
		
	}
	
	public List<ProdutoDto> getListaDto() {
		return listaDto;
	}

	public void setListaDto(List<ProdutoDto> listaDto) {
		this.listaDto = listaDto;
	}

	public CarrinhoDto(List<ProdutoDto> listaDto){
		this.listaDto=listaDto;
	}

	public Double getTotalPreco() {
		return totalPreco;
	}

	public void setTotalPreco(Double totalPreco) {
		this.totalPreco = totalPreco;
	}
	
}
