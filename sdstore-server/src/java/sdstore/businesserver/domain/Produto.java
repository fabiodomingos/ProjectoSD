package sdstore.businesserver.domain;

import java.util.HashMap;
import java.util.Map;

import sdstore.businesserver.exception.ProdutoIdException;

public class Produto {
	
	private static Map<String, Produto> produtoMap;
	
	static {
		Produto.produtoMap = new HashMap<String, Produto>();
	}
	
	String id;
	String descricao;
	String categoria;
	Integer preco;
	Integer quantidade;
	
	public static void createProduto(String id, String descricao, String categoria) throws ProdutoIdException {
		Produto produto = Produto.produtoMap.get(id);
		if (produto != null) {
			throw new ProdutoIdException(id);
		}
		Produto newProduto = new Produto(id, descricao, categoria);
		Produto.produtoMap.put(id, newProduto);
	}
	
	public static Produto getProduto(String id) throws ProdutoIdException {
		Produto produto = Produto.produtoMap.get(id);
		if (produto == null) {
			throw new ProdutoIdException(id);
		}
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
	
	public Integer getPreco() {
		return preco;
	}
	
	public void setPreco(Integer prize) {
		this.preco = prize;
	}
}