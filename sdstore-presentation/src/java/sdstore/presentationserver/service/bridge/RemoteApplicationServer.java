package sdstore.presentationserver.service.bridge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPFaultException;

import sdstore.presentationserver.exception.CategoriaNameException;
import sdstore.presentationserver.exception.ProdutoExistException;
import sdstore.presentationserver.exception.ProdutoListException;
import sdstore.presentationserver.exception.QuantidadeException;
//import sdstore.presentationserver.handler.Handler;
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

		}
		BindingProvider bp = (BindingProvider)webService;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);
		
//		handlers
		Binding binding = bp.getBinding();
		sdstore.presentationserver.handler.Handler handler = new sdstore.presentationserver.handler.Handler();
		List<Handler> handlerChain = new ArrayList<Handler>();
		handlerChain.add(handler);
		bp.getBinding().setHandlerChain(handlerChain);
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
	public void Junta(String codigo,Integer quantidade,String user) throws ProdutoListException,SOAPFaultException {
		try{
		updateEndpointUrl();
		webService.juntaCarrinho(codigo, quantidade, user);
		}catch(ProdutoListException_Exception e){
			throw new ProdutoListException();
		}
	}

	@Override
	public CarrinhoDto Carrinho(String user) throws SOAPFaultException{
		updateEndpointUrl();
		CarrinhoDto dtoCarrinho = webService.listaCarrinho(user);
		return dtoCarrinho;
	}
	
	@Override
	public void Limpa(String user){
		updateEndpointUrl();
		webService.limpaCarrinho(user);
	}
	
	@Override
	public void Encomenda(String user) throws ProdutoExistException, QuantidadeException, ProdutoListException {
		try{
		updateEndpointUrl();
		webService.encomenda(user);
		}catch(ProdutoExistException_Exception e){
			throw new ProdutoExistException(null);
		}catch(QuantidadeException_Exception e){
			throw new QuantidadeException(null);
		}catch(ProdutoListException_Exception e){
			throw new ProdutoListException();
			
		}
		
	}
	
	
}
