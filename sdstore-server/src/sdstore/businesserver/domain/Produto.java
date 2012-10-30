package sdstore.businesserver.domain;

public class Produto {
	
	String id;
	String descricao;
	String categoria;
	
	public Produto(String id, String descricao, String categoria) {
		this.id = id;
		this.descricao = descricao;
		this.categoria = categoria;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
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
	
}
