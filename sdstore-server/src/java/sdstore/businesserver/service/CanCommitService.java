package sdstore.businesserver.service;



import sdstore.businesserver.domain.Catalogo;

public class CanCommitService extends PortalService {
	
	private String _xid;
	private String _resultado;
	
	public CanCommitService(String xid){
		_xid=xid;
	}
	
	@Override
	public void dispatch(){
		_resultado = Catalogo.prepare(_xid);
		
	}
	
	public String getResultado(){
		return this._resultado;
	}

}
