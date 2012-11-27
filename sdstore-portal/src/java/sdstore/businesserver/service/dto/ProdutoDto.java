package sdstore.businesserver.service.dto;

public class ProdutoDto implements Comparable<ProdutoDto>{
	
	private String id;
	private Double preco;
	private Integer quantidade;
	private String descricao;
	private String categoria;
	private String fornecedor;
		
	public ProdutoDto(){
		
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Double getPreco() {
		return preco;
	}


	public void setPreco(Double preco) {
		this.preco = preco;
	}


	public Integer getQuantidade() {
		return quantidade;
	}


	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public String getCategoria() {
		return categoria;
	}


	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	@Override
	public int compareTo(ProdutoDto dto) {
		if(this.getPreco() < dto.getPreco()){
			return -1;
		}
		if(this.getPreco() > dto.getPreco()){
			return 1;
		}
		return 0;
	}

	public String getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}
}
