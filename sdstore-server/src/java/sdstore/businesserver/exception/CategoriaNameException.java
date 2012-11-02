package sdstore.businesserver.exception;

public class CategoriaNameException extends PortalException{
	
private static final long serialVersionUID = 1L;
	
	private String nome;
	
	public CategoriaNameException(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
