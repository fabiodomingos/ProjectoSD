package sdstore.businesserver.service;


import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.xml.registry.BulkResponse;
import javax.xml.registry.BusinessQueryManager;
import javax.xml.registry.Connection;
import javax.xml.registry.ConnectionFactory;
import javax.xml.registry.RegistryService;
import javax.xml.registry.infomodel.Organization;
import javax.xml.registry.infomodel.Service;
import javax.xml.registry.infomodel.ServiceBinding;
import javax.xml.ws.BindingProvider;

import sdstore.businesserver.exception.CategoriaNameException;
import sdstore.businesserver.exception.ProdutoExistException;
import sdstore.businesserver.exception.ProdutoListException;
import sdstore.businesserver.exception.QuantidadeException;
import sdstore.businesserver.service.dto.CarrinhoDto;
import sdstore.businesserver.service.dto.CategoriaListDto;
import sdstore.businesserver.service.dto.ProdListDto;
import sdstore.businesserver.service.dto.ProdutoDto;
import sdstore.stubs.PortalWebService;
import sdstore.stubs.PortalWebServiceService;
import sdstore.stubs.ProdutoExistException_Exception;
import sdstore.stubs.ProdutoListException_Exception;
import sdstore.stubs.QuantidadeException_Exception;

@WebService
public class ConsolaWebService {
	
	private static Map<String, String> endpointUrlMap;
	private Set<String> enderecos = new HashSet<String>();
//	carrinho de compras do cliente
	private List<ProdutoDto> carrinhoCompras = new ArrayList<ProdutoDto>();
	private Set<String> listaCategoriasPortal = new HashSet<String>();
	private Set<ProdutoDto> listaProdutosPortal = new HashSet<ProdutoDto>();
	private Set<ProdutoDto> listaProdutosCliente = new HashSet<ProdutoDto>();
	
	static{
//		ConsolaWebService.endpointUrlMap = new HashMap<String, String>();
//		ConsolaWebService.endpointUrlMap.put("fornecedor1", "http://localhost:8080/sdstore-server-fornecedor1/BusinessServerFornecedor1");
//		ConsolaWebService.endpointUrlMap.put("fornecedor2", "http://localhost:8080/sdstore-server-fornecedor2/BusinessServerFornecedor2");
	}
	
	PortalWebService webService;
	
//	{
//		PortalWebServiceService service = new PortalWebServiceService();
//		webService = service.getPortalWebServicePort();
//	}
	
