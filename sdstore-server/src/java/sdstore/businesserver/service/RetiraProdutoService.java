package sdstore.businesserver.service;

import javax.transaction.xa.XAException;

import sdstore.businesserver.domain.Catalogo;
import sdstore.businesserver.exception.ProdutoExistException;
import sdstore.businesserver.exception.QuantidadeException;

public class RetiraProdutoService extends PortalService{

	private String _codigo;
	private Integer _quantidade;
	private String result;
	private String _tx;
	
	public RetiraProdutoService(String codigo,Integer quantidade, String tx){
		_codigo=codigo;
		_quantidade=quantidade;
		_tx=tx;
	}

	@Override
	public final void dispatch() throws ProdutoExistException, QuantidadeException{
		try{
		
		result = Catalogo.retiraProduto(_codigo,_quantidade,_tx);
//		Catalogo.commit(_tx);
		}catch(ProdutoExistException e){
			throw new ProdutoExistException(_codigo);
		}catch(QuantidadeException e){
			throw new QuantidadeException(_quantidade);
		}
	}
	
	public String getResultado(){
		return this.result;
	}
	
}
