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
import sdstore.businesserver.service.dto.ProdutoTotalDto;
import sdstore.businesserver.service.dto.ProdutoTotalListDto;


public class ListaProdutoService extends PortalService{

	String nomeCatalogo;
	CategoriaDto categoriaDto;
	ProdutoTotalListDto result;
	
	public ListaProdutoService(CategoriaDto categoria, String catalogo) {
		this.categoriaDto = categoria;
		this.nomeCatalogo = catalogo;
	}

	@Override
	public final void dispatch() throws ProdutoListException, CatalogoNameException {
		try{
			String categoria = categoriaDto.getNome();
			//String nomeCatalogo = catalogoDto.getNome();
			Catalogo cata = Catalogo.getCatalogo(nomeCatalogo);
			List<Produto> listaProdutosCategoria = cata.getProdutosCategoria(categoria);
			List<ProdutoTotalDto> listaDto = new ArrayList<ProdutoTotalDto>();
			for(Produto prod : listaProdutosCategoria){
				Integer totalProd = cata.getTotalProdutosCatalogo(prod);
				ProdutoTotalDto aux = new ProdutoTotalDto(prod.getCategoria(), prod.getId(), prod.getDescricao(), totalProd);
				listaDto.add(aux);
			}
			result = new ProdutoTotalListDto(listaDto);
			
		}catch(ProdutoListException exception){
			throw exception;
		}catch(CatalogoNameException exception){
			throw exception;
		}
		
	}
	
	public ProdutoTotalListDto getListProduto() {
		return this.result;
	}

}
