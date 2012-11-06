package sdstore.businesserver.exception;

public class ClienteNomeException extends PortalException{
	
private static final long serialVersionUID = 1L;
	
	private String nome;
	
	public ClienteNomeException(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
