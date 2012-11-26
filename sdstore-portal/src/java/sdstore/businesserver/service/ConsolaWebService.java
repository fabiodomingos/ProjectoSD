package sdstore.businesserver.service;


import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.Calendar;
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
	
//	private static Map<String, String> endpointUrlMap;
//	lista dos enderecos dos fornecedores
	private Set<String> enderecos = new HashSet<String>();
//	carrinho de compras do cliente
	private List<ProdutoDto> carrinhoCompras = new ArrayList<ProdutoDto>();
	private Set<String> listaCategoriasPortal = new HashSet<String>();
	private Set<ProdutoDto> listaProdutosPortal = new HashSet<ProdutoDto>();
	private Set<ProdutoDto> listaProdutosCliente = new HashSet<ProdutoDto>();
	
	//variavel de controlo das horas a actualizar
	private int primeiraVez=0;
	Calendar dataAntiga;
	Calendar dataNova;
	
	static{
	}
	
	PortalWebService webService;

	
	private void updateEndpointUrl(){
		try{
			////////////////////////////////////////
			////// LIGACAO AO UDDI REGISTRY ////////
			////////////////////////////////////////
			ConnectionFactory connFactory = org.apache.ws.scout.registry.ConnectionFactoryImpl.newInstance();
			Properties props = new Properties();
			// Localização do ficheiro de configuração da ligação,
			// que deve estar na directoria WEB-INF/classes do .war
			props.setProperty("scout.juddi.client.config.file", "uddi.xml");
			// URL para pesquisas ao UDDI registry
			props.setProperty("javax.xml.registry.queryManagerURL", "http://localhost:8081/juddiv3/services/inquiry");
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
//			vou passar as organizações e buscar os servicos
			for(Organization o:orgs){
				Collection<?> servicos = o.getServices();
				Iterator<?> servIter = servicos.iterator();
				while(servIter.hasNext()){
					Service svc = (Service) servIter.next();
					Collection<?> serviceBindings = svc.getServiceBindings();
					Iterator<?> sbIter = serviceBindings.iterator();
					while(sbIter.hasNext()){
						ServiceBinding sb = (ServiceBinding) sbIter.next();
						enderecos.add(sb.getAccessURI());
					}
				}
			}
//			System.out.println("A IMPRIMIR OS ENDERECOS QUE ESTAO NO PORTAL ACESSIVEIS");
//			System.out.println(enderecos);			
		}catch(Exception e){
			
		}
	}
	
	private PortalWebService getFornecedores(String endereco){
		PortalWebService webService;
		PortalWebServiceService service = new PortalWebServiceService();
		webService = service.getPortalWebServicePort();
		for(String ende:enderecos){
			if(ende.equals(endereco)){
				BindingProvider bp = (BindingProvider)webService;
				bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, ende);
			}
		}
		return webService;
	}
	
//	metodo que verifica se necessita actualizar ou nao o UDDI
	public boolean horaUpdate(){
		System.out.println("A verificar horas");
		boolean resposta=false;
		if(primeiraVez==0){
//			primeira vez tira os tempos
			dataAntiga = Calendar.getInstance();
			dataNova = Calendar.getInstance();
			System.out.println(dataAntiga.getTimeInMillis()+" ESTA E A DATA ANTIGA");
			System.out.println(dataNova.getTimeInMillis()+" ESTA E A DATA NOVA");
			resposta = true;
			primeiraVez=1;
		}else{
			dataNova = Calendar.getInstance();
			long diferenca = dataNova.getTimeInMillis() - dataAntiga.getTimeInMillis();
			System.out.println("diferenca em milisegundos: "+diferenca);
			if(diferenca>10000){
				dataAntiga = Calendar.getInstance();
				resposta = true;
			}else{
				resposta = false;
			}
		}
		return resposta;
	}
	
	@WebMethod
	public CategoriaListDto listaCategoriaWebService() throws ProdutoListException{
		try{
//			horaUpdate();
			if(horaUpdate()==true){
			updateEndpointUrl();
			}
			CategoriaListDto dto = new CategoriaListDto();
			System.out.println("listaCategorias!!!!!!");
			for(String endereco:enderecos){
				PortalWebService webService = getFornecedores(endereco);
				List<String> listaCategoria = webService.listaCategoriaWebService().getCategoriaList();
				for(String cate:listaCategoria){
					juntaCategorias(cate);
				}
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
//			podera estar a mais, a pensar numa solucao para nao ir sempre ao uddi
			if(horaUpdate()==true){
			updateEndpointUrl();
			}
			for(String endereco:enderecos){
				PortalWebService webService = getFornecedores(endereco);
				List<sdstore.stubs.ProdutoDto> listaProduto = webService.getListaProdutoWebService().getListaDto();
				for(sdstore.stubs.ProdutoDto prod:listaProduto){
					ProdutoDto novo = new ProdutoDto();
					novo.setCategoria(prod.getCategoria());
					novo.setDescricao(prod.getDescricao());
					novo.setId(prod.getId());
					novo.setPreco(prod.getPreco()+0.1*prod.getPreco());
					novo.setQuantidade(prod.getQuantidade());
					juntaProdutos(novo);
				}
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
		try{
			Integer controlo = 0;
			for(String endereco:enderecos){
				PortalWebService webService = getFornecedores(endereco);
				sdstore.stubs.ProdutoDto dtoRecebido = webService.pedeProduto(codigo);
				ProdutoDto prodEnviar = new ProdutoDto();
				if(carrinhoCompras.isEmpty()){
					prodEnviar.setId(dtoRecebido.getId());
					prodEnviar.setQuantidade(quantidade);
					prodEnviar.setPreco(dtoRecebido.getPreco()+dtoRecebido.getPreco()*0.1);
					prodEnviar.setCategoria(dtoRecebido.getCategoria());
					prodEnviar.setDescricao(dtoRecebido.getDescricao());
					carrinhoCompras.add(prodEnviar);	
				}else{
					for(ProdutoDto dto:carrinhoCompras){
						if(dto.getId().equals(codigo)){
						dto.setQuantidade(quantidade+dto.getQuantidade());
						controlo=1;
						}
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
		carrinhoCompras.clear();
		
	}
	
	@WebMethod
	public void encomenda() throws ProdutoExistException, QuantidadeException{
		String nome = null;
		Integer quantidade = 0;
		try{		
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
			if(prod.getCategoria().equals(categoria)){
				listaProdutosCliente.add(prod);
			}
		}
		
	}
	
}
