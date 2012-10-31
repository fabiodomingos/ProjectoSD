package sdstore.businesserver.domain;

public class Catalogo {
	
	Double preco_portal;
	
	public Double getPreco_portal() {
		return preco_portal;
	}
	
	public void setPreco_portal(Double preco_portal) {
		this.preco_portal = preco_portal;
	}
	
	public Integer getQuantidade_total() {
		return quantidade_total;
	}
	
	public void setQuantidade_total(Integer quantidade_total) {
		this.quantidade_total = quantidade_total;
	}
	
	public Catalogo(Double preco_portal, Integer quantidade_total) {
		super();
		this.preco_portal = preco_portal;
		this.quantidade_total = quantidade_total;
	}
	
	
	
}
