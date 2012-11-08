package sdstore.presentationserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import sdstore.presentationserver.exception.PresenterArgumentCountException;
import sdstore.presentationserver.exception.PresenterArgumentException;
import sdstore.presentationserver.exception.PresenterCommandException;
import sdstore.presentationserver.exception.ProdutoListException;
import sdstore.presentationserver.presenter.AjudaPresenter;
import sdstore.presentationserver.presenter.CarrinhoPresenter;
import sdstore.presentationserver.presenter.ExceptionPresenter;
import sdstore.presentationserver.presenter.ListaCategoriasPresenter;
import sdstore.presentationserver.presenter.ListaProdutosPresenter;
import sdstore.presentationserver.presenter.ProdutoListExceptionPresenter;
import sdstore.presentationserver.service.bridge.ApplicationServerBridge;
import sdstore.presentationserver.service.bridge.RemoteApplicationServer;
import sdstore.presentationserver.service.stubs.CarrinhoDto;
import sdstore.presentationserver.service.stubs.CategoriaListDto;
import sdstore.presentationserver.service.stubs.ProdListDto;


public class SdStoreApplication {
	
	private static ApplicationServerBridge serviceBridge = null;
	
	public static void main(String[] args){
		System.out.println("Benvindo a applicação SDStore\n\n"
				+ "Insira o comando:");
	
		serviceBridge = new RemoteApplicationServer();
		
		while(true){
			System.out.println("[SDStore]> ");
			String input = readInputFromConsole();
			try {
				processCommand(input);
			} catch (PresenterCommandException e) {
				ExceptionPresenter.present(e);
			}catch(PresenterArgumentCountException e){
				ExceptionPresenter.present(e);
			}catch(PresenterArgumentException e){
				ExceptionPresenter.present(e);
			}
		}
	}
	
	private static void processCommand(String input) throws PresenterArgumentCountException, PresenterCommandException,PresenterArgumentException {
		String[] token = input.split(" ");
		String command = token[0];
		Integer argCount = token.length - 1;
		
		if(command.equals("lista-categorias")){
			Integer validArg = 0;
			checkCommandArg(command,argCount,validArg);
			listCategoriaCommand();
		}else if (command.equals("lista-produtos")) {
			Integer validArg = 1;
			checkCommandArg(command,argCount,validArg);
			String categoria = token[1];
			listProdutosByCategoriaCommand(categoria);
		}else if(command.equals("carrinho")){
			Integer validArg = 0;
			checkCommandArg(command,argCount,validArg);
			carrinhoCommand();
		}else if(command.equals("junta")){
			Integer validArg = 2;
			checkCommandArg(command,argCount,validArg);
			String codigo = token[1];
			Integer quantidade = Integer.parseInt(token[2]);
			juntaCommand(codigo,quantidade);
		}else if(command.equals("limpa")){
			Integer validArg = 0;
			checkCommandArg(command,argCount,validArg);
			limpaCommand();
		}else if(command.equals("encomenda")){
			Integer validArg = 0;
			checkCommandArg(command,argCount,validArg);
			encomendaCommand();
		}else if(command.equals("ajuda")){
			ajudaCommand();
		}else if(command.equals("sair")){
			exit();
		}else{
			throw new PresenterCommandException(command);
		}
		
	}

	private static void ajudaCommand() {
		AjudaPresenter.present();
	}

	private static void exit() {
		System.out.println("A sair do programa");
		System.exit(0);
	}

	private static void encomendaCommand() {
		try {
			serviceBridge.Encomenda();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	private static void limpaCommand() {
		try {
			serviceBridge.Limpa();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	private static void juntaCommand(String codigo, Integer quantidade) {
		try {
			serviceBridge.Junta(codigo,quantidade);
//			serviceBridge.Junta(dto);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private static void carrinhoCommand() {
//		cria o dto
		try {
			CarrinhoDto result = serviceBridge.Carrinho();
			CarrinhoPresenter.present(result);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	private static void listProdutosByCategoriaCommand(String categoria) {
		ProdListDto result = serviceBridge.listaProdutosCategoria(categoria);
		ListaProdutosPresenter.present(result);
		
	}

	private static void listCategoriaCommand() {
		try{
		CategoriaListDto result = serviceBridge.listaCategoria();
		ListaCategoriasPresenter.present(result);
		}catch (ProdutoListException e) {
			ProdutoListExceptionPresenter.present(e);
		}
	}

	private static void checkCommandArg(String command, Integer argCount,
			Integer validArg) throws PresenterArgumentCountException {
		if(argCount != validArg){
			throw new PresenterArgumentCountException(command,argCount);
		}
		
	}

	private static String readInputFromConsole() {
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
		try {
			input = buffer.readLine();
		} catch (Exception e) {
			System.out.println("Erro na tentativa de leitura");
		}
		return input;
	}

}
