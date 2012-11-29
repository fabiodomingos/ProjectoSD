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
	
	//BaseDados dados;
	
	// lista de bindings URL
	ArrayList<String> bindings = new ArrayList<String>();

	@Override
	public void contextDestroyed(ServletContextEvent arg0){
		//codigo para libertar recursos antes do undeploy
	}
	
	@Override 
	public void contextInitialized(ServletContextEvent arg0){
		//codigo para executar apos o deploy da aplicacao no jboss
		try {
			String catalogoName = arg0.getServletContext().getInitParameter("nomeCatalogo");
			String listaProdutosTxt = arg0.getServletContext().getInitParameter("listaProdutos");

			File dir = new File("/temp/"+catalogoName+"/je.lck");
			dir.delete();
			
			//le a linha do ficheiro
			File file = new File(listaProdutosTxt);
			FileInputStream in = new FileInputStream(file);
			Scanner scanner = new Scanner(in);
			
			//cria o catalogo
		    Catalogo.createCatalogo(catalogoName);
			
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
			
			// Não se encontrou a organização já registada, logo vamos criá-la
			String organizationName = "SD-Store";
			String nomeFornecedor = arg0.getServletContext().getInitParameter("nomeFornecedor");
			String nomeFornecedorGrande = arg0.getServletContext().getInitParameter("nomeFornecedorGrande");
			String bindingURL = "http://localhost:8080/sdstore-server-" + nomeFornecedor + "/BusinessServer" + nomeFornecedorGrande;
		
			Organization org = null;
			Collection<String> namePatterns = new ArrayList<String>();
			namePatterns.add("%"+organizationName+"%");
			// Efectua a pesquisa
			BulkResponse r = businessQueryManager.findOrganizations(findQualifiers, namePatterns, null, null, null, null);
			@SuppressWarnings("unchecked")
			Collection<Organization> orgs = r.getCollection();
			                       
			for (Organization o : orgs) {
				if (o.getName().getValue().equals(organizationName)) {
					Collection<?> servicos = o.getServices();
					Iterator<?> servIter = servicos.iterator();
					while(servIter.hasNext()){
						Service svc = (Service) servIter.next();
						Collection<?> serviceBindings = svc.getServiceBindings();
						Iterator<?> sbIter = serviceBindings.iterator();
						while(sbIter.hasNext()){
							ServiceBinding sb = (ServiceBinding) sbIter.next();
							bindings.add(sb.getAccessURI());
						}
					}
				}
			}
			
			if(bindings.contains(bindingURL)){
				System.out.println("JAAAAA EXISTEEEEEEEEEE");
			}
			else{
			
			org = businessLifeCycleManager.createOrganization(organizationName);

			// Cria serviço
			Service service = businessLifeCycleManager.createService(nomeFornecedor);
			service.setDescription(businessLifeCycleManager.createInternationalString(nomeFornecedor));
			// Adiciona o serviço à organização
			org.addService(service);
			    
			// Cria serviceBinding
			ServiceBinding serviceBinding = businessLifeCycleManager.createServiceBinding();
			serviceBinding.setDescription(businessLifeCycleManager.createInternationalString("url do binding"));
			serviceBinding.setValidateURI(false);
			// Aqui define-se o URL do endpoint do Web Service
			serviceBinding.setAccessURI(bindingURL);				
			// Adiciona o serviceBinding ao serviço
			service.addServiceBinding(serviceBinding);	
			
			Collection<Organization> orgs2 = new ArrayList<Organization>();
		    orgs2.add(org);
		     
		    // Finalmente, regista a nova organization/service/serviceBinding
		    // (ou as novas alterações) no UDDI registry
		    BulkResponse br1 = businessLifeCycleManager.saveOrganizations(orgs2);
		    
		    if (br1.getStatus() == JAXRResponse.STATUS_SUCCESS)
	            System.out.println("Registo completado com sucesso.");
		    else
	            System.out.println("Erro durante o registo no UDDI.");
		    
			
			
			}
				
			
		} catch (Exception e) {
			System.out.println("Erro ao iniciar a webapp\n");
			e.printStackTrace();
		}

	}
}
