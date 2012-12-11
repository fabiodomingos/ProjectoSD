package sdstore.businesserver.service;

import javax.transaction.xa.XAException;

import sdstore.businesserver.exception.PortalException;

public abstract class PortalService {

public abstract void dispatch() throws PortalException;
	
	//@Atomic
	public void execute() throws PortalException{
		dispatch();
	}
}
