package sdstore.businesserver.exception;

public class ProdutoIdException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;

	public ProdutoIdException(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
		
}
