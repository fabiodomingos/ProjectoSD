package sdstore.businesserver.domain;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.xa.XAException;

import com.sleepycat.je.Transaction;

import sdstore.businesserver.BaseDados;
import sdstore.businesserver.exception.ProdutoExistException;
import sdstore.businesserver.exception.ProdutoListException;
import sdstore.businesserver.exception.QuantidadeException;
import sdstore.businesserver.service.dto.ProdutoDto;

public class Catalogo {
	
	private static Map<String, Catalogo> catalogoMap;
	
	private static BaseDados dados;
	
	static {
		Catalogo.catalogoMap = new HashMap<String, Catalogo>();
	}
	
	private String nome;
	private Double preco_portal;
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
		File dir = new File("/temp/"+name);
		dir.mkdir();
		dados=new BaseDados(dir);
	}
	
	public static BaseDados getDados(){
		return dados;
	}
	
	public static void createCatalogo(String name){
		Catalogo newCatalogo = new Catalogo(name, 0.1, 0);
		Catalogo.catalogoMap.put(name, newCatalogo);
	}

	public static Catalogo getCatalogo(String name)  {
		return Catalogo.catalogoMap.get(name);
	}
	
	
	public static Produto getProduto(String nome) throws ProdutoExistException{
		Produto p = dados.get(nome);
		return p;
	}
	
	public static List<Produto> getProdutoList() throws ProdutoListException{
		return dados.getCategoria();
	}

//	regista um produto no catalogo
	public void registaProduto(Produto p,Double preco,Integer quantidade) {
		p.setQuantidade(quantidade);
		p.setPreco(preco);
		dados.run2(p);
	}

	public static String retiraProduto(List<ProdutoDto> lista,String tx) throws ProdutoExistException, QuantidadeException{
		String resultado = " ";
		Integer controlo = 0;
		try {
//			inicia a transacao
			Transaction trans = dados.beginTransaction(tx);
			for(ProdutoDto prod : lista){
				Produto prod1 = dados.get(prod.getId());
//				vou verificar se tenho a quantidade e se existe a quantidade
				if(prod1.getId().equals(prod.getId())){
					controlo=1;
					if(prod1.getQuantidade()<prod.getQuantidade()){
						resultado = "NO";
						break;
					}else{
						prod1.setQuantidade(prod1.getQuantidade()-prod.getQuantidade());
////					se existir e tiver a quantidade certa vai fazer os puts
						dados.run(trans,prod1);
						resultado = "YES";
					}
				}
			}
//			retorna o resultado da cena
			return resultado;
		} catch (XAException e) {
			resultado = "NO";
			return resultado;
		}
	}
	
	
	public static String prepare(String xid){
		try {
			dados.preparar(xid);
			return "YES";
		} catch (XAException e) {
			return "NO";
		}
	}
	
	public static void abort(String xid){
		try{
		dados.abort(xid);
		}catch(XAException e){
			
		}
	}
	
	
	public static String commit(String xid){
		try{
			dados.commit(xid);
			return "YES";
		}catch(XAException e){
			return "NO";
		}
	}
	
}
