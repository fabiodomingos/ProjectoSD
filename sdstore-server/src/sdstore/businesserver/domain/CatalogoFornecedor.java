package sdstore.businesserver.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sdstore.businesserver.exception.ProdutoIdException;

public class CatalogoFornecedor {
	
	private Map<String, Produto> produtoMap;
	
	
	public CatalogoFornecedor() {
		produtoMap = new HashMap<String, Produto>();
	}
	
	public List<Produto> getProdutoList() throws FornecedorProdutoListException {
		if (produtoMap.isEmpty()) {
			throw new FornecedorProdutoListException(this.nome);
		}
		return new ArrayList<Produto>(produtoMap.values());
	}
	
	public Integer numeroProduto(Produto prod) throws ProdutoIdException{
		
		Integer resultado=0;
		
		if(!(produtoMap.containsValue(prod))){
			throw new ProdutoIdException(prod.getId());
		}
		else{
			for(Produto aux : produtoMap){
				if(aux.getId().equals(prod.getId())){
					resultado++;
				}
			}
		}
	}

}
