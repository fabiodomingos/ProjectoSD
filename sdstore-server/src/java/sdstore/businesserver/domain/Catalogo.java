package sdstore.businesserver.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sdstore.businesserver.exception.FornecedorListException;
import sdstore.businesserver.exception.FornecedorProdutoListException;
import sdstore.businesserver.exception.ProdutoIdException;

public class Catalogo {
	
	private Double preco_portal;
	private Map<String, Produto> produtoMap;
	private Integer quantidade_total;
	
	
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
		produtoMap = new HashMap<String, Produto>();
	}
	
	
	public Produto getProduto(String nome) throws ProdutoIdException {
		if (!produtoMap.containsKey(nome)) {
			throw new ProdutoIdException(nome);
		}
		return produtoMap.get(nome);
	}
	
	public List<Produto> getProdutoListFornecedor(Fornecedor f) throws FornecedorListException, FornecedorProdutoListException {
		if(f.getFornecedorList().isEmpty()){
			throw new FornecedorListException();
		}
		else{
			return f.getProdutoList();
		}
	}
	
	public Integer getProdutoTotal(Produto prod){
		
		return null;
	}
}
