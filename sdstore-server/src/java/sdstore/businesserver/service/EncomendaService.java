package sdstore.businesserver.service;

import java.util.ArrayList;
import java.util.List;

import sdstore.businesserver.domain.Carrinho;
import sdstore.businesserver.domain.Cliente;
import sdstore.businesserver.domain.Fornecedor;
import sdstore.businesserver.domain.Produto;
import sdstore.businesserver.exception.ClienteNomeException;
import sdstore.businesserver.exception.ProdutoIdException;
import sdstore.businesserver.service.dto.ClienteSelectionDto;
import sdstore.businesserver.service.dto.FornecedorProdutoRegisterDto;
import sdstore.businesserver.service.dto.FornecedorSelectionDto;
import sdstore.businesserver.service.dto.ProdutoDto;
import sdstore.businesserver.service.dto.ProdutoListDto;

public class EncomendaService extends PortalService {
	
//	private ProdutoListDto carrinhoDto;
//	private FornecedorProdutoRegisterDto produtoFornecedorDto;
	private FornecedorSelectionDto fornecedorDto;
	private ClienteSelectionDto clienteDto;
	
	public EncomendaService(ClienteSelectionDto dto){
		this.clienteDto = dto;
	}
	
	public final void dispatch() throws ClienteNomeException {
		try {			
			Carrinho carrinho = Carrinho.getCarrinho(clienteDto.getNome());
			Fornecedor fornecedor = Fornecedor.getFornecedor(fornecedorDto.getNome());
			List<Produto> listaCarrinho = carrinho.getCarrinhoProdList();
			List<Fornecedor> listaFornecedores = fornecedor.getFornecedorList();
			List<Produto> listaProdFornecedores = fornecedor.getProdutoList();
			for(Produto prod: listaCarrinho){
				for(Fornecedor f : listaFornecedores){
					Integer control=0;
					for(Produto prodForn : listaProdFornecedores){
						if(prod.getId().equals(prodForn.getId())){
							control=1;
							if(prod.getPreco()>=prodForn.getPreco()){
								
							}
							else{
								
							}
						}
						//mandar exception de n‹o existir produto
					}
				}
			}
		
		} catch (ClienteNomeException exception) {
			throw exception;
		}
//		} catch(ProdutoIdException e){
//			throw e;
//		}
	}

	public ProdutoListDto getEncomenda() {
		return carrinho;
	}

}
