package sdstore.businesserver.exception;

public class CatalogoNameException extends PortalException {

private String nome;
	
	public CatalogoNameException(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
