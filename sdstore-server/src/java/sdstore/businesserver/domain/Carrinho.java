package sdstore.businesserver.domain;

import java.util.HashMap;
import java.util.Map;

import sdstore.businesserver.exception.ClienteNomeException;
import sdstore.businesserver.exception.FornecedorProdutoExistsException;
import sdstore.businesserver.exception.ProdutoIdException;

public class Carrinho {

	private static Map<String, Carrinho> carrinhoMap;
	
	static {
		Carrinho.carrinhoMap = new HashMap<String, Carrinho>();
	}
	
	private String id_cliente;
	private Map<Integer, Produto> produtoMap;

	public static void createCarrinho(String cliente) throws ClienteNomeException{
		Carrinho carrinho = Carrinho.carrinhoMap.get(cliente);
		if(carrinho == null){
			throw new ClienteNomeException(cliente);
		}
		Carrinho newCarrinho = new Carrinho(cliente);
		Carrinho.carrinhoMap.put(cliente, newCarrinho);	
	}
	
	public void registerProduto(Produto novo, Integer quantidade) throws FornecedorProdutoExistsException, ProdutoIdException {
		if (produtoMap.get(novo.id) != null) {
			throw new FornecedorProdutoExistsException(novo.id);
		}	
//		duvidas se funca
		Produto c = Catalogo.getProduto(novo.getId());
		produtoMap.put(quantidade, c);
	}
	
	// DUVIDA SE FUNCA
	public void unregisterProduto(Produto velho) throws ProdutoIdException {
//		Produto prod = Carrinho.produtoMap.get(velho.getId());
		
		if (!produtoMap.containsKey(velho.getId())) {
			throw new ProdutoIdException(velho.getId());
		}
		
		produtoMap.remove(velho);
	}
	
	public static Carrinho getCarrinho(String cliente) throws ClienteNomeException{
		Carrinho carrinho = Carrinho.carrinhoMap.get(cliente);
		if (carrinho == null){
			throw new ClienteNomeException(cliente);
		}
		return Carrinho.carrinhoMap.get(cliente);
	}
	
	public Carrinho(String cliente) {
		this.id_cliente = cliente;
		carrinhoMap = new HashMap<String, Carrinho>();
	}

	public String getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(String id_cliente) {
		this.id_cliente = id_cliente;
	}
	
	
	
}
