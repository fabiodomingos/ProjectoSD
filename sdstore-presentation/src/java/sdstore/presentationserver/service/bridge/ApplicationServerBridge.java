package sdstore.presentationserver.service.bridge;

import javax.xml.ws.soap.SOAPFaultException;

import sdstore.presentationserver.exception.CategoriaNameException;
import sdstore.presentationserver.exception.ProdutoExistException;
import sdstore.presentationserver.exception.ProdutoListException;
import sdstore.presentationserver.exception.QuantidadeException;
import sdstore.presentationserver.service.stubs.CarrinhoDto;
import sdstore.presentationserver.service.stubs.CategoriaListDto;
import sdstore.presentationserver.service.stubs.ProdListDto;
import sdstore.presentationserver.service.stubs.ProdutoListException_Exception;

public interface ApplicationServerBridge{

	public void Encomenda(String user) throws ProdutoExistException, QuantidadeException;
	
	public void Limpa(String user);
	
	public void Junta(String codigo,Integer quantidade,String user) throws ProdutoListException, SOAPFaultException;
	
	public CarrinhoDto Carrinho(String user);
	
	public ProdListDto listaProdutosCategoria(String categoria) throws ProdutoListException, CategoriaNameException;
	
	public CategoriaListDto listaCategoria() throws ProdutoListException;
}
