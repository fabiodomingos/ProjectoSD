package sdstore.businesserver.service;

import sdstore.businesserver.domain.Catalogo;
import sdstore.businesserver.exception.ProdutoExistException;
import sdstore.businesserver.exception.QuantidadeException;

public class RetiraProdutoService extends PortalService{

	private String _codigo;
	private Integer _quantidade;
	private String result;
	
	public RetiraProdutoService(String codigo,Integer quantidade){
		_codigo=codigo;
		_quantidade=quantidade;
	}

	@Override
	public final void dispatch() throws ProdutoExistException, QuantidadeException{
		try{
		result = Catalogo.retiraProduto(_codigo,_quantidade);
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
