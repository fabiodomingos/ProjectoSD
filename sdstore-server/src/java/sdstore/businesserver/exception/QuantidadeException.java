package sdstore.businesserver.exception;

public class QuantidadeException extends PortalException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer quantidade;

	public QuantidadeException(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	
	
	

}
