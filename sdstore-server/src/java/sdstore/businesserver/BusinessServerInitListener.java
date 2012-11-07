package sdstore.businesserver;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import sdstore.businesserver.domain.Catalogo;

import sdstore.businesserver.domain.Produto;


public class BusinessServerInitListener implements ServletContextListener{

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

//			le a linha do ficheiro
			File file = new File(listaProdutosTxt);
			FileInputStream in = new FileInputStream(file);
			Scanner scanner = new Scanner(in);
			
//			cria o catalogo
			Catalogo.createCatalogo(catalogoName);
			
			while(scanner.hasNext()){
				String readLine = scanner.nextLine();
				System.out.println("linha lida"+readLine);
				String[] splited = readLine.split(" ");
				System.out.println(splited[0]);
				System.out.println("plas");
				System.out.println(splited[1]);
//				crio o produto 
				Produto.createProduto(splited[0], splited[1], splited[2]);

				Catalogo catalogo = Catalogo.getCatalogo(catalogoName);
//				vai buscar um produto e regista no catalogo
				Produto p = Produto.getProduto(splited[0]);
				catalogo.registaProduto(p,Double.parseDouble(splited[3]), Integer.parseInt(splited[4]));				
			}
			
		} catch (Exception e) {
			System.out.println("Erro ao iniciar a webapp\n");
			e.printStackTrace();
		}
	}
}
