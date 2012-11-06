package sdstore.businesserver.service.dto;

public class ClienteSelectionDto {
	
	private String nome;
	
	public ClienteSelectionDto(String nome){
		this.nome = nome;
	}
	
	public String getNome(){
		return nome;
	}
	
	public void setNome(String nome){
		this.nome = nome;
	}

}
