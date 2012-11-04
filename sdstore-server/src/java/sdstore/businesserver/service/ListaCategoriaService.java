package sdstore.businesserver.service;

import java.util.ArrayList;
import java.util.List;

import sdstore.businesserver.domain.Fornecedor;
import sdstore.businesserver.domain.Produto;
import sdstore.businesserver.exception.CategoriaListException;
import sdstore.businesserver.exception.CategoriaNameException;
import sdstore.businesserver.service.dto.CategoriaDto;
import sdstore.businesserver.service.dto.CategoriaListDto;
import sdstore.businesserver.service.dto.FornecedorSelectionDto;

public class ListaCategoriaService extends PortalService {
	
	CategoriaListDto result;
	FornecedorSelectionDto dto;
	
	
	public ListaCategoriaService(FornecedorSelectionDto dto) {
		this.dto = dto;
	}
	
	public final void dispatch() throws CategoriaNameException, CategoriaListException {
		try {		
			String nomeFornecedor = dto.getNome();
			String categoria;
			Fornecedor forn = Fornecedor.getFornecedor(nomeFornecedor);
			List<Produto> listaProdutosFornecedor = forn.getProdutoList();
			List<CategoriaDto> categoriaList = new ArrayList<CategoriaDto>();
			for(Produto prod : listaProdutosFornecedor){
				categoria = prod.getCategoria();
				CategoriaDto categoriaDto = new CategoriaDto(categoria);
				categoriaList.add(categoriaDto);
			}
			result = new CategoriaListDto(categoriaList);
		} catch (CategoriaNameException exception) {
			throw exception;
		} catch (CategoriaListException exception) {
			throw exception;
		}
	}



	public CategoriaListDto getListCategoria() {
		return this.result;
	}

}
