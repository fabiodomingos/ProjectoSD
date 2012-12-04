package sdstore.businesserver.service;

import javax.transaction.xa.XAException;

import sdstore.businesserver.domain.Catalogo;

public class CanCommitService extends PortalService {
	
	private String _xid;
	private String _codigo;
	private Integer _quantidade;
	private String _resultado;
	
	public CanCommitService(String xid, String codigo, Integer quantidade){
		_xid=xid;
		_codigo = codigo;
		_quantidade = quantidade;
		
	}
	
	@Override
	public void dispatch(){
		_resultado = Catalogo.prepare(_xid, _codigo, _quantidade);
		
	}
	
	public String getResultado(){
		return this._resultado;
	}

}
