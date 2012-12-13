package sdstore.businesserver.service;


import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.jws.HandlerChain;
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
import javax.xml.soap.SOAPException;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.soap.SOAPFaultException;

import sdstore.businesserver.exception.CategoriaNameException;
import sdstore.businesserver.exception.PortalException;
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
@HandlerChain(file="handler-chain.xml")
public class ConsolaWebService {
	

//	lista dos enderecos dos fornecedores
	private Set<String> enderecos = new HashSet<String>();
//	carrinho de compras do cliente
	private List<ProdutoDto> carrinhoCompras = new ArrayList<ProdutoDto>();
	
	// carrinho de compras do cliente Auxiliar
	private List<ProdutoDto> carrinhoComprasAux = new ArrayList<ProdutoDto>();
	
	private Map<String,String> respostas = new HashMap<String, String>();
	private Map<String,String> respostasCanCommit = new HashMap<String,String>();
	private Map<String,String> respostasCommit = new HashMap<String,String>();
	

	private Set<String> listaCategoriasPortal = new HashSet<String>();
	private Set<ProdutoDto> listaProdutosPortal = new HashSet<ProdutoDto>();
	private Set<ProdutoDto> listaProdutosCliente = new HashSet<ProdutoDto>();
	
	
	//cenas para os handlers
	List<Handler> handlerList;
	
	private Map<String,List<ProdutoDto>> carrinhoClientes = new HashMap<String, List<ProdutoDto>>();
	
	//variavel de controlo das horas a actualizar
	private int primeiraVez=0;
	private int primeira2PC=0;
	private Integer controlo = 0;
	Calendar dataAntiga;
	Calendar dataNova;

	boolean commit;
	boolean abort;
	
	static{
	}
	
