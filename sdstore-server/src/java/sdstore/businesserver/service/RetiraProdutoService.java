package sdstore.businesserver.service;

import sdstore.businesserver.domain.Catalogo;

public class RetiraProdutoService extends PortalService{

	private String _codigo;
	private Integer _quantidade;
	private String result;
	
	public RetiraProdutoService(String codigo,Integer quantidade){
		_codigo=codigo;
		_quantidade=quantidade;
	}

	@Override
	public final void dispatch() {
		result = Catalogo.retiraProduto(_codigo,_quantidade);
	}
	
	public String getResultado(){
		return this.result;
	}
	
}
