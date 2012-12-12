package sdstore.businesserver.domain;

import java.util.HashMap;
import java.util.Map;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class Produto {
	
	private static Map<String, Produto> produtoMap;
	
	static {
		Produto.produtoMap = new HashMap<String, Produto>();
	}
	
	@PrimaryKey
	String id;
	String descricao;
	String categoria;
	Double preco;
	Integer quantidade;
	
	public static void createProduto(String id, String descricao, String categoria) {
		Produto newProduto = new Produto(id, descricao, categoria);
		Produto.produtoMap.put(id, newProduto);
	}
	
	public static Produto getProduto(String id){
		return Produto.produtoMap.get(id);
	}
	
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
	
	public Integer getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(Integer quant) {
		this.quantidade = quant;
	}
	
	public Double getPreco() {
		return preco;
	}
	
	public void setPreco(Double prize) {
		this.preco = prize;
	}
	
	private Produto() {};
}