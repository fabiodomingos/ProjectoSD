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
			}
			
			
			InitialContext context = new InitialContext();
			ConnectionFactory connFactory = (ConnectionFactory) context.lookup("java:jboss/jaxr/ConnectionFactory");
			
			////////////////////////////////////////
			////// LIGACAO AO UDDI REGISTRY ////////
			////////////////////////////////////////
			
			Properties props = new Properties();
			// Localiza��o do ficheiro de configura��o da liga��o,
			// que deve estar na directoria WEB-INF/classes do .war
			props.setProperty("scout.juddi.client.config.file", "uddi.xml");
			// URL para pesquisas ao UDDI registry
			props.setProperty("javax.xml.registry.queryManagerURL", "http://localhost:8081/juddiv3/services/inquiry");
			// URL para publicar dados no UDDI registry
			props.setProperty("javax.xml.registry.lifeCycleManagerURL", "http://localhost:8081/juddiv3/services/publish");
			// URL do gestor de seguran�a do UDDI registry
			props.setProperty("javax.xml.registry.securityManagerURL", "http://localhost:8081/juddiv3/services/security");
			// Vers�o UDDI que o registry usa
			props.setProperty("scout.proxy.uddiVersion", "3.0");
			// Protocolo de transporte usado para invoca��es ao UDDI registry
			props.setProperty("scout.proxy.transportClass", "org.apache.juddi.v3.client.transport.JAXWSTransport");
			connFactory.setProperties(props);
			
			// Finalmente, estabelece a liga��o ao UDDI registry
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
			// (caso se pretenda registar/alterar informa��o no UDDI registry)
			BusinessLifeCycleManager businessLifeCycleManager = rs.getBusinessLifeCycleManager();
			
			
			////////////////////////////////////////////////////////
			// Registo de novas entidades/altera��es a entidades j�
			// existentes no UDDI registry
			////////////////////////////////////////////////////////
			
			// N�o se encontrou a organiza��o j� registada, logo vamos cri�-la
			String organizationName = "SD-Store";
			String nomeFornecedor = arg0.getServletContext().getInitParameter("nomeFornecedor");
			String nomeFornecedorGrande = arg0.getServletContext().getInitParameter("nomeFornecedorGrande");
			String bindingURL = "http://localhost:8080/sdstore-server-" + nomeFornecedor + "/BusinessServer" + nomeFornecedorGrande;
			bindings.add(bindingURL);
			
			if(bindings.contains(bindingURL)){
				System.out.println("JAAAAA EXISTE ESTE URLLLLLLLLLLLLLLLL");
			}
			else{
			
			Organization org = businessLifeCycleManager.createOrganization(organizationName);

			// Cria servi�o
			Service service = businessLifeCycleManager.createService(nomeFornecedor);
			service.setDescription(businessLifeCycleManager.createInternationalString(nomeFornecedor));
			// Adiciona o servi�o � organiza��o
			org.addService(service);
			    
			// Cria serviceBinding
			ServiceBinding serviceBinding = businessLifeCycleManager.createServiceBinding();
			serviceBinding.setDescription(businessLifeCycleManager.createInternationalString("url do binding"));
			serviceBinding.setValidateURI(false);
			// Aqui define-se o URL do endpoint do Web Service
			serviceBinding.setAccessURI(bindingURL);				
			// Adiciona o serviceBinding ao servi�o
			service.addServiceBinding(serviceBinding);			    
			
			
			
			Collection<Organization> orgs2 = new ArrayList<Organization>();
		    orgs2.add(org);
		     
		    // Finalmente, regista a nova organization/service/serviceBinding
		    // (ou as novas altera��es) no UDDI registry
		    BulkResponse br = businessLifeCycleManager.saveOrganizations(orgs2);
		    
		    if (br.getStatus() == JAXRResponse.STATUS_SUCCESS)
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
