package sdstore.businesserver.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sdstore.businesserver.exception.FornecedorNameException;
import sdstore.businesserver.exception.FornecedorProdutoExistsException;
import sdstore.businesserver.exception.FornecedorProdutoListException;
import sdstore.businesserver.exception.ProdutoIdException;

public class Fornecedor {
	
	
	private static Map<String, Fornecedor> fornecedorMap;
	
	static {
		Fornecedor.fornecedorMap = new HashMap<String, Fornecedor>();
	}
	
	String nome;
	private Map<String, Produto> produtoMap;
	
	public static void createFornecedor(String name) throws FornecedorNameException {
		Fornecedor fornecedor = Fornecedor.fornecedorMap.get(name);
		if (fornecedor != null) {
			throw new FornecedorNameException(name);
		}
		Fornecedor newFornecedor = new Fornecedor(name);
		Fornecedor.fornecedorMap.put(name, newFornecedor);
	}
	
	public static Fornecedor getFornecedor(String name) throws FornecedorNameException {
		Fornecedor fornecedor = Fornecedor.fornecedorMap.get(name);
		if (fornecedor == null) {
			throw new FornecedorNameException(name);
		}
		return Fornecedor.fornecedorMap.get(name);
	}
	
	public Fornecedor(String name) {
		this.nome = name;
		produtoMap = new HashMap<String, Produto>();
	}
	
	public String getName() {
		return this.nome;
	}
	
	public void registerProduto(Produto novo, Integer valorProduto, Integer totProduto) throws FornecedorProdutoExistsException {
		if (produtoMap.get(novo.id) != null) {
			throw new FornecedorProdutoExistsException(novo.id);
		}
		Produto prod = new Produto(novo.id, novo.descricao, novo.categoria);
		prod.preco = valorProduto;
		prod.quantidade = totProduto;
		produtoMap.put(novo.id, prod);
	}
	
	public void unregisterProduto(String nome) throws ProdutoIdException {
		if (!produtoMap.containsKey(nome)) {
			throw new ProdutoIdException(nome);
		}
		produtoMap.remove(nome);
	}
	
	public Produto getProduto(String nome) throws ProdutoIdException {
		if (!produtoMap.containsKey(nome)) {
			throw new ProdutoIdException(nome);
		}
		return produtoMap.get(nome);
	}
	
	public List<Produto> getProdutoList() throws FornecedorProdutoListException {
		if (produtoMap.isEmpty()) {
			throw new FornecedorProdutoListException(this.nome);
		}
		return new ArrayList<Produto>(produtoMap.values());
	}
	
	
}