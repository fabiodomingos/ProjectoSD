package sdstore.businesserver.exception;

public class ProdutoChangePriceException extends PortalException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String price;
	public ProdutoChangePriceException(String id, String price) {
		this.id = id;
		this.price = price;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}

}
