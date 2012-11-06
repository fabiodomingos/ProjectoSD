package sdstore.businesserver.exception;

public class CarrinhoProdutoListException extends PortalException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id_cliente;
	
	public CarrinhoProdutoListException(String id_cliente) {
		this.id_cliente = id_cliente;
	}
	
	public String getId_cliente() {
		return id_cliente;
	}
	
	public void setId_cliente(String id_cliente) {
		this.id_cliente = id_cliente;
	}
	
	public CarrinhoProdutoListException(){
		super();
	}

}
