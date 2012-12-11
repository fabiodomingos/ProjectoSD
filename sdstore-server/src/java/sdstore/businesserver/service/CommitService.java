package sdstore.businesserver.service;

import sdstore.businesserver.domain.Catalogo;

public class CommitService extends PortalService{

	private String _xid;
	private String _resultado;
	
	public CommitService(String xid){
		_xid=xid;
	}
	
	@Override
	public void dispatch(){
		_resultado = Catalogo.commit(_xid);
	}
	public String getResultado(){
		return this._resultado;
	}
	
}
