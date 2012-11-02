package sdstore.businesserver.service;

import sdstore.businesserver.domain.Fornecedor;
import sdstore.businesserver.domain.Produto;
import sdstore.businesserver.exception.FornecedorNameException;
import sdstore.businesserver.service.dto.FornecedorProdutoRegisterDto;
import sdstore.businesserver.service.dto.ProdutoDto;

public class RemoveProdutoService extends PortalService {

	private FornecedorProdutoRegisterDto produtoDto;
	
	public RemoveProdutoService(FornecedorProdutoRegisterDto dto){
		this.produtoDto = dto;
		
	}
	
	public final void dispatch() throws FornecedorNameException{
		try {			
			Integer total;
			String codigo = produtoDto.getProduto().getCodigo();
			String nomeFornecedor = produtoDto.getProduto().getFornecedor().getNome();
			Fornecedor forn = Fornecedor.getFornecedor(nomeFornecedor);
			Produto p = forn.getProduto(codigo);
			total = forn.getNumeroProdutos(p);
			forn.getProduto(codigo).setQuantidade(total-1);
			forn.unregisterProduto(codigo);
		}catch(FornecedorNameException e){
			throw e;
		}
	}
	
	public FornecedorProdutoRegisterDto getProduto() {
		return produtoDto;
	}

}
