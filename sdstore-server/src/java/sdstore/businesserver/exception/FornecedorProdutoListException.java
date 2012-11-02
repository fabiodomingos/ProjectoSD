package sdstore.businesserver.exception;

public class FornecedorProdutoListException extends PortalException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public FornecedorProdutoListException(String nome) {
		this.nome = nome;
	}
	public FornecedorProdutoListException(){
		super();
	}
}
