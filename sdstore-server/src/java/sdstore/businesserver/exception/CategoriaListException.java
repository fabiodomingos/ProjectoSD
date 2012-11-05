package sdstore.businesserver.exception;

public class CategoriaListException extends PortalException{
	
private static final long serialVersionUID = 1L;
	
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public CategoriaListException(String nome) {
		this.nome = nome;
	}

}
