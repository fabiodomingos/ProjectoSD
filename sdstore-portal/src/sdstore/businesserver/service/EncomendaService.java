package sdstore.businesserver.service;

import java.util.ArrayList;
import java.util.List;

import sdstore.businesserver.domain.Cliente;
import sdstore.businesserver.domain.Produto;
import sdstore.businesserver.exception.ProdutoIdException;
import sdstore.businesserver.service.dto.FornecedorProdutoRegisterDto;
import sdstore.businesserver.service.dto.FornecedorSelectionDto;
import sdstore.businesserver.service.dto.ProdutoDto;
import sdstore.businesserver.service.dto.ProdutoListDto;

public class EncomendaService extends PortalService {
	
	private ProdutoListDto carrinho;
	private FornecedorProdutoRegisterDto produtoFornecedor;
	private FornecedorSelectionDto fornecedor;
	
	
	public EncomendaService(ProdutoListDto dto){
		this.carrinho = dto;
	}
	
	public final void dispatch() throws ProdutoIdException {
		try {			
			String nomeFornecedor = fornecedor.getNome();
			
			
			
		} catch (ProdutoIdException exception) {
			throw exception;
		}
	}

	public ProdutoListDto getEncomenda() {
		return carrinho;
	}

}
