package sdstore.businesserver.service.dto;

public class FornecedorProdutoRegisterDto {
	
	private ProdutoSelectionDto produto;
	private Integer preco;
	private Integer total;

	public FornecedorProdutoRegisterDto(ProdutoSelectionDto produto, Integer preco, Integer totalProd) {
		this.produto = produto;
		this.preco = preco;
		this.total = totalProd;
	}

	public ProdutoSelectionDto getProduto() {
		return produto;
	}

	public void setProduto(ProdutoSelectionDto produto) {
		this.produto = produto;
	}

	public Integer getPreco() {
		return preco;
	}

	public void setPreco(Integer preco) {
		this.preco = preco;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
	
	

}
