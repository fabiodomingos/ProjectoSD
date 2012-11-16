package sdstore.presentationserver.exception;

public class ProdutoExistException extends PortalException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;

	public ProdutoExistException(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	

	
}
