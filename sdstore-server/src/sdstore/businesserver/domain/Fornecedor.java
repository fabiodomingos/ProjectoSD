package sdstore.businesserver.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anacom.businessserver.domain.Mobile;
import anacom.businessserver.domain.Operator;
import anacom.businessserver.exception.MobileNumberException;
import anacom.businessserver.exception.OperatorMobileExistsException;
import anacom.businessserver.exception.OperatorMobileListException;
import anacom.businessserver.exception.OperatorPrefixException;

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
	
	public void registerProduto(Produto novo) throws FornecedorProdutoExistsException {
		if (produtoMap.get(nome) != null) {
			throw new FornecedorProdutoExistException(nome);
		}
		Produto prod = new Produto(novo.id, novo.descricao, novo.categoria);
		produtoMap.put(novo.id, prod);
	}
	
	public void unregisterProduto(String nome) throws ProdutoIdException {
		if (!produtoMap.containsKey(nome)) {
			throw new ProdutoIdException(number);
		}
		produtoMap.remove(nome);
	}
	
	public Produto getMobile(String nome) throws ProdutoIdException {
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