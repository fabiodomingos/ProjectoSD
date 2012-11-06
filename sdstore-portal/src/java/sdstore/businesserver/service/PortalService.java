package sdstrore.businesserver.service;

public abstract class PortalService {

	public abstract void dispatch();
		
	//@Atomic
	public void execute(){
		dispatch();
	}
}
