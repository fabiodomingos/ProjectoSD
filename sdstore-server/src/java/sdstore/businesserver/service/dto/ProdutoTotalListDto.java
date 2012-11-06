package sdstore.businesserver.service.dto;

import java.util.List;

public class ProdutoTotalListDto {

	private List<ProdutoTotalDto> productList;

	public ProdutoTotalListDto(List<ProdutoTotalDto> productList) {
		this.productList = productList;
	}

	public List<ProdutoTotalDto> getProductList() {
		return productList;
	}

	public void setProductList(List<ProdutoTotalDto> produtList) {
		this.productList = produtList;
	}
	
}
