package sdstore.businesserver.exception;

public class ServiceBridgeFornecedorException extends PortalException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String webServiceMethod;

	public ServiceBridgeFornecedorException(String webServiceMethod) {
		this.webServiceMethod = webServiceMethod;
	}

	public String getWebServiceMethod() {
		return this.webServiceMethod;
	}

	public void setWebServiceMethod(String webServiceMethod) {
		this.webServiceMethod = webServiceMethod;
	}
	


}
