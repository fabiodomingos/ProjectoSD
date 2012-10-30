package sdstore.businesserver.exception;

public class ProdutoUnvaliableException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;

	public ProdutoUnvaliableException(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
