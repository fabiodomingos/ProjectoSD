package sdstore.businesserver.service.dto;

public class ProdutoTotalDto {

	private Integer quantidade;
	private String categoria;
	private String codigo;
	private String descricao;
	
	public ProdutoTotalDto(String categoria, String codigo, String descricao, Integer quantidade) {
		this.categoria = categoria;
		this.codigo = codigo;
		this.descricao = descricao;
		this.quantidade = quantidade;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
}
