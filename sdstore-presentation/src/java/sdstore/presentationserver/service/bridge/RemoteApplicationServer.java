package sdstore.presentationserver.service.bridge;

import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPFaultException;

import sdstore.presentationserver.exception.CategoriaNameException;
import sdstore.presentationserver.exception.ProdutoExistException;
import sdstore.presentationserver.exception.ProdutoListException;
import sdstore.presentationserver.exception.QuantidadeException;
import sdstore.presentationserver.service.stubs.CarrinhoDto;
import sdstore.presentationserver.service.stubs.CategoriaListDto;
import sdstore.presentationserver.service.stubs.CategoriaNameException_Exception;
import sdstore.presentationserver.service.stubs.ConsolaWebService;
import sdstore.presentationserver.service.stubs.ConsolaWebServiceService;
import sdstore.presentationserver.service.stubs.ProdListDto;
import sdstore.presentationserver.service.stubs.ProdutoExistException_Exception;
import sdstore.presentationserver.service.stubs.ProdutoListException_Exception;
import sdstore.presentationserver.service.stubs.QuantidadeException_Exception;

public class RemoteApplicationServer implements ApplicationServerBridge{

	private static Map<String,String> endpointUrlMap;
	
	static{
		RemoteApplicationServer.endpointUrlMap = new HashMap<String,String>();
		RemoteApplicationServer.endpointUrlMap.put("Portal", "http://localhost:8080/sdstore-portal/Portal?wsdl");
	}
	
	ConsolaWebService webService;
	{
		ConsolaWebServiceService servico = new ConsolaWebServiceService();
		webService = servico.getConsolaWebServicePort();
	}
	
	private void updateEndpointUrl(){
		String endpointUrl = RemoteApplicationServer.endpointUrlMap.get("Portal");
		if (endpointUrl == null) {
//			throw new OperatorPrefixException_Exception();
		}
		BindingProvider bp = (BindingProvider)webService;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);
	}

	@Override
	public CategoriaListDto listaCategoria() throws ProdutoListException,SOAPFaultException{
		try{
		updateEndpointUrl();
		CategoriaListDto resultado = webService.listaCategoriaWebService();
		return resultado;
		}catch(ProdutoListException_Exception e){
			throw new ProdutoListException();
		}
	}

	@Override
	public ProdListDto listaProdutosCategoria(String categoria) throws ProdutoListException, CategoriaNameException,SOAPFaultException {
		try{
		updateEndpointUrl();
		ProdListDto resultado = webService.getlistaProdutos(categoria);
		return resultado;
		}catch(ProdutoListException_Exception e){
			throw new ProdutoListException();
		}catch(CategoriaNameException_Exception e){
			throw new CategoriaNameException(categoria);
		}
	}

	@Override
	public void Junta(String codigo,Integer quantidade) throws ProdutoExistException,SOAPFaultException {
		try{
		updateEndpointUrl();
		webService.juntaCarrinho(codigo, quantidade);
		}catch(ProdutoExistException_Exception e){
			throw new ProdutoExistException(codigo);
		}
	}

	@Override
	public CarrinhoDto Carrinho() throws SOAPFaultException{
		updateEndpointUrl();
		CarrinhoDto dtoCarrinho = webService.listaCarrinho();
		return dtoCarrinho;
	}
	
	@Override
	public void Limpa(){
		updateEndpointUrl();
		webService.limpaCarrinho();
	}
	
	@Override
	public void Encomenda() throws ProdutoExistException, QuantidadeException,SOAPFaultException {
		try{
		updateEndpointUrl();
		webService.encomenda();
		}catch(ProdutoExistException_Exception e){
			throw new ProdutoExistException(null);
		}catch(QuantidadeException_Exception e){
			throw new QuantidadeException(null);
		}
		
	}
	
	
}
