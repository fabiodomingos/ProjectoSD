package sdstore.businesserver.domain;

import java.util.HashMap;
import java.util.Map;

public class Carrinho {
	
	private String id_cliente;

	private static Map<Integer, Produto> carrinhoMap;
	
	public Carrinho() {
		carrinhoMap = new HashMap<Integer, Produto>();
	}

	public String getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(String id_cliente) {
		this.id_cliente = id_cliente;
	}
	
	
	
}
