package sdstore.businesserver.service.dto;

import java.util.List;

public class ProdutoListDto {

	private List<ProdutoDto> productList;

	public ProdutoListDto(List<ProdutoDto> productList) {
		this.productList = productList;
	}

	public List<ProdutoDto> getProductList() {
		return productList;
	}

	public void setProductList(List<ProdutoDto> produtList) {
		this.productList = produtList;
	}

	
}
