package sdstore.businesserver.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sdstore.businesserver.exception.CatalogoNameException;
import sdstore.businesserver.exception.CategoriaNameException;
import sdstore.businesserver.exception.FornecedorListException;
import sdstore.businesserver.exception.FornecedorNameException;
import sdstore.businesserver.exception.FornecedorProdutoListException;
import sdstore.businesserver.exception.ProdutoIdException;
import sdstore.businesserver.exception.ProdutoListException;
import sdstore.businesserver.service.dto.CategoriaDto;

public class Catalogo {
	
	private static Map<String, Catalogo> catalogoMap;
	
	static {
		Catalogo.catalogoMap = new HashMap<String, Catalogo>();
	}
	
	private String nome;
	private Double preco_portal;
	private static List<Produto> produtoMap;
	private Integer quantidade_total;
	
	
	public String getNome(){
		return nome;	
	}
	
	public void setPreco(String nome){
		this.nome = nome;
	}
	
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
	
	public Catalogo(String name, Double preco_portal, Integer quantidade_total) {
		super();
		this.nome = name;
		this.preco_portal = preco_portal;
		this.quantidade_total = quantidade_total;
		produtoMap = new ArrayList<Produto>();
	}
	
	public static void createCatalogo(String name) throws CatalogoNameException {
		Catalogo catalogo = Catalogo.catalogoMap.get(name);
		if (catalogo != null) {
			throw new CatalogoNameException(name);
		}
		Catalogo newCatalogo = new Catalogo(name, 0.1, 0);
		Catalogo.catalogoMap.put(name, newCatalogo);
	}
	
	public static Catalogo getCatalogo(String name) throws CatalogoNameException {
		Catalogo catalogo = Catalogo.catalogoMap.get(name);
		if (catalogo == null) {
			throw new CatalogoNameException(name);
		}
		return Catalogo.catalogoMap.get(name);
	}
	
	
	public static Produto getProduto(String nome) throws ProdutoIdException {
		for(Produto p : produtoMap){
			if(p.getId().equals(nome))
				return p;
		}
		throw new ProdutoIdException(nome);
	}
	
	public List<Produto> getProdutosCategoria(String categoria) throws CategoriaNameException {
		List<Produto> produtoList = new ArrayList<Produto>();
		for(Produto p : produtoMap){
			if(p.getCategoria().equals(categoria)){
				produtoList.add(p);
			}else{
				throw new CategoriaNameException(categoria);
			}
		}
		return produtoList;
	}
	
	public List<Produto> getProdutoList() throws ProdutoListException{
		if(produtoMap.isEmpty()){
			throw new ProdutoListException(null);
		}
		return produtoMap;

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
		
		Integer totalProd=0;
		return totalProd;
	}
	
}
