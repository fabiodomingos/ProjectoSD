package sdstore.businesserver.exception;

public class ProdutoListException extends PortalException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String codigo;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public ProdutoListException(String codigo) {
		super();
		this.codigo = codigo;
	} 
	
	

	
}
