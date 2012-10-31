package sdstore.businesserver;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import sdstore.businesserver.domain.Fornecedor;


public class BusinessServerInitListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0){
		//codigo para libertar recursos antes do undeploy
	}
	
	@Override 
	public void contextInitialized(ServletContextEvent arg0){
		//codigo para executar apos o deploy da aplicacao no jboss
		try {
			String fornecedorName = arg0.getServletContext().getInitParameter("fornecedorName");
			Fornecedor.createFornecedor(fornecedorName);
		} catch (Exception e) {
			System.out.println("Erro ao iniciar a webapp\n");
			e.printStackTrace();
		}
	}
}
