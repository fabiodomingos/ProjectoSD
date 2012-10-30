package sdstore.businesserver.service.dto;

public class ProdutoSelectionDto {

	private String categoria;
	private String codigo;
	private String descricao;
	
	public ProdutoSelectionDto(String categoria, String codigo, String descricao) {
		this.categoria = categoria;
		this.codigo = codigo;
		this.descricao = descricao;
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
	
	
}
