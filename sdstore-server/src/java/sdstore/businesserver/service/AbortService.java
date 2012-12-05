package sdstore.businesserver.service;

import sdstore.businesserver.domain.Catalogo;
import sdstore.businesserver.exception.PortalException;

public class AbortService extends PortalService{
	
	private String _tx;
	
	public AbortService(String _tx) {
		this._tx = _tx;
	}



	@Override
	public void dispatch() throws PortalException {
		Catalogo.abort(_tx);
		
	}
	

}
