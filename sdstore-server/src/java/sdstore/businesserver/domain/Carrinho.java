package sdstore.businesserver.domain;

import java.util.HashMap;
import java.util.Map;

import sdstore.businesserver.exception.ClienteNomeException;

public class Carrinho {

	private static Map<String, Carrinho> carrinhoMap;
	
	static {
		Carrinho.carrinhoMap = new HashMap<String, Carrinho>();
	}
	
	private String id_cliente;

	public static void createCarrinho(String cliente) throws ClienteNomeException{
		Carrinho carrinho = Carrinho.carrinhoMap.get(cliente);
		if(carrinho == null){
			throw new ClienteNomeException(cliente);
		}
		Carrinho newCarrinho = new Carrinho(cliente);
		Carrinho.carrinhoMap.put(cliente, newCarrinho);	
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