	PortalWebService webService;

	
	private void updateEndpointUrl(){
		try{
//			apaga antes de inserir novos para garantir que so esta la os que realmente estao.
			enderecos.clear();
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
			

			System.out.println("ENDERECOS DOS FORNECEDORES: "+enderecos);
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
				Binding binding = bp.getBinding();
				sdstore.businesserver.handler.Handler handler = new sdstore.businesserver.handler.Handler();
				List<Handler> handlerChain = new ArrayList<Handler>();
				handlerChain.add(handler);
				bp.getBinding().setHandlerChain(handlerChain);
				
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
			resposta = true;
			primeiraVez=1;
		}else{
			dataNova = Calendar.getInstance();
			long diferenca = dataNova.getTimeInMillis() - dataAntiga.getTimeInMillis();
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
			listaCategoriasPortal.clear();
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
	public ProdListDto getlistaProdutos(String categoria) throws CategoriaNameException, ProdutoListException{
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
	public CarrinhoDto listaCarrinho(String user){
		Double precoTotal = 0.0;
		List<ProdutoDto> carrinhoCliente = new ArrayList<ProdutoDto>();
		if(carrinhoClientes.containsKey(user)){
			carrinhoCliente = carrinhoClientes.get(user);
		}
		
		CarrinhoDto dto = new CarrinhoDto(carrinhoCliente);
		for(ProdutoDto prod : carrinhoCliente){
			System.out.println(prod);
			precoTotal = precoTotal + prod.getPreco()*prod.getQuantidade();
		}
		dto.setTotalPreco(precoTotal);
		carrinhoClientes.put(user, carrinhoCliente);
		return dto;
	}
	
	@WebMethod
	public void juntaCarrinho(String codigo,Integer quantidade,String user) throws ProdutoListException{
		try{
			List<ProdutoDto> carrinhoCliente = new ArrayList<ProdutoDto>();
			List<ProdutoDto> carrinhoClienteAux = new ArrayList<ProdutoDto>();
			System.out.println(carrinhoClientes);
			
			if(carrinhoClientes.containsKey(user)){
			 carrinhoCliente = carrinhoClientes.get(user);
			}
			 System.out.println(carrinhoCliente);
			
			if(horaUpdate()==true){
			updateEndpointUrl();
			}
			
			// Vai buscar todos os produtos que existem nos diferente fornecedores 
			for(String endereco:enderecos){
				PortalWebService webService = getFornecedores(endereco);
				List<sdstore.stubs.ProdutoDto> listaProduto = webService.getListaProdutoWebService().getListaDto();
				for(sdstore.stubs.ProdutoDto prod:listaProduto){
					ProdutoDto prodEnviar = new ProdutoDto();
					prodEnviar.setId(prod.getId());
					prodEnviar.setQuantidade(prod.getQuantidade());
					prodEnviar.setPreco(prod.getPreco()+prod.getPreco()*0.1);
					prodEnviar.setCategoria(prod.getCategoria());
					prodEnviar.setDescricao(prod.getDescricao());
					prodEnviar.setFornecedor(endereco);
					carrinhoCompras.add(prodEnviar);
				}
			}
			
			// Vamos retirar ao carrinhoCompras GLOBAL a quantidade que o cliente j‡ tem no seu carrinho 
			carrinhoCompras = retiraQuantiLista(carrinhoCliente);
			// Vai retirar do carrinhoCompras GLOBAL os produtos que n‹o queremos e colocamos numa lista auxiliar 
			carrinhoComprasAux = adicionaAuxiliar(carrinhoCompras, codigo);
			// Ordena o carrinhoComprasAux pelo preco
			Collections.sort(carrinhoComprasAux);
			// Adiciona ao carrinhoCliente o produto pretendido
			Integer quantidadeAux = 0;
			for(ProdutoDto prod: carrinhoComprasAux){
				quantidadeAux = prod.getQuantidade();
				if(quantidade>quantidadeAux){
					carrinhoCliente.add(prod);
					quantidade = quantidade-quantidadeAux;
				}
				else{
					prod.setQuantidade(quantidade);
					carrinhoCliente.add(prod);
					break;
				}
			}
			// Junta os produtos por fornecedor		
			Integer QuantidadeTotal = 0;
			Double PrecoTotal = 0.0;
			Integer control = 0;
			for(ProdutoDto prod : carrinhoCliente){
				control = 0;
				if(!carrinhoClienteAux.isEmpty()){
					for(ProdutoDto prod2: carrinhoClienteAux){
						if((prod2.getId().equals(prod.getId()))&&(prod2.getFornecedor().equals(prod.getFornecedor()))){
							QuantidadeTotal = prod2.getQuantidade()+prod.getQuantidade();
							PrecoTotal = prod2.getPreco()+prod.getPreco();
							prod2.setQuantidade(QuantidadeTotal);
							prod2.setPreco(PrecoTotal);
							control = 1;
						}
					}
					if(control == 0){
						carrinhoClienteAux.add(prod);
					}
					
				}
				else{
					carrinhoClienteAux.add(prod);
				}
			}
			carrinhoClientes.put(user, carrinhoClienteAux);
			carrinhoCompras.clear();
			carrinhoComprasAux.clear();

		}catch(ProdutoListException_Exception e){
			throw new ProdutoListException();
		}
	}
	
	@WebMethod
	public void limpaCarrinho(String user){
		List<ProdutoDto> carrinhoCliente = new ArrayList<ProdutoDto>();
		if(carrinhoClientes.containsKey(user)){
		 carrinhoCliente = carrinhoClientes.get(user);
		 carrinhoCliente.clear();
		}
	}
	
	@WebMethod
	public void encomenda(String user) throws ProdutoExistException, QuantidadeException, ProdutoListException{
		
		
		String nome = null;
		Integer quantidade = 0;
		List<ProdutoDto> carrinhoCliente = new ArrayList<ProdutoDto>();
		Set<String> fornecedoresEnvolvidos = new HashSet<String>();
		List<sdstore.stubs.ProdutoDto> produtosFinal = new ArrayList<sdstore.stubs.ProdutoDto>();
		
		if(carrinhoClientes.containsKey(user)){
			 carrinhoCliente = carrinhoClientes.get(user);
		}
		try{
//			verifica os fornecedores envolvidos
			for(ProdutoDto prod: carrinhoCliente){
				fornecedoresEnvolvidos.add(prod.getFornecedor());
			}
			System.out.println("LISTA FORNECEDORES ENVOLVIDOS "+fornecedoresEnvolvidos);
//			percorro a lista de fornecedores 
			for(String forn:fornecedoresEnvolvidos){
//				percorro o carrinho a procura dos produtos do fornecedor
				for(ProdutoDto pro:carrinhoCliente){
					if(forn.equals(pro.getFornecedor())){
						sdstore.stubs.ProdutoDto prod1 = new sdstore.stubs.ProdutoDto();
						prod1.setId(pro.getId());
						prod1.setQuantidade(pro.getQuantidade());
						produtosFinal.add(prod1);
					}
				}
				System.out.println("LISTA DE PRODUTOS ENVOLVIDOS DO FORNECEDOR "+forn+" AI ESTA A LISTA "+produtosFinal);
				PortalWebService webService = getFornecedores(forn);
				String resultado = webService.retiraProduto(produtosFinal, user);
				respostas.put(forn, resultado);
			}
			
			
			System.out.println("RESPOSTAS DO RETIRA PRODUTO "+respostas);

			if(verificaCanCommit().equals("YES")){
				for(String chave:respostas.keySet()){
					PortalWebService webService = getFornecedores(chave);
					String resultado = webService.canCommitService(user);
					respostasCanCommit.put(chave, resultado);
				}
			}else{
				for(String chave:respostas.keySet()){
					PortalWebService webService = getFornecedores(chave);
					webService.abortService(user);
				}
			}
			
			System.out.println("RESPOSTAS DO CAN COMMIT "+respostasCanCommit);
			
			if(verificaCommit().equals("YES")){
				for(String chave:respostasCanCommit.keySet()){
					commit = false;
					//int conta=0;
					PortalWebService webService = getFornecedores(chave);
//					Thread.sleep(40000);
//					System.out.println("PASSO NO SLEEP");
					while(commit==false){
						try{
							String resultado = webService.commitService(user);
							commit = true;
						}catch(Exception e){
							commit=false;
//							if(conta==3){
//								Thread.sleep(40000);
//							}
//							else{
//								commit=false;
//								conta++;
//							}
							
						}
					}
				}
			}else{
				for(String chave:respostasCanCommit.keySet()){
					abort = false;
					PortalWebService webService = getFornecedores(chave);
					while(abort==false){
						webService.abortService(user);
						abort = true;
					}
				}
			}
			
			carrinhoCompras.clear();
			carrinhoCliente.clear();
			carrinhoClientes.remove(user);
			respostas.clear();
			respostasCanCommit.clear();
			
		}catch(Exception e){
			for(String chave:respostas.keySet()){
				PortalWebService webService = getFornecedores(chave);
				webService.abortService(user);
			}
		}
	}

	public String verificaCanCommit(){
		String result = null;
		for(String res: respostas.values()){
			if(res.equals("NO")){
				result="NO";
				break;
			}
			else{
				result="YES";
			}
		}
		return result;
	}
	
	public String verificaCommit(){
		String result = null;
		for(String res: respostasCanCommit.values()){
			if(res.equals("NO")){
				result="NO";
				break;
			}
			else{
				result="YES";
			}
		}
		return result;
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
	
	// Vamos retirar ao carrinhoCompras GLOBAL a quantidade que o cliente j‡ tem no seu carrinho 
	public List<ProdutoDto> retiraQuantiLista(List<ProdutoDto> carrinhoCliente){
		for(ProdutoDto prod: carrinhoCompras){
			for(ProdutoDto prod2: carrinhoCliente){
				if((prod2.getId().equals(prod.getId()))
						&&(prod2.getFornecedor().equals(prod.getFornecedor()))){
					prod.setQuantidade(prod.getQuantidade()-prod2.getQuantidade());
				}
			}
		}
		return carrinhoCompras;
	}

	// Vai retirar do carrinhoCompras GLOBAL os produtos que n‹o queremos e colocamos numa lista auxiliar 
	public List<ProdutoDto> adicionaAuxiliar(List<ProdutoDto> carrinhoCompras,String codigo){
		for(ProdutoDto prod: carrinhoCompras){
			if(prod.getId().equals(codigo)&&prod.getQuantidade()>0){
				carrinhoComprasAux.add(prod);
			}
		}
		return carrinhoComprasAux;
	}	
}
