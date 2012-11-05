package sdstore.businesserver.service;

import java.util.ArrayList;
import java.util.List;

import sdstore.businesserver.domain.Catalogo;
import sdstore.businesserver.domain.Produto;
import sdstore.businesserver.exception.CatalogoNameException;
import sdstore.businesserver.exception.ProdutoListException;
import sdstore.businesserver.service.dto.CatalogoDto;
import sdstore.businesserver.service.dto.CategoriaDto;
import sdstore.businesserver.service.dto.ProdutoDto;
import sdstore.businesserver.service.dto.ProdutoListDto;


public class ListaProdutoService extends PortalService{

	CategoriaDto categoriaDto;
	CatalogoDto catalogoDto;
	ProdutoListDto result;
	
	public ListaProdutoService(CategoriaDto categoria, CatalogoDto dto) {
		this.categoriaDto = categoria;
		this.catalogoDto = dto;
	}

	@Override
	public final void dispatch() throws ProdutoListException, CatalogoNameException {
		try{
			String categoria = categoriaDto.getNome();
			String nomeCatalogo = catalogoDto.getNome();
			Catalogo cata = Catalogo.getCatalogo(nomeCatalogo);
			List<Produto> listaProdutosCategoria = cata.getProdutosCategoria(categoria);
			List<ProdutoDto> listaDto = new ArrayList<ProdutoDto>();
			for(Produto prod : listaProdutosCategoria){
				ProdutoDto aux = new ProdutoDto(prod.getCategoria(), prod.getId(), prod.getDescricao());
				listaDto.add(aux);
			}
			result = new ProdutoListDto(listaDto);
			
		}catch(ProdutoListException exception){
			throw exception;
		}catch(CatalogoNameException exception){
			throw exception;
		}
		
	}
	
	public ProdutoListDto getListProduto() {
		return this.result;
	}

}