	private void updateEndpointUrl(String fornecedorName){
		try{
			InitialContext context = new InitialContext();
			ConnectionFactory connFactory = (ConnectionFactory) context.lookup("java:jboss/jaxr/ConnectionFactory");
			
			////////////////////////////////////////
			////// LIGACAO AO UDDI REGISTRY ////////
			////////////////////////////////////////

			Properties props = new Properties();
			// Localização do ficheiro de configuração da ligação,
			// que deve estar na directoria WEB-INF/classes do .war
			props.setProperty("scout.juddi.client.config.file", "uddi.xml");
			// URL para pesquisas ao UDDI registry
			props.setProperty("javax.xml.registry.queryManagerURL", "http://localhost:8081/juddiv3/services/inquiry");
			// URL para publicar dados no UDDI registry
			props.setProperty("javax.xml.registry.lifeCycleManagerURL", "http://localhost:8081/juddiv3/services/publish");
			// URL do gestor de segurança do UDDI registry
			props.setProperty("javax.xml.registry.securityManagerURL", "http://localhost:8081/juddiv3/services/security");
			// Versão UDDI que o registry usa
			props.setProperty("scout.proxy.uddiVersion", "3.0");
			// Protocolo de transporte usado para invocações ao UDDI registry
			props.setProperty("scout.proxy.transportClass", "org.apache.juddi.v3.client.transport.JAXWSTransport");
			connFactory.setProperties(props);

			// Finalmente, estabelece a ligação ao UDDI registry
			Connection connection = connFactory.createConnection();

			PasswordAuthentication passwdAuth = new PasswordAuthentication("username", "password".toCharArray());  
			Set<PasswordAuthentication> creds = new HashSet<PasswordAuthentication>();  
			creds.add(passwdAuth);
			connection.setCredentials(creds);
			
			// Obter objecto RegistryService
			RegistryService rs = connection.getRegistryService();
						
			// Obter objecto QueryManager da JAXR Business API 
			// (caso se pretenda fazer pesquisas)
			BusinessQueryManager businessQueryManager = rs.getBusinessQueryManager();
			
			Collection findQualifiers = new ArrayList();
			Collection namePatterns = new ArrayList();
			namePatterns.add("%SD-Store%");
			
			BulkResponse r = businessQueryManager.findOrganizations(findQualifiers, namePatterns, null, null, null, null);
			
			Collection<Organization> orgs = r.getCollection();
			
			System.out.println("A IMPRIMIR AS ORGANIZACOES!!!!!!!!!!!!!!!");
			System.out.println(orgs);
			System.out.println("ACABEI DE IMPRIMIR ORGANIZACOES");
			
//			vou passar as organizações e buscar os servicos
			for(Organization o:orgs){
				System.out.println(o);
				Collection<?> servicos = o.getServices();
				Iterator<?> servIter = servicos.iterator();
				System.out.println("VOU IMPRIMIR SERVICE BINDINGS!!!!!!!!!!!!!");
				while(servIter.hasNext()){
					System.out.println("A IMPRIMIR SERVICE BINDINGS!");
					Service svc = (Service) servIter.next();
					Collection<?> serviceBindings = svc.getServiceBindings();
					System.out.println(serviceBindings);
					Iterator<?> sbIter = serviceBindings.iterator();
					while(sbIter.hasNext()){
						ServiceBinding sb = (ServiceBinding) sbIter.next();
						System.out.println("A IMPRIIR URL DOS SERVICOS!!!!!!");
						System.out.println(sb.getAccessURI());
						enderecos.add(sb.getAccessURI());
					}
				}
			}
			System.out.println("A IMPRIMIR OS ENDERECOS QUE ESTAO NO PORTAL ACESSIVEIS");
			System.out.println(enderecos);
//			System.out.println(endpointUrlMap);
			
		}catch(Exception e){
			
		}
		
//		String endpointUrl = ConsolaWebService.endpointUrlMap.get(fornecedorName);
////		if(endpointUrl==null){
////			throw new FornecedorNameException_Exception();
////		}
//		BindingProvider bp = (BindingProvider)webService;
//		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);
	}
	
	@WebMethod
	public CategoriaListDto listaCategoriaWebService() throws ProdutoListException{
		try{
				updateEndpointUrl("fornecedor1");
				List<String> listaCategoria =  webService.listaCategoriaWebService().getCategoriaList();
				CategoriaListDto dto = new CategoriaListDto();
				for(String cate: listaCategoria){
					juntaCategorias(cate);
				}
				updateEndpointUrl("fornecedor2");
				List<String> listaCategoria2 =  webService.listaCategoriaWebService().getCategoriaList();
				for(String cate: listaCategoria2){
					juntaCategorias(cate);
				}
				dto.setCategoriaList(listaCategoriasPortal);
				return dto;
		}catch(ProdutoListException_Exception e){
			throw new ProdutoListException();
		}
	}
	
	@WebMethod
	public ProdListDto getlistaProdutos(String categoria) throws ProdutoListException, CategoriaNameException{
		try{
			listaProdutosPortal.clear();
			updateEndpointUrl("fornecedor1");
			List<sdstore.stubs.ProdutoDto> listaProduto = webService.getListaProdutoWebService().getListaDto();
			for(sdstore.stubs.ProdutoDto prod : listaProduto){
				ProdutoDto novo = new ProdutoDto();
				novo.setCategoria(prod.getCategoria());
				novo.setDescricao(prod.getDescricao());
				novo.setId(prod.getId());
				novo.setPreco(prod.getPreco()+0.1*prod.getPreco());
				novo.setQuantidade(prod.getQuantidade());
				juntaProdutos(novo);
			}
			updateEndpointUrl("fornecedor2");
			List<sdstore.stubs.ProdutoDto> listaProduto2 = webService.getListaProdutoWebService().getListaDto();
			for(sdstore.stubs.ProdutoDto prod : listaProduto2){
				ProdutoDto novo = new ProdutoDto();
				novo.setCategoria(prod.getCategoria());
				novo.setDescricao(prod.getDescricao());
				novo.setId(prod.getId());
				novo.setPreco(prod.getPreco()+0.1*prod.getPreco());
				novo.setQuantidade(prod.getQuantidade());
				juntaProdutos(novo);
			}	
			
			prodCategoria(categoria);
			if(listaProdutosCliente.isEmpty()){
				throw new CategoriaNameException(categoria);
			}
			ProdListDto dto = new ProdListDto(listaProdutosCliente);
			return dto;
		}catch(ProdutoListException_Exception e){
			throw new ProdutoListException();
		}
	}
	
