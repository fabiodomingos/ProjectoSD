package sdstore.businesserver.service;

import sdstore.businesserver.domain.Carrinho;
import sdstore.businesserver.domain.Catalogo;
import sdstore.businesserver.domain.Produto;
import sdstore.businesserver.exception.ClienteNomeException;
import sdstore.businesserver.exception.FornecedorProdutoExistsException;
import sdstore.businesserver.exception.ProdutoIdException;
import sdstore.businesserver.service.dto.ClienteSelectionDto;
import sdstore.businesserver.service.dto.ProdutoDto;

public class JuntaService extends PortalService{

	private ProdutoDto codigoDto;
	private Integer quantidade;
	private ClienteSelectionDto clienteDto;
	
	public JuntaService(ProdutoDto codigo, Integer quantidade, ClienteSelectionDto cliente){
		this.codigoDto = codigo;
		this.quantidade = quantidade;
		this.clienteDto = cliente;
	}
	
	public final void dispatch() throws ProdutoIdException, ClienteNomeException, FornecedorProdutoExistsException{
		try{
			Carrinho carrinho = Carrinho.getCarrinho(clienteDto.getNome());
			Produto prod = Catalogo.getProduto(codigoDto.getCodigo());
			carrinho.registerProduto(prod, quantidade);
		}catch(ClienteNomeException exception){
			throw exception;
		}catch(ProdutoIdException exception){
			throw exception;
		}catch(FornecedorProdutoExistsException exception){
			throw exception;
		}
	}

}
