package sdstore.businesserver.service;

import java.util.ArrayList;
import java.util.List;

import anacom.businessserver.domain.Mobile;
import anacom.businessserver.domain.Operator;
import anacom.businessserver.exception.OperatorMobileListException;
import anacom.businessserver.exception.OperatorPrefixException;
import anacom.businessserver.service.dto.MobileBalanceDto;
import anacom.businessserver.service.dto.MobileSelectionDto;
import anacom.businessserver.service.dto.OperatorMobileListDto;
import sdstore.businesserver.domain.Cliente;
import sdstore.businesserver.domain.Produto;
import sdstore.businesserver.exception.ProdutoIdException;
import sdstore.businesserver.service.dto.ClienteSelectionDto;
import sdstore.businesserver.service.dto.ProdutoDto;
import sdstore.businesserver.service.dto.ProdutoListDto;
import sdstore.businesserver.service.dto.ProdutoSelectionDto;

public class CarrinhoService extends PortalService {
	
	private ProdutoSelectionDto produtoDto;
	private ProdutoListDto listaProdutos;
	private ClienteSelectionDto clienteDto;

	public CarrinhoService(ProdutoSelectionDto dto){
		this.produtoDto = dto;
	}
	
	public final void dispatch() throws ProdutoIdException {
		try {			
			String nomeCliente = clienteDto.getNome();
			Cliente cliente = Cliente.getCliente(nomeCliente);
			List<Produto> produtoList = cliente.getProdutoListCliente();
			List<ProdutoDto> lista = new ArrayList<ProdutoDto>();
			for (Produto produto: produtoList) {
				String nomeProduto = produto.getId();
				String categoria = produto.getCategoria();
				String descricao = produto.getDescricao();
				ProdutoDto produtoDto = new ProdutoDto(categoria, nomeProduto, descricao);
				lista.add(produtoDto);
			}
			listaProdutos = new ProdutoListDto(lista);
		} catch (ProdutoIdException exception) {
			throw exception;
		}
	}
	
	
	public ProdutoListDto getCarrinho() {
		return this.listaProdutos;
	}

}
