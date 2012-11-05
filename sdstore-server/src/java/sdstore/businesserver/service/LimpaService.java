package sdstore.businesserver.service;

import sdstore.businesserver.domain.Carrinho;
import sdstore.businesserver.exception.ClienteNomeException;
import sdstore.businesserver.service.dto.ClienteSelectionDto;

public class LimpaService extends PortalService{
	
	private ClienteSelectionDto clienteDto;

	public LimpaService(ClienteSelectionDto cliente){
		this.clienteDto = cliente;
	}

	@Override
	public final void dispatch() throws ClienteNomeException {
		try{
			Carrinho carrinho = Carrinho.getCarrinho(clienteDto.getNome());
			carrinho.limpa(clienteDto.getNome());
		}catch(ClienteNomeException exception){
			throw exception;
		}
	}

}
