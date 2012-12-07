package sdstore.businesserver;

import java.io.File;
import java.io.FileInputStream;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.registry.BulkResponse;
import javax.xml.registry.BusinessLifeCycleManager;
import javax.xml.registry.BusinessQueryManager;
import javax.xml.registry.Connection;
import javax.xml.registry.ConnectionFactory;
import javax.xml.registry.FindQualifier;
import javax.xml.registry.JAXRException;
import javax.xml.registry.JAXRResponse;
import javax.xml.registry.RegistryService;
import javax.xml.registry.infomodel.Key;
import javax.xml.registry.infomodel.Organization;
import javax.xml.registry.infomodel.Service;
import javax.xml.registry.infomodel.ServiceBinding;

import sdstore.businesserver.domain.Catalogo;

import sdstore.businesserver.domain.Produto;


public class BusinessServerInitListener implements ServletContextListener{
	
	TreeSet<String> enderecos = new TreeSet<String>();
	File dir;
	BaseDados dados;
	String organizationName;
	
	//BaseDados dados;
	
	// lista de bindings URL
	ArrayList<String> bindings = new ArrayList<String>();

	@Override
	public void contextDestroyed(ServletContextEvent arg0){
		
		try{
			dados.close();
			dir.delete();
			
			ConnectionFactory connFactory = org.apache.ws.scout.registry.ConnectionFactoryImpl.newInstance();
			/**
			 * Ligacao ao UDDI
			 */
			Properties props = new Properties();
			props.setProperty("scout.juddi.client.config.file", "uddi.xml");
			props.setProperty("javax.xml.registry.queryManagerURL",  "http://localhost:8081/juddiv3/services/inquiry");
			props.setProperty("javax.xml.registry.lifeCycleManagerURL", "http://localhost:8081/juddiv3/services/publish");
			props.setProperty("javax.xml.registry.securityManagerURL", "http://localhost:8081/juddiv3/services/security");
			props.setProperty("scout.proxy.uddiVersion", "3.0");
			props.setProperty("scout.proxy.transportClass", "org.apache.juddi.v3.client.transport.JAXWSTransport");
			connFactory.setProperties(props);

			Connection connection = connFactory.createConnection();


			PasswordAuthentication passwdAuth = new PasswordAuthentication("username", "password".toCharArray());  
			Set<PasswordAuthentication> creds = new HashSet<PasswordAuthentication>();  
			creds.add(passwdAuth);
			connection.setCredentials(creds);
			
			/**
			 * Obter objecto RegistryService
			 */
			RegistryService rs = connection.getRegistryService();

			/*
			 * Obter objecto QueryManager
			 */
			BusinessQueryManager businessQueryManager = rs.getBusinessQueryManager();
			/*
			 * Obter objecto LifeCycle
			 */
			BusinessLifeCycleManager businessLifeCycleManager = rs.getBusinessLifeCycleManager();

			Collection<String> findQualifiers = new ArrayList<String>();
			findQualifiers.add(FindQualifier.SORT_BY_NAME_DESC);
			
			//String organizationName = "Anacom";
			//String serviceName = operatorName;
			organizationName = "SD-Store";

			Collection<String> namePatterns = new ArrayList<String>();
			namePatterns.add("%"+organizationName+"%");
			BulkResponse r = businessQueryManager.findOrganizations(findQualifiers, namePatterns, null, null, null, null);
			
			String nomeFornecedor = arg0.getServletContext().getInitParameter("nomeFornecedor");
			String nomeFornecedorGrande = arg0.getServletContext().getInitParameter("nomeFornecedorGrande");
			String bindingURL = "http://localhost:8080/sdstore-server-" + nomeFornecedor + "/BusinessServer" + nomeFornecedorGrande;
		
			
			@SuppressWarnings("unchecked")
			Collection<Organization> orgs = r.getCollection();
			
			System.out.println("ORGANIZACOES "+orgs);
			ServiceBinding sbRemove = null;
			Service apagar = null;
			Organization delete = null;
			for(Organization o:orgs){
				@SuppressWarnings("unchecked")
				Collection<Service> servs = o.getServices();
				if(!servs.isEmpty()){
					for(Service c:servs){
						Collection<ServiceBinding> binds = c.getServiceBindings();
						if(c.getName().getValue().equals(nomeFornecedor)){
							for(ServiceBinding b: binds){
								if(b.getAccessURI().equals(bindingURL)){
									c.removeServiceBinding(b);
									break;
								}
							}
						}
					}
				}
			}
//			for(Organization o:orgs){
//				if(o.getName().getValue().equals(organizationName)){
//					//Service serv = null;
//					Collection<?> servicos = o.getServices();
//					Iterator<?> servIter = servicos.iterator();
//					while(servIter.hasNext()){
//						Service svc = (Service) servIter.next();
//						//serv = svc;
//						Collection<?> serviceBindings = svc.getServiceBindings();
//						System.out.println("BINDING QUE ESTA NO SERVICO: "+ serviceBindings.size());
//						Iterator<?> sbIter = serviceBindings.iterator();
//						while(sbIter.hasNext()){
//							ServiceBinding sb = (ServiceBinding) sbIter.next();
//							System.out.println("SERVICE URL "+sb.getAccessURI());
//							if(sb.getAccessURI().equals(bindingURL)){
//								sbRemove = sb;
//								svc.removeServiceBinding(sbRemove);
//								break;
//							}
//						}
////						o.removeService(svc);
//						break;
//						//svc.removeServiceBinding(sbRemove);
//					}
//					//o.removeService(serv);
//				}
//			}
		
			
			//orgApagar.removeService(servApagar);
			businessLifeCycleManager.saveOrganizations(orgs);
			
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}
	
	@Override 
	public void contextInitialized(ServletContextEvent arg0){
		//codigo para executar apos o deploy da aplicacao no jboss
		try {
			String catalogoName = arg0.getServletContext().getInitParameter("nomeCatalogo");
			String listaProdutosTxt = arg0.getServletContext().getInitParameter("listaProdutos");

			dir = new File("/temp/"+catalogoName+"/je.lck");
			
			
			
			//le a linha do ficheiro
			File file = new File(listaProdutosTxt);
			FileInputStream in = new FileInputStream(file);
			Scanner scanner = new Scanner(in);
			
			//cria o catalogo
		    Catalogo.createCatalogo(catalogoName);
		    dados = Catalogo.getDados();
			
			while(scanner.hasNext()){
				String readLine = scanner.nextLine();
				String[] splited = readLine.split(" ");
				//crio o produto 
				Produto.createProduto(splited[0], splited[1], splited[2]);

				Catalogo catalogo = Catalogo.getCatalogo(catalogoName);
				//vai buscar um produto e regista no catalogo
				Produto p = Produto.getProduto(splited[0]);
				catalogo.registaProduto(p,Double.parseDouble(splited[3]), Integer.parseInt(splited[4]));	
				//dados.run(p);
			}
			
			
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
			// Obter objecto BusinessLifeCycleManager da JAXR Business API 
			// (caso se pretenda registar/alterar informação no UDDI registry)
			BusinessLifeCycleManager businessLifeCycleManager = rs.getBusinessLifeCycleManager();
			
			
			Collection<String> findQualifiers = new ArrayList<String>();
			findQualifiers.add(FindQualifier.SORT_BY_NAME_DESC);
			
			////////////////////////////////////////////////////////
			// Registo de novas entidades/alterações a entidades já
			// existentes no UDDI registry
			////////////////////////////////////////////////////////
			
			organizationName = "SD-Store";
//			String nomeFornecedor = arg0.getServletContext().getInitParameter("nomeFornecedor");
//			String nomeFornecedorGrande = arg0.getServletContext().getInitParameter("nomeFornecedorGrande");
//			String bindingURL = "http://localhost:8080/sdstore-server-" + nomeFornecedor + "/BusinessServer" + nomeFornecedorGrande;
			
			Organization org = null;
			Collection<String> namePatterns = new ArrayList<String>();
			namePatterns.add("%"+organizationName+"%");
			// Efectua a pesquisa
			BulkResponse r = businessQueryManager.findOrganizations(findQualifiers, namePatterns, null, null, null, null);
			@SuppressWarnings("unchecked")
			Collection<Organization> orgs = r.getCollection();
			
			System.out.println("ORGANIZACAOES EXISTENTESSSS:   "+ orgs.size());
			
			if(orgs.size()==0 || orgs==null){
				System.out.println("VAI CRIAR A ORGANIZACAO PELA PRIMEIRA VEZ!!!");
				org = businessLifeCycleManager.createOrganization(organizationName);             
			}
			else{
				for(Organization or : orgs){
					if(or.getName().getValue().equalsIgnoreCase(organizationName)) {
						org=or;

					}
				}
			}
			
			String nomeFornecedor = arg0.getServletContext().getInitParameter("nomeFornecedor");
			String nomeFornecedorGrande = arg0.getServletContext().getInitParameter("nomeFornecedorGrande");
			String bindingURL = "http://localhost:8080/sdstore-server-" + nomeFornecedor + "/BusinessServer" + nomeFornecedorGrande;
			
			
			// Cria serviço
			Service service = businessLifeCycleManager.createService(nomeFornecedor);
			service.setDescription(businessLifeCycleManager.createInternationalString(nomeFornecedor));
			// Adiciona o serviço à organização
			org.addService(service);

			// Cria serviceBinding
			ServiceBinding serviceBinding = businessLifeCycleManager.createServiceBinding();
			serviceBinding.setDescription(businessLifeCycleManager.createInternationalString("url do fornecedor"));
			serviceBinding.setValidateURI(false);
			// Aqui define-se o URL do endpoint do Web Service
			serviceBinding.setAccessURI(bindingURL);				
			// Adiciona o serviceBinding ao serviço
			service.addServiceBinding(serviceBinding);
			
//			for (Organization o : orgs) {
//				System.out.println("ENTROOOO AQUIIIIIII");
//				if (o.getName().getValue().equals(organizationName)) {
//					org = o;
//					Service service = null;
//					@SuppressWarnings("unchecked")
//					Collection<Service> servicos = o.getServices();
//					for(Service s: servicos){
//						if(s.getName().getValue().equals(nomeFornecedor)){
//							service = s;
//							System.out.println("QUAL O SERVICO: "+ service.getDescription().getValue());
//							ServiceBinding serviceBinding = null;
//							@SuppressWarnings("unchecked")
//							Collection<ServiceBinding> sBindings = s.getServiceBindings();
//							System.out.println("TOTAL DE URLS:  "+ sBindings.size());
//							for(ServiceBinding sb: sBindings){
//								System.out.println("VARIAVEL BINDINGURL:  "+ bindingURL);
//								System.out.println("PROCURA DE BINDING URL:  "+ sb.getAccessURI());
//								if(sb.getAccessURI().equals(bindingURL)){
//									serviceBinding = sb;
//									sb.setAccessURI(bindingURL);
//								}
//							}
//							if(serviceBinding ==null){
//								serviceBinding = businessLifeCycleManager.createServiceBinding();
//							    serviceBinding.setDescription(businessLifeCycleManager.
//							            createInternationalString("URL do binding"));
//							    serviceBinding.setValidateURI(false);
//							    
//							    serviceBinding.setAccessURI(bindingURL);				
//							    
//							    service.addServiceBinding(serviceBinding);
//							}
//						}
//					}
//					if(service == null){
//						System.out.println("ESTOU AQUI");
//						service = businessLifeCycleManager.createService(nomeFornecedor);
//						service.setDescription(businessLifeCycleManager.createInternationalString(nomeFornecedor));
//						org.addService(service);
//						System.out.println("SERVICO:   "+ service.getName().getValue());
//						
//						ServiceBinding serviceBinding = businessLifeCycleManager.createServiceBinding();
//					    serviceBinding.setDescription(businessLifeCycleManager.
//					            createInternationalString("url do binding"));
//					    serviceBinding.setValidateURI(false);
//					    
//					    serviceBinding.setAccessURI(bindingURL);				
//					    service.addServiceBinding(serviceBinding);
//					}
//					
//				}
//			}
//			
//			if(org==null){
//				System.out.println("MEQUIEEEEE");
//				org = businessLifeCycleManager.createOrganization(organizationName);
//
//				//Cria serviço
//				Service service = businessLifeCycleManager.createService(nomeFornecedor);
//				service.setDescription(businessLifeCycleManager.createInternationalString(nomeFornecedor));
//				// Adiciona o serviço à organização
//				org.addService(service);
//							    
//				// Cria serviceBinding
//				ServiceBinding serviceBinding = businessLifeCycleManager.createServiceBinding();
//				serviceBinding.setDescription(businessLifeCycleManager.createInternationalString("url do binding"));
//				serviceBinding.setValidateURI(false);
//				// Aqui define-se o URL do endpoint do Web Service
//				serviceBinding.setAccessURI(bindingURL);				
//				// Adiciona o serviceBinding ao serviço
//				service.addServiceBinding(serviceBinding);	
//			}
			
			Collection<Organization> orgs2 = new ArrayList<Organization>();
		    orgs2.add(org);
		     
			// Finalmente, regista a nova organization/service/serviceBinding
		    // (ou as novas alterações) no UDDI registry
		    BulkResponse br1 = businessLifeCycleManager.saveOrganizations(orgs2);
		    
		    if (br1.getStatus() == JAXRResponse.STATUS_SUCCESS)
	            System.out.println("Registo completado com sucesso.");
		    else
	            System.out.println("Erro durante o registo no UDDI.");
				
			
		} catch (Exception e) {
			System.out.println("Erro ao iniciar a webapp\n");
			e.printStackTrace();
		}

	}
}
