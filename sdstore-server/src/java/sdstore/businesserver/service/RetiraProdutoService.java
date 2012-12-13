package sdstore.businesserver.service;

import java.util.List;

import javax.transaction.xa.XAException;

import sdstore.businesserver.domain.Catalogo;
import sdstore.businesserver.exception.ProdutoExistException;
import sdstore.businesserver.exception.QuantidadeException;
import sdstore.businesserver.service.dto.ProdutoDto;

public class RetiraProdutoService extends PortalService{


	private List<ProdutoDto> _lista;
	private String result;
	private String _tx;
	
	public RetiraProdutoService(List<ProdutoDto> lista, String tx){
		_lista=lista;
		_tx=tx;
	}

	@Override
	public final void dispatch() throws ProdutoExistException, QuantidadeException{
		try{
		
		result = Catalogo.retiraProduto(_lista,_tx);
		}catch(ProdutoExistException e){

		}catch(QuantidadeException e){

		}
	}
	
	public String getResultado(){
		return this.result;
	}
	
}