	@WebMethod
	public CarrinhoDto listaCarrinho(){
		updateEndpointUrl("fornecedor1");
		Double precoTotal = 0.0;
		CarrinhoDto dto = new CarrinhoDto(carrinhoCompras);
		for(ProdutoDto prod : carrinhoCompras){
			System.out.println(prod);
			precoTotal = precoTotal + prod.getPreco()*prod.getQuantidade();
		}
		dto.setTotalPreco(precoTotal);
		return dto;
	}
	
	@WebMethod
	public void juntaCarrinho(String codigo,Integer quantidade) throws ProdutoExistException{
		updateEndpointUrl("fornecedor1");
		try{
		sdstore.stubs.ProdutoDto dtoRecebido = webService.pedeProduto(codigo);
//		List<sdstore.stubs.ProdutoDto> listaProdutos = webService.getListaProdutoWebService().getListaDto();	
		Integer controlo = 0;
		ProdutoDto prodEnviar = new ProdutoDto();
		if(carrinhoCompras.isEmpty()){			
			prodEnviar.setId(dtoRecebido.getId());
			prodEnviar.setQuantidade(quantidade);
			prodEnviar.setPreco(dtoRecebido.getPreco()+dtoRecebido.getPreco()*0.1);
			prodEnviar.setCategoria(dtoRecebido.getCategoria());
			prodEnviar.setDescricao(dtoRecebido.getDescricao());
			carrinhoCompras.add(prodEnviar);
		}
		else{
			for(ProdutoDto dto: carrinhoCompras){
				if(dto.getId().equals(codigo)){
					dto.setQuantidade(quantidade+dto.getQuantidade());
					controlo=1;
				}
			}
			if(controlo==0){
			prodEnviar.setId(dtoRecebido.getId());
			prodEnviar.setQuantidade(quantidade);
			prodEnviar.setPreco(dtoRecebido.getPreco()+dtoRecebido.getPreco()*0.1);
			prodEnviar.setCategoria(dtoRecebido.getCategoria());
			prodEnviar.setDescricao(dtoRecebido.getDescricao());
			carrinhoCompras.add(prodEnviar);
			}
		}
		}catch(ProdutoExistException_Exception e){
			throw new ProdutoExistException(codigo);
		}

	}
	
	@WebMethod
	public void limpaCarrinho(){
		updateEndpointUrl("fornecedor1");
		carrinhoCompras.clear();
		
	}
	
	@WebMethod
	public void encomenda() throws ProdutoExistException, QuantidadeException{
		String nome = null;
		Integer quantidade = 0;
		try{		
		updateEndpointUrl("fornecedor1");
		for(ProdutoDto prod: carrinhoCompras){
			String resultado = webService.retiraProduto(prod.getId(), prod.getQuantidade());
			nome = prod.getId();
			quantidade = prod.getQuantidade();
		}
		carrinhoCompras.clear();
		}catch(ProdutoExistException_Exception e){
			throw new ProdutoExistException(nome);
		}catch(QuantidadeException_Exception e){
			carrinhoCompras.clear();
			throw new QuantidadeException(quantidade);
		}
	}
	
	
	public void juntaCategorias(String categoria){
		
		listaCategoriasPortal.add(categoria);
	}
	
	public void juntaProdutos(ProdutoDto dto){
		Double preco = dto.getPreco();
		Integer stock = dto.getQuantidade();
		String codigo = dto.getId();
		Integer controlo=0;
		if(listaProdutosPortal.isEmpty()){
			listaProdutosPortal.add(dto);
		}
		else{
			for(ProdutoDto aux: listaProdutosPortal){
				if(aux.getId().equals(codigo)){
					aux.setQuantidade(stock+aux.getQuantidade());
					if(aux.getPreco()>preco){
						aux.setPreco(preco);
					}
					controlo = 1;
				}
			}
			if(controlo == 0){
				listaProdutosPortal.add(dto);
			}
		}
	}
	
	public void prodCategoria(String categoria){
		listaProdutosCliente.clear();
		for(ProdutoDto prod : listaProdutosPortal){
//			if(!prod.getCategoria().equals(categoria)){
//				listaProdutosPortal.remove(prod);
//			}
			if(prod.getCategoria().equals(categoria)){
				listaProdutosCliente.add(prod);
			}
		}
		
	}
	
}
