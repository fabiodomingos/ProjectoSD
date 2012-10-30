package sdstore.businesserver.exception;

public class FornecedorProdutoExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nome;

	public FornecedorProdutoExistsException(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
