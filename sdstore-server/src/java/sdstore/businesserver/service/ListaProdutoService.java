package sdstore.businesserver.service;

import java.util.ArrayList;
import java.util.List;

import sdstore.businesserver.domain.Catalogo;
import sdstore.businesserver.domain.Produto;
import sdstore.businesserver.exception.CategoriaNameException;
import sdstore.businesserver.exception.ProdutoListException;
import sdstore.businesserver.service.dto.ProdutoDto;
import sdstore.businesserver.service.dto.ProdutoListDto;

public class ListaProdutoService extends PortalService{

	private String _categoria;
	private ProdutoListDto result;
	
	public ListaProdutoService(String categoria){
		_categoria=categoria;
	}
	
	@Override
	public final void dispatch() throws ProdutoListException, CategoriaNameException{
		try{
			Integer controlo=0;
			List<Produto> listaProdutos = Catalogo.getProdutoList();
			List<ProdutoDto> listaNovaProdutos = new ArrayList<ProdutoDto>();		
			for(Produto prod : listaProdutos){
				if(prod.getCategoria().equals(_categoria)){
					ProdutoDto dto = new ProdutoDto();
					dto.setId(prod.getId());
					dto.setPreco(prod.getPreco());
					dto.setQuantidade(prod.getQuantidade());
					dto.setDescricao(prod.getDescricao());
					dto.setCategoria(prod.getCategoria());
					listaNovaProdutos.add(dto);
					controlo = 1;
				}
			}
			if(controlo==0){
				throw new CategoriaNameException(_categoria);
			}
			result = new ProdutoListDto(listaNovaProdutos);
		}catch(ProdutoListException e){
			throw e;
		}
	}
	
	public ProdutoListDto getListProdutos(){
		return this.result;
	}

}
