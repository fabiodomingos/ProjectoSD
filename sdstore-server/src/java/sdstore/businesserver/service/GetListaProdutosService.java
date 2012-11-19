package sdstore.businesserver.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sdstore.businesserver.domain.Catalogo;
import sdstore.businesserver.domain.Produto;
import sdstore.businesserver.exception.ProdutoListException;
import sdstore.businesserver.service.dto.ProdutoDto;
import sdstore.businesserver.service.dto.ProdutoListDto;

public class GetListaProdutosService extends PortalService{

	private ProdutoListDto result;
	
	@Override
	public void dispatch() throws ProdutoListException {
		try{
			List<Produto> listaProdutos = Catalogo.getProdutoList();
			Set<ProdutoDto> ProdutosFornecedor = new HashSet<ProdutoDto>();
			for(Produto prod : listaProdutos){
				ProdutoDto dto = new ProdutoDto();
				dto.setId(prod.getId());
				dto.setPreco(prod.getPreco());
				dto.setQuantidade(prod.getQuantidade());
				dto.setDescricao(prod.getDescricao());
				dto.setCategoria(prod.getCategoria());
				ProdutosFornecedor.add(dto);
			}
			
			result= new ProdutoListDto(ProdutosFornecedor);
			
		}catch(ProdutoListException e){
			throw e;
		}
	}

	public ProdutoListDto getListaProdutos() {
		return this.result;
	}

}
