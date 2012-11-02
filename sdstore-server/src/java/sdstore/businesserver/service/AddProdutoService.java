package sdstore.businesserver.service;

import sdstore.businesserver.domain.Fornecedor;
import sdstore.businesserver.domain.Produto;
import sdstore.businesserver.exception.FornecedorNameException;
import sdstore.businesserver.service.dto.FornecedorProdutoRegisterDto;
import sdstore.businesserver.service.dto.ProdutoDto;

public class AddProdutoService extends PortalService{
	
	private FornecedorProdutoRegisterDto produtoDto;
	
	
	public AddProdutoService(FornecedorProdutoRegisterDto produto){
		this.produtoDto = produto;
	}
	
	public final void dispatch() throws FornecedorNameException{
		try {			
			String codigo = produtoDto.getNewProduto().getCodigo();
			String categoria = produtoDto.getNewProduto().getCategoria();
			String descricao = produtoDto.getNewProduto().getDescricao();
			String nomeFornecedor = produtoDto.getNewProduto().getFornecedor().getNome();
			Fornecedor forn = Fornecedor.getFornecedor(nomeFornecedor);
			Produto novo = new Produto(codigo, descricao, categoria);
			forn.registerProduto(novo, produtoDto.getPrizeProduto(), produtoDto.getTotalProduto());
		}catch(FornecedorNameException e){
			throw e;
		}
	}
	
	
	public FornecedorProdutoRegisterDto getProduto() {
		return produtoDto;
	}

}
