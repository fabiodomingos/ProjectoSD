package sdstore.businesserver.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sdstore.businesserver.exception.ProdutoListException;

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
	
	public static void createCatalogo(String name){
		Catalogo catalogo = Catalogo.catalogoMap.get(name);
		if (catalogo != null) {
//			throw new CatalogoNameException(name);
		}
		Catalogo newCatalogo = new Catalogo(name, 0.1, 0);
		Catalogo.catalogoMap.put(name, newCatalogo);
	}
//	
	public static Catalogo getCatalogo(String name)  {
		Catalogo catalogo = Catalogo.catalogoMap.get(name);
		if (catalogo == null) {
//			throw new CatalogoNameException(name);
		}
		return Catalogo.catalogoMap.get(name);
	}
	
	
	public static Produto getProduto(String nome){
		for(Produto p : produtoMap){
			if(p.getId().equals(nome))
				return p;
		}
//		throw new ProdutoIdException(nome);
		return null;
	}
	
	public static List<Produto> getProdutoList() throws ProdutoListException{
		if(produtoMap.isEmpty()){
			throw new ProdutoListException(null);
		}
		return produtoMap;

	}

//	regista um produto no catalogo
	public void registaProduto(Produto p,Double preco,Integer quantidade) {
		for(Produto prod : produtoMap){
			if(prod.getId().equals(p.getId())){
//				throw new FornecedorProdutoExistsException(p.getId());
			}
		}
		p.setPreco(preco);
		p.setQuantidade(quantidade);
		produtoMap.add(p);
	}

	public static String retiraProduto(String _codigo,Integer _quantidade) {
		String resultado = " ";
		for(Produto prod : produtoMap){
			if(prod.getId().equals(_codigo)){
				if(prod.getQuantidade()==0){
					resultado="KO";
				}else{
					prod.setQuantidade(prod.getQuantidade()-_quantidade);
					resultado="OK";
				}
			}
		}
		return resultado;
	}
	
	
}
