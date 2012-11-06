package sdstore.businesserver.service;

import java.util.ArrayList;
import java.util.List;

import sdstore.businesserver.domain.Catalogo;
import sdstore.businesserver.domain.Produto;
import sdstore.businesserver.service.dto.ProdutoDto;
import sdstore.businesserver.service.dto.ProdutoListDto;

public class ListaProdutoService extends PortalService{

	private String _categoria;
	private ProdutoListDto result;
	
	public ListaProdutoService(String categoria){
		_categoria=categoria;
	}
	
	@Override
	public final void dispatch() {
		List<Produto> listaProdutos = Catalogo.getProdutoList();
		List<ProdutoDto> listaNovaProdutos = new ArrayList<ProdutoDto>();
		
		for(Produto prod : listaProdutos){
			if(prod.getId().equals(_categoria)){
				ProdutoDto dto = new ProdutoDto();
				dto.setId(prod.getId());
				dto.setPreco(prod.getPreco());
				dto.setQuantidade(prod.getQuantidade());
				dto.setDescricao(prod.getDescricao());
				dto.setCategoria(prod.getCategoria());
				listaNovaProdutos.add(dto);
//				listaNovaProdutos.add(prod);
			}
		}
		result = new ProdutoListDto(listaNovaProdutos);
	}
	
	public ProdutoListDto getListProdutos(){
		return this.result;
	}

}
