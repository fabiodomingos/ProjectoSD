package sdstore.businesserver.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sdstore.businesserver.exception.ClienteNomeException;
import sdstore.businesserver.exception.FornecedorListException;
import sdstore.businesserver.exception.FornecedorProdutoListException;
import sdstore.businesserver.exception.ProdutoIdException;

public class Cliente {
	
private static Map<String, Cliente> clienteMap;
	
	static {
		Cliente.clienteMap = new HashMap<String, Cliente>();
	}

	
	private Map<String, Produto> listaProdutosMap;
	String nome;
	
	public Cliente(String nome) {
		this.nome = nome;
		listaProdutosMap = new HashMap<String, Produto>();
	}
	
	public static void createCliente(String nome) throws ClienteNomeException {
		Cliente cliente = Cliente.clienteMap.get(nome);
		if (cliente != null) {
			throw new ClienteNomeException(nome);
		}
		Cliente newCliente = new Cliente(nome);
		Cliente.clienteMap.put(nome, newCliente);
	}
	
	public static Cliente getCliente(String nome) throws ClienteNomeException {
		Cliente cliente = Cliente.clienteMap.get(nome);
		if (cliente == null) {
			throw new ClienteNomeException(nome);
		}
		return Cliente.clienteMap.get(nome);
	}
	
	public Produto getProduto(String nome) throws ProdutoIdException {
		if (!listaProdutosMap.containsKey(nome)) {
			throw new ProdutoIdException(nome);
		}
		return listaProdutosMap.get(nome);
	}
	
	public List<Produto> getProdutoListCliente(){
		return new ArrayList<Produto>(listaProdutosMap.values());
	}
	
	public String getNome(){
		return nome;
	}
	
}
